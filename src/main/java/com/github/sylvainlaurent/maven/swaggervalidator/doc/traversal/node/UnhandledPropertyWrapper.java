package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

import io.swagger.models.properties.Property;

public class UnhandledPropertyWrapper implements VisitableProperty {

    private final Property property;
    private String propertyName;

    public UnhandledPropertyWrapper(String propertyName, Property property) {
        this.property = property;
        this.propertyName = propertyName;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public void accept(PropertyVisitor visitor) {
        // EMPTY
    }
}
