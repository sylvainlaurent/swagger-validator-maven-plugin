package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

public class ObjectPropertyRequiredStore {

    private static final Map<Object, List<String>> requiredObjectPropertiesMap = synchronizedMap(new IdentityHashMap<>());

    public static List<String> get(Object objectProperty) {
        return requiredObjectPropertiesMap.get(objectProperty);
    }

    public static void storeRequiredProperties(Object objectProperty, List<String> required) {
        requiredObjectPropertiesMap.put(objectProperty, required);
    }
}
