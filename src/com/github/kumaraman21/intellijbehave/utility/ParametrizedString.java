package com.github.kumaraman21.intellijbehave.utility;


import com.intellij.openapi.util.Pair;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class ParametrizedString implements Comparable<ParametrizedString> {

    private static Pattern compileParameterPattern(String parameterPrefix) {
        return Pattern.compile("(\\" + parameterPrefix + "\\w*)(\\W|\\Z)", Pattern.DOTALL);
    }

    private List<Token> tokens = new ArrayList<Token>();
    private List<Token> tokensWithoutIdentifier = new ArrayList<Token>();
    private final String content;
    private final String parameterPrefix;


    public ParametrizedString(String content) {
        this(content, "$");
    }

    public ParametrizedString(String content, String parameterPrefix) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        this.content = content;
        this.parameterPrefix = parameterPrefix;
        parse(compileParameterPattern(parameterPrefix));
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

    public boolean isSameAs(ParametrizedString other) {
        return other.content.equals(content);
    }

    private void parse(final Pattern parameterPattern) {
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
            add(new Token(start, end - start, true));
            prev = end;
        }
        if (prev < content.length()) {
            add(new Token(prev, content.length() - prev, false));
        }
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
            return content.substring(getOffset(), getOffset() + getLength());
        }

        @Override
        public String toString() {
            return "<<" + (isIdentifier() ? "$" : "") + value() + ">>";
        }

        public boolean regionMatches(int toffset, String other, int ooffset, int len) {
            try {
                return normalize(content, getOffset() + toffset, len).equalsIgnoreCase(normalize(other, ooffset, len));
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

    public Token getToken(int index) {
        return tokens.get(index);
    }

    public int getTokenCount() {
        return tokens.size();
    }

    public WeightChain calculateWeightChain(String input) {
        WeightChain chain = acceptsBeginning(0, input, 0);
        chain.input = input;
        chain.collectWeights();
        return chain;
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

    public static class WeightChain {
        public static WeightChain zero() {
            return new WeightChain();
        }

        private String input;
        private int inputIndex;
        private int weight;
        private int tokenIndex = -1;
        private WeightChain next;

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
            if (weight > pair.weight) {
                return true;
            }
            return false;
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

    public boolean isSameAs(String input) {
        return complete(input).equals(input);
    }

    public String complete(String input) {
        List<String> builder = new ArrayList<String>();
        List<Pair<String, Boolean>> myTokens = new ArrayList<Pair<String, Boolean>>();

        for (Token token : tokens) {
            String value = token.value();
            if (token.isIdentifier) {
                myTokens.add(new Pair<String, Boolean>(value.trim(), true));
            } else {
                StringTokenizer tok = new StringTokenizer(value);
                while (tok.hasMoreTokens()) {
                    myTokens.add(new Pair<String, Boolean>(tok.nextToken().trim(), false));
                }
            }
        }

        StringTokenizer tok = new StringTokenizer(input);
        String[] inputTokens = new String[tok.countTokens()];
        for (int i = 0; i < inputTokens.length; ++i) {
            inputTokens[i] = tok.nextToken();
        }
        Iterator<Pair<String, Boolean>> myTokenIt = myTokens.iterator();
        Iterator<String> myInputIt = Arrays.asList(inputTokens).iterator();
        boolean match = false;
        //for (int markInput = 0; markInput < inputTokens.length; ++markInput) {
        while (myInputIt.hasNext()) {
            Pair<String, Boolean> token = myTokenIt.next();// myTokens.get(markMine);
            String currentInput = myInputIt.next();
            if (token.second) {
                if (!myTokenIt.hasNext() || !myInputIt.hasNext()) {
                    builder.add(currentInput);
                    break;
                }
                //lookahead
                Pair<String, Boolean> lookAheadToken = myTokenIt.next();
                while (lookAheadToken.second && myTokenIt.hasNext()) {
                    lookAheadToken = myTokenIt.next();
                }
                String value = lookAheadToken.first;
                //find a matching input token
                match = false;
                List<String> subs = new ArrayList<String>();

                do {
                    subs.add(currentInput);
                    currentInput = myInputIt.next();
                    boolean b1 = !myInputIt.hasNext() && value.startsWith(currentInput);
                    boolean b2 = myInputIt.hasNext() && value.equals(currentInput);
                    if (b1 || b2) {
                        builder.addAll(subs);
                        builder.add(value);
                        match = true;
                        break;
                    }

                } while (myInputIt.hasNext());
                if (!match) return "";
            } else {
                match = (!myInputIt.hasNext() && token.first.startsWith(
                        currentInput)) || (myInputIt.hasNext() && token.first.equals(currentInput));
                if (!match) return "";
                builder.add(token.first);
            }
        }
        if (!match) return "";
        while (myTokenIt.hasNext()) {
            Pair<String, Boolean> token = myTokenIt.next();
            builder.add(token.second ? "$" + token.first : token.first);
        }

        return StringUtils.join(builder, " ");
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
    public List<Pair<String, String>> getTokensOf(String input) {
        List<Pair<String, String>> retVal = new ArrayList<Pair<String, String>>();
        Deque<Pair<String, Boolean>> myTokens = new ArrayDeque<Pair<String, Boolean>>();

        for (Token token : tokens) {
            String value = token.value();
            if (token.isIdentifier) {
                myTokens.add(new Pair<String, Boolean>(value.trim(), true));
            } else {
                StringTokenizer tok = new StringTokenizer(value);
                while (tok.hasMoreTokens()) {
                    myTokens.add(new Pair<String, Boolean>(tok.nextToken().trim(), false));
                }
            }
        }

        StringTokenizer tok = new StringTokenizer(input);
        Deque<String> inputTokens = new ArrayDeque<String>();

        while (tok.hasMoreTokens()) {
            inputTokens.add(tok.nextToken().trim());
        }
        //Iterator<Pair<String, Boolean>> myTokenIt = myTokens.iterator();
        //ListIterator<String> myInputIt = inputTokens.listIterator();
        boolean match = false;
        final List<String> subs = new ArrayList<String>();
        while (!inputTokens.isEmpty()) {
            Pair<String, Boolean> token = myTokens.removeFirst();
            String currentInput = inputTokens.removeFirst();
            if (token.second) {
                if (!subs.isEmpty()) {
                    retVal.add(Pair.create(StringUtils.join(subs, " "), (String) null));
                    subs.clear();
                }
                if (myTokens.isEmpty()) {
                    subs.add(currentInput);
                    while (!inputTokens.isEmpty()) {
                        currentInput = inputTokens.removeFirst();
                        subs.add(currentInput);
                    }
                    retVal.add(Pair.create(StringUtils.join(subs, " "), token.first));
                    subs.clear();
                    break;
                }
                if (inputTokens.isEmpty()) {
                    return null;
                }
                //lookahead
                Pair<String, Boolean> lookAheadToken = myTokens.removeFirst();
                while (lookAheadToken.second && !myTokens.isEmpty()) {
                    lookAheadToken = myTokens.removeFirst();
                }
                String value = lookAheadToken.first;
                //find a matching input token
                match = false;
                do {
                    subs.add(currentInput);
                    currentInput = inputTokens.removeFirst();
                    boolean b1 = inputTokens.isEmpty() && value.startsWith(currentInput);
                    boolean b2 = !inputTokens.isEmpty() && value.equals(currentInput);
                    if (b1 || b2) {
                        retVal.add(Pair.create(StringUtils.join(subs, " "), token.first));
                        match = true;
                        myTokens.addFirst(lookAheadToken);
                        inputTokens.addFirst(currentInput);
                        break;
                    }

                } while (!inputTokens.isEmpty());
                subs.clear();
                if (!match) return null;
            } else {
                match = (inputTokens.isEmpty() && token.first.startsWith(
                        currentInput)) || (!inputTokens.isEmpty() && token.first.equals(currentInput));
                if (!match) return null;
                subs.add(currentInput);
            }
        }
        if (!match || !myTokens.isEmpty()) return null;
        if (!subs.isEmpty()) {
            retVal.add(Pair.create(StringUtils.join(subs, " "), (String) null));
            subs.clear();
        }

        return retVal;
    }

    public List<String> textAccordingTo(List<Pair<String, String>> actualText) {
        Map<String, String> parameters = new HashMap<String, String>();

        for (Pair<String, String> pair : actualText) {
            if (pair.second != null) {
                parameters.put(pair.second, pair.first);
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
}
