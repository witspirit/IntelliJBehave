package com.github.kumaraman21.intellijbehave.utility;


import com.intellij.openapi.util.Pair;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 * @author debritod
 */
public class ParametrizedString implements Comparable<ParametrizedString> {

    private final String content;
    private final String parameterPrefix;
    private final List<Token> tokens = new ArrayList<Token>();
    private final List<Token> tokensWithoutIdentifier = new ArrayList<Token>();
    private static final Pattern dollarPattern = Pattern.compile("(\\$\\w*)(\\W|\\Z)", Pattern.DOTALL);
    private static final Pattern bracketPattern = Pattern.compile("(<\\w*?>)(\\W|\\Z)", Pattern.DOTALL);

    public ParametrizedString(String content) {
        this(content, "$");
    }


    private ParametrizedString(String content, String parameterPrefix) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        this.content = content;
        this.parameterPrefix = parameterPrefix;
        if (!parse(compileParameterPattern(parameterPrefix), 0)) {
            tokens.clear();
            tokensWithoutIdentifier.clear();
            parse(bracketPattern, 1);
        }
    }

    private static Pattern compileParameterPattern(String parameterPrefix) {
        if (parameterPrefix.equals("$")) return dollarPattern;
        return Pattern.compile("(\\" + parameterPrefix + "\\w*)(\\W|\\Z)", Pattern.DOTALL);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public String toString() {
        return StringUtils.join(tokens.toArray(), " ");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ParametrizedString) && isSameAs((ParametrizedString) obj);
    }

    private boolean isSameAs(ParametrizedString other) {
        return other.content.equals(content);
    }

    private boolean parse(final Pattern parameterPattern, int add) {
        boolean foundIdentifier = false;
        final Matcher matcher = parameterPattern.matcher(content);

        int prev = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (start > prev) {
                add(new Token(prev, start - prev, false));
            }
            end -= matcher.group(2).length();
            start += parameterPrefix.length(); // remove prefix from the identifier
            add(new Token(start, end - start - add, true));
            foundIdentifier = true;
            prev = end;
        }
        if (prev < content.length()) {
            add(new Token(prev, content.length() - prev, false));
        }
        return foundIdentifier;
    }

    private void add(Token token) {
        tokens.add(token);
        if (!token.isIdentifier) {
            tokensWithoutIdentifier.add(token);
        }
    }

    public String toStringWithoutIdentifiers() {
        List<String> result = new ArrayList<String>();
        for (Token token : tokensWithoutIdentifier) {
            result.add(token.value().trim());
        }

        return StringUtils.join(result, " ");
    }

    @Override
    public int compareTo(@NotNull ParametrizedString other) {
        return toStringWithoutIdentifiers().compareTo(other.toStringWithoutIdentifiers());
    }

    public Token getToken(int index) {
        return tokens.get(index);
    }

    private WeightChain calculateWeightChain(String input) {
        WeightChain chain = acceptsBeginning(0, input, 0);
        chain.input = input;
        chain.collectWeights();
        return chain;
    }

    public List<StringToken> tokenize(String stepInput) {

        List<StringToken> stringTokens = new ArrayList<StringToken>();

        WeightChain chain = calculateWeightChain(stepInput);
        List<String> inputTokens = chain.tokenize();
        while (chain != null) {
            if (!chain.isZero()) {
                Token token = tokens.get(chain.getTokenIndex());
                String value = inputTokens.get(chain.getTokenIndex());
                stringTokens.add(new StringToken(value, token.isIdentifier()));
            }
            chain = chain.getNext();
        }
        return stringTokens;
    }

    private WeightChain acceptsBeginning(int inputIndex, String input, int tokenIndexStart) {
        WeightChain pair = new WeightChain();
        pair.inputIndex = inputIndex;

        WeightChain current = pair;

        List<Token> tokenList = this.tokens;
        for (int tokenIndex = tokenIndexStart, n = tokenList.size(); tokenIndex < n; tokenIndex++) {
            boolean isLastToken = (tokenIndex == n - 1);
            Token token = tokenList.get(tokenIndex);
            if (!token.isIdentifier()) {
                int remaining = input.length() - inputIndex;
                if (remaining > token.getLength() && isLastToken) {
                    // more data than the token itself
                    return WeightChain.zero();
                }

                int overlaping = Math.min(token.getLength(), remaining);
                if (overlaping > 0) {
                    if (token.regionMatches(0, input, inputIndex, overlaping)) {
                        current.tokenIndex = tokenIndex;
                        current.weight++;
                        if (overlaping == token.getLength()) // full token match
                        {
                            current.weight++;
                            if ((inputIndex + overlaping) == input.length())
                            // no more data, break the loop now
                            {
                                return pair;
                            }
                        } // break looop
                        else {
                            return pair;
                        }

                        inputIndex += overlaping;
                        WeightChain next = new WeightChain();
                        next.inputIndex = inputIndex;
                        current.next = next;
                        current = next;
                    } else {
                        // no match
                        return WeightChain.zero();
                    }
                } else {
                    // not enough data, returns what has been collected
                    return pair;
                }
            } else {
                current.tokenIndex = tokenIndex;
                current.weight++;

                // not the most efficient part, but no other solution right now
                WeightChain next = WeightChain.zero();
                for (int j = inputIndex + 1; j < input.length(); j++) {
                    WeightChain sub = acceptsBeginning(j, input, tokenIndex + 1);
                    if (sub.hasMoreWeightThan(next)) {
                        next = sub;
                    }
                }
                current.next = next;
                return pair;
            }
        }
        return pair;
    }

    public boolean isSameAs(String input) {
        return complete(input, false).equals(input);
    }

    public String complete(String input, boolean hasPostParameter) {
        List<ContentToken> builder = new ArrayList<ContentToken>();
        List<StringToken> myTokens = new ArrayList<StringToken>();
        List<ContentToken> inputTokens = new ArrayList<ContentToken>();

        putInWordTokens(myTokens);

        inputTokens.addAll(split(input));

        Iterator<StringToken> myTokenIt = myTokens.iterator();
        Iterator<ContentToken> myInputIt = inputTokens.iterator();
        boolean match = false;
        while (myInputIt.hasNext()) {
            StringToken token = myTokenIt.next();
            ContentToken currentInput = myInputIt.next();
            if (token.isIdentifier()) {
                if (!myTokenIt.hasNext() || !myInputIt.hasNext()) {
                    builder.add(currentInput);
                    break;
                }
                //lookahead
                StringToken lookAheadToken = myTokenIt.next();
                while (lookAheadToken.isIdentifier() && myTokenIt.hasNext()) {
                    lookAheadToken = myTokenIt.next();
                }
                String value = lookAheadToken.getValue();
                //find a matching input token
                match = false;
                List<ContentToken> subs = new ArrayList<ContentToken>();

                do {
                    subs.add(currentInput);
                    currentInput = myInputIt.next();
                    boolean b1 = !myInputIt.hasNext() && value.startsWith(currentInput.value());
                    boolean b2 = myInputIt.hasNext() && value.equals(currentInput.value());
                    if (b1 || b2) {
                        builder.add(new ContentToken(subs));
                        //builder.add(value);
                        match = true;
                        break;
                    }

                } while (myInputIt.hasNext());
                if (!match) return "";
            } else {
                String tokenValue = token.getValue();
                match = (!myInputIt.hasNext() && tokenValue.startsWith(currentInput.value())) ||
                        (myInputIt.hasNext() && tokenValue.equals(currentInput.value()));
                if (!match) return "";
            }
        }
        if (!match) return "";
        while (myTokenIt.hasNext()) {
            StringToken token = myTokenIt.next();
            String tokenValue = token.getValue();
            if (token.isIdentifier()) {
                builder.add(new ContentToken("$" + tokenValue));
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator<ContentToken> parameterIt = builder.iterator();
        List<Token> tokens1 = this.tokens;
        for (int i = 0; i < tokens1.size() - 1; i++) {
            Token token = tokens1.get(i);
            if (token.isIdentifier) {
                sb.append(parameterIt.next());
            } else {
                sb.append(token.value());
            }
        }
        Token lastToken = tokens1.get(tokens1.size() - 1);
        if (lastToken.isIdentifier) {
            if (!hasPostParameter) {
                sb.append(parameterIt.next());
            }
        } else {
            sb.append(lastToken.value());
        }
        return sb.toString();
    }

    private static final Pattern pattern = Pattern.compile("([\\S&&[^:]]+|[\\W&&[^\\s]])", Pattern.DOTALL);
    //private static Pattern injectPattern = Pattern.compile("(<.+>)", Pattern.DOTALL);

    public static List<ContentToken> split(final String text) {
        final Matcher matcher = pattern.matcher(text);
        List<ContentToken> result = new ArrayList<ContentToken>();

        while (matcher.find()) {
            result.add(new ContentToken(text, matcher.start(), matcher.end()));
        }
        return result;
    }

    private static final Pattern injectPattern = Pattern.compile("(<{1,2}+.+?>{1,2}+)", Pattern.DOTALL);

    public static List<ContentToken> splitOnInject(final String text) {
        final Matcher matcher = injectPattern.matcher(text);

        List<ContentToken> result = new ArrayList<ContentToken>();
        int unmatchedStart = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (unmatchedStart != start) {
                result.add(new ContentToken(text, unmatchedStart, start));
            }
            result.add(new ContentToken(text, start, end));
            unmatchedStart = end;
        }
        if (unmatchedStart < text.length()) result.add(new ContentToken(text, unmatchedStart, text.length()));
        return result;
    }

    public static String unwrapInject(String maybeInject) {
        if (maybeInject.startsWith("<") && maybeInject.endsWith(">")) {
            int start = maybeInject.indexOf("|");
            if (start >= 0) {
                ++start;
                int end = maybeInject.indexOf(">", start);
                return maybeInject.substring(start, end);
            }
            return "";
        }
        return maybeInject;
    }

    private void putInWordTokens(Collection<StringToken> myTokens) {
        for (Token token : tokens) {
            String value = token.value();
            if (token.isIdentifier) {
                myTokens.add(new StringToken(value.trim(), true));
            } else {
                for (ContentToken tok : split(value)) {
                    myTokens.add(new StringToken(tok.value().trim(), false));
                }
            }
        }
    }

    /**
     * Process input and return a list of tokens in the same syntax as me.
     * E.g. if I'm: "the red $fox jumps over the $hill" and input is:
     * "the red funny bird jumps over the mountains" then the tokens returned are:
     * [the red, false]
     * [funny bird, true]
     * [jumps over the, false]
     * [mountains, true]
     *
     * @param input
     * @return
     */
    @Nullable
    public List<Pair<ContentToken, String>> getTokensOf(String input) {
        List<Pair<ContentToken, String>> retVal = new ArrayList<Pair<ContentToken, String>>();
        Deque<StringToken> myTokens = new ArrayDeque<StringToken>();

        putInWordTokens(myTokens);

        if (!myTokens.isEmpty()) {
            Deque<ContentToken> inputTokens = new ArrayDeque<ContentToken>();
            inputTokens.addAll(split(input));

            boolean match = false;
            final List<ContentToken> subs = new ArrayList<ContentToken>();
            while (!inputTokens.isEmpty()) {
                if (myTokens.isEmpty()) return null;
                StringToken token = myTokens.removeFirst();
                ContentToken currentInput = inputTokens.removeFirst();
                if (token.isIdentifier()) {
                    if (!subs.isEmpty()) {
                        retVal.add(Pair.create(new ContentToken(subs), (String) null));
                        subs.clear();
                    }
                    if (myTokens.isEmpty()) {
                        subs.add(currentInput);
                        while (!inputTokens.isEmpty()) {
                            currentInput = inputTokens.removeFirst();
                            subs.add(currentInput);
                        }
                        retVal.add(Pair.create(new ContentToken(subs), token.getValue()));
                        subs.clear();
                        break;
                    }
                    if (inputTokens.isEmpty()) {
                        return null;
                    }
                    //lookahead
                    StringToken lookAheadToken = myTokens.removeFirst();
                    while (lookAheadToken.isIdentifier() && !myTokens.isEmpty()) {
                        lookAheadToken = myTokens.removeFirst();
                    }
                    String value = lookAheadToken.getValue();
                    //find a matching input token
                    match = false;
                    do {
                        subs.add(currentInput);
                        currentInput = inputTokens.removeFirst();
                        boolean b1 = inputTokens.isEmpty() && value.startsWith(currentInput.value());
                        boolean b2 = !inputTokens.isEmpty() && value.equals(currentInput.value());
                        if (b1 || b2) {
                            retVal.add(Pair.create(new ContentToken(subs), token.getValue()));
                            match = true;
                            myTokens.addFirst(lookAheadToken);
                            inputTokens.addFirst(currentInput);
                            break;
                        }

                    } while (!inputTokens.isEmpty());
                    subs.clear();
                    if (!match) return null;
                } else {
                    String tokenValue = token.getValue();
                    match = (inputTokens.isEmpty() && tokenValue.startsWith(currentInput.value())) ||
                            (!inputTokens.isEmpty() && tokenValue.equals(currentInput.value()));
                    if (!match) {
                        //maybe an inject
                        String inject = unwrapInject(currentInput.value());
                        match = (inputTokens.isEmpty() && tokenValue.startsWith(inject)) ||
                                (!inputTokens.isEmpty() && tokenValue.equals(inject));
                    }
                    if (!match) return null;
                    subs.add(currentInput);
                }
            }
            if (!match || !myTokens.isEmpty()) return null;
            if (!subs.isEmpty()) {
                retVal.add(Pair.create(new ContentToken(subs), (String) null));
                subs.clear();
            }
        }
        return retVal;
    }

    public List<String> textAccordingTo(List<Pair<ContentToken, String>> actualText) {
        Map<String, String> parameters = new HashMap<String, String>();

        for (Pair<ContentToken, String> pair : actualText) {
            if (pair.second != null) {
                parameters.put(pair.second, pair.first.value());
            }
        }
        List<String> retval = new ArrayList<String>();
        for (Token token : tokens) {
            String value = token.value().trim();
            if (token.isIdentifier) {
                String oldParameter = parameters.get(value);
                if (oldParameter != null) {
                    retval.add(oldParameter);
                }
            } else {
                retval.add(value);
            }
        }
        return retval;
    }

    public static class StringToken {
        private final String value;
        private final boolean identifier;

        public StringToken(String value, boolean identifier) {
            this.value = value;
            this.identifier = identifier;
        }

        public String getValue() {
            return value;
        }

        public boolean isIdentifier() {
            return identifier;
        }
    }

    public static class WeightChain {
        private String input;
        private int inputIndex;
        private int weight;
        private int tokenIndex = -1;
        private WeightChain next;

        public static WeightChain zero() {
            return new WeightChain();
        }

        public WeightChain last() {
            WeightChain last = this;
            WeightChain iter = this;
            while (iter != null) {
                if (!iter.isZero()) {
                    last = iter;
                }
                iter = iter.next;
            }
            return last;
        }

        public boolean isZero() {
            return weight == 0 && tokenIndex == -1;
        }

        public WeightChain getNext() {
            return next;
        }

        public int getTokenIndex() {
            return tokenIndex;
        }

        public boolean hasMoreWeightThan(WeightChain pair) {
            return weight > pair.weight;
        }

        @Override
        public String toString() {
            return "WeightChain [inputIndex=" + inputIndex + ", weight=" + weight + ", tokenIndex=" + tokenIndex + "]";
        }

        public void collectWeights() {
            int w = weight;
            WeightChain n = next;
            while (n != null) {
                if (!n.isZero()) {
                    w += n.weight;
                }
                n = n.next;
            }

            this.weight = w;
        }

        public List<String> tokenize() {
            List<String> parts = new ArrayList<String>();
            if (isZero()) {
                return parts;
            }

            int indexBeg = inputIndex;
            WeightChain n = next;
            while (n != null) {
                if (!n.isZero()) {
                    parts.add(input.substring(indexBeg, n.inputIndex));
                    indexBeg = n.inputIndex;
                }
                n = n.next;
            }
            parts.add(input.substring(indexBeg));

            return parts;
        }
    }

    public class Token {
        private final int offset;
        private final int length;
        private final boolean isIdentifier;

        public Token(int offset, int length, boolean isIdentifier) {
            this.offset = offset;
            this.length = length;
            this.isIdentifier = isIdentifier;
        }

        public String value() {
            return content.substring(offset, offset + length);
        }

        @Override
        public String toString() {
            return "<<" + (isIdentifier ? "$" : "") + value() + ">>";
        }

        public boolean regionMatches(int toffset, String other, int ooffset, int len) {
            try {
                return normalize(content, offset + toffset, len).equalsIgnoreCase(normalize(other, ooffset, len));
            } catch (final java.lang.StringIndexOutOfBoundsException e) {
                return false;
            }
        }

        private String normalize(final String input, final int offset, final int len) {
            return input.substring(offset, offset + len).replaceAll("\\s+", "");
        }

        public int getOffset() {
            return offset;
        }

        public int getLength() {
            return length;
        }

        public boolean isIdentifier() {
            return isIdentifier;
        }
    }

    public static class ContentToken {
        private String content;
        private final int start;
        private final int end;

        public ContentToken(String content) {
            this(content, 0, content.length() - 1);
        }

        public ContentToken(String content, int start, int end) {
            this.content = content;
            this.start = start;
            this.end = end;
        }

        public ContentToken(Collection<ContentToken> tokens) {
            Iterator<ContentToken> it = tokens.iterator();
            if (it.hasNext()) {
                ContentToken next = it.next();
                content = next.content;
                start = next.start;
                while (it.hasNext()) {
                    next = it.next();
                }
                end = next.end;
            } else {
                start = 0;
                end = 0;
            }
        }

        public String getContent() {
            return content;
        }

        public int getEnd() {
            return end;
        }

        public int getStart() {
            return start;
        }

        public int getLength() {
            return end - start + 1;
        }

        public String value() {
            return content.substring(start, end);
        }

        @Override
        public String toString() {
            return value();
        }
    }
}
