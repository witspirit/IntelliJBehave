package com.github.kumaraman21.intellijbehave.settings;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class FileExtensionsValidator {
    private static final Pattern FILE_EXTENSIONS_PATTERN = Pattern.compile("^\\s*\\.[a-zA-Z0-9]+?\\s*(,\\s*\\.[a-zA-Z0-9]+?\\s*)*$");

    public boolean isValid(String input){
        String trimmedFileExtensions = StringUtils.trimToEmpty(input);
        return "".equals(trimmedFileExtensions) || FILE_EXTENSIONS_PATTERN.matcher(trimmedFileExtensions).matches();
    }
}
