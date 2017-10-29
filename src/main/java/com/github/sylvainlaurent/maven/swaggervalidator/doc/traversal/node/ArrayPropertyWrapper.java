package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;

public class ArrayPropertyWrapper implements VisitableProperty {

    private final ArrayProperty arrayProperty;
    private final String propertyName;

    public ArrayPropertyWrapper(String propertyName, ArrayProperty arrayProperty) {
        this.arrayProperty = arrayProperty;
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

    public Property getItems() {
        return arrayProperty.getItems();
    }
}
