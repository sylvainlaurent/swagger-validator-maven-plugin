package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import io.swagger.models.properties.Property;

public class UnhandledPropertyWrapper extends AbstractPropertyWrapper<Property> {

    public UnhandledPropertyWrapper(String propertyName, Property property) {
        super(propertyName, property);
    }

    @Override
    public void accept(ModelVisitor visitor) {
        visitor.visit(this);
    }

}
