package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import static java.util.Collections.synchronizedMap;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class ObjectPropertyRequiredStore {

    private static final Map<Object, List<String>> requiredObjectPropertiesMap = synchronizedMap(new IdentityHashMap<Object, List<String>>());

    public static List<String> get(Object objectProperty) {
        return requiredObjectPropertiesMap.get(objectProperty);
    }

    public static void storeRequiredProperties(Object objectProperty, List<String> required) {
        requiredObjectPropertiesMap.put(objectProperty, required);
    }
}
