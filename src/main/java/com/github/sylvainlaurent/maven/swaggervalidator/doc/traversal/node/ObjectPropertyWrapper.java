package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import static java.util.Collections.synchronizedMap;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;

public class ObjectPropertyWrapper implements VisitableProperty {

    private static final Map<String, List<String>> requiredObjectPropertiesMap = synchronizedMap(new HashMap<String, List<String>>());

    private final ObjectProperty objectProperty;
    private final String propertyName;

    public ObjectPropertyWrapper(String propertyName, ObjectProperty objectProperty) {
        this.objectProperty = objectProperty;
        this.propertyName = propertyName;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public void accept(PropertyVisitor visitor) {
        visitor.visit(this);
    }

    public List<String> getRequiredProperties() {
        return emptyIfNull(requiredObjectPropertiesMap.get(key(objectProperty)));
    }

    public Map<String, Property> getProperties() {
        return objectProperty.getProperties();
    }

    public static void storeRequiredProperties(Object objectProperties, List<String> required) {
        requiredObjectPropertiesMap.put(key(objectProperties), required);
    }

    private static String key(Object objectProperties) {
        return String.valueOf(System.identityHashCode(objectProperties));
    }

}
