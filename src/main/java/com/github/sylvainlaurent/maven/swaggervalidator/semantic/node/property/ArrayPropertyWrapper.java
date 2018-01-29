package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PropertyVisitor;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;

public class ArrayPropertyWrapper extends AbstractPropertyWrapper<ArrayProperty> {

    public ArrayPropertyWrapper(String propertyName, ArrayProperty property) {
        super(propertyName, property);
    }

    @Override
    public void accept(PropertyVisitor visitor) {
        visitor.visit(this);
    }

    public Property getItems() {
        return property.getItems();
    }
}
