package com.github.kumaraman21.intellijbehave.utility;

import org.apache.commons.lang.LocaleUtils;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class LocalizedStorySupport {

    private static final Pattern LANGUAGE_PATTERN = Pattern.compile("language:[ ]*([a-z]{2}(_[A-Z]{2}(_[^ ]+)?)?)");

    /**
     * Returns the language directive if it exists.
     * <p/>
     * The methods checks if the {@link CharSequence} contains a language directive,
     * if so it returns the corresponding locale as a {@link String} otherwise returns
     * <code>null</code>.
     *
     * @param charSeq the sequence where the language directive is searched
     * @return the defined locale or <code>null</code>
     */
    public static String checkForLanguageDefinition(CharSequence charSeq) {
        Matcher matcher = LANGUAGE_PATTERN.matcher(charSeq);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Returns the {@link LocalizedKeywords} that corresponds to the given locale. If
     * the locale provided in invalid, {@link Locale#ENGLISH} is used.
     *
     * @param localeAsString the locale for which the {@link LocalizedKeywords} must
     *                       be returned
     */
    @NotNull
    public LocalizedKeywords getKeywords(String localeAsString) {
        Locale locale;
        try {
            locale = LocaleUtils.toLocale(localeAsString);
        } catch (Exception e) {
            locale = Locale.ENGLISH;
        }
        return new LocalizedKeywords(locale);
    }
}
