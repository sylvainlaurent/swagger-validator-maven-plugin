package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.regex.Pattern.compile;

public class MediaType {

    private static final Set<String> CUSTOM_MEDIA_TYPES = new HashSet<>();

    private MediaType () {
        // private constructor
    }

    // adapted from https://github.com/lovell/media-type
    private static final Pattern RFC6838_MEDIA_TYPE_PATTERN =
            compile("^(application|audio|font|image|message|model|multipart|text|video|\\*)/([a-zA-Z0-9!#$%^&*_\\-+{}|'.`~]{1,127})(;.*)?$");

    public static void addCustomMediaTypes(String[] customMediaTypes) {
        if (customMediaTypes != null) {
            CUSTOM_MEDIA_TYPES.addAll(asList(customMediaTypes));
        }
    }

    public static boolean isValid(String mediaType) {
        return CUSTOM_MEDIA_TYPES.contains(mediaType) || RFC6838_MEDIA_TYPE_PATTERN.matcher(mediaType).matches();
    }
}
