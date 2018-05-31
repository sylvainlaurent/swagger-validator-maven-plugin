package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.UnhandledPropertyWrapper;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;

public class VisitablePropertyFactory {

    @SuppressWarnings("unchecked")
    public static <T extends Property> VisitableProperty<T> createVisitableProperty(String propertyName, T property) {
        if (property instanceof ObjectProperty) {
            return (VisitableProperty<T>) new ObjectPropertyWrapper(propertyName, (ObjectProperty) property);
        }
        if (property instanceof ArrayProperty) {
            return (VisitableProperty<T>) new ArrayPropertyWrapper(propertyName, (ArrayProperty) property);
        }
        if (property instanceof RefProperty) {
            return (VisitableProperty<T>) new RefPropertyWrapper(propertyName, (RefProperty) property);
        }

        return (VisitableProperty<T>) new UnhandledPropertyWrapper(propertyName, property);
    }
}
