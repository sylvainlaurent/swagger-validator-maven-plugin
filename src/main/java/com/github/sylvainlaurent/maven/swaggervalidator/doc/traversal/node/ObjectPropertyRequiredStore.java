package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import static java.util.Collections.synchronizedMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectPropertyRequiredStore {

    private static final Map<String, List<String>> requiredObjectPropertiesMap = synchronizedMap(new HashMap<String, List<String>>());

    public static List<String> get(Object objectProperties) {
        return requiredObjectPropertiesMap.get(key(objectProperties));
    }

    public static void storeRequiredProperties(Object objectProperties, List<String> required) {
        requiredObjectPropertiesMap.put(key(objectProperties), required);
    }

    private static String key(Object objectProperties) {
        return String.valueOf(System.identityHashCode(objectProperties));
    }

}
