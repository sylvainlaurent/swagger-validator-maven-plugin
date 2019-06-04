package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator;

import static org.apache.commons.lang3.reflect.FieldUtils.readDeclaredStaticField;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.models.Swagger;

public class MediaType {
    private static final Set<String> DEFINED_MEDIA_TYPES = getDefinedMimeTypes();

    @SuppressWarnings("unchecked")
    private static Set<String> getDefinedMimeTypes() {
        Set<String> set;
        try {
            set = ((Map<com.google.common.net.MediaType, com.google.common.net.MediaType>) readDeclaredStaticField(
                    com.google.common.net.MediaType.class, "KNOWN_TYPES", true)).keySet().stream()
                    .map(x -> x.withoutParameters().toString()).collect(Collectors.toSet());
        } catch (IllegalAccessException e) {
            set = new HashSet<>();
        }
        return set;
    }

    public static Set<String> getMimeTypes() {
        return DEFINED_MEDIA_TYPES;
    }

    public static void addCustomMimeTypes(String[] customMimeTypes) {
        if (customMimeTypes != null) {
            DEFINED_MEDIA_TYPES.addAll(Arrays.asList(customMimeTypes));
        }
    }
}
