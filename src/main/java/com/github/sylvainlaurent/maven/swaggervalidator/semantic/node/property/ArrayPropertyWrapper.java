package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import io.swagger.models.properties.ArrayProperty;

public class ArrayPropertyWrapper extends AbstractPropertyWrapper<ArrayProperty> {

    private VisitableProperty items;

    public ArrayPropertyWrapper(String propertyName, ArrayProperty property) {
        super(propertyName, property);
        items = VisitablePropertyFactory.createVisitableProperty("items", property.getItems());
    }

    @Override
    public void accept(ModelVisitor visitor) {
        visitor.visit(this);
    }

    public VisitableProperty getItems() {
        return items;
    }
}
