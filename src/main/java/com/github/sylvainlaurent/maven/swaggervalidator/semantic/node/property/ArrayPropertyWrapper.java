package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;

public class ArrayPropertyWrapper extends AbstractPropertyWrapper<ArrayProperty> {

    private VisitableProperty<? extends Property> items;

    public ArrayPropertyWrapper(String propertyName, ArrayProperty property) {
        super(propertyName, property);
        items = VisitablePropertyFactory.createVisitableProperty("items", property.getItems());
    }

    @Override
    public void accept(ModelVisitor visitor) {
        visitor.visit(this);
    }

    public VisitableProperty<? extends Property> getItems() {
        return items;
    }
}
