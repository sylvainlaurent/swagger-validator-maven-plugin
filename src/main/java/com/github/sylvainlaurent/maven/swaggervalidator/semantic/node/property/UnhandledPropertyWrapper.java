package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PropertyVisitor;
import io.swagger.models.properties.Property;

public class UnhandledPropertyWrapper extends AbstractPropertyWrapper<Property> {

    public UnhandledPropertyWrapper(String propertyName, Property property) {
        super(propertyName, property);
    }

    @Override
    public void accept(PropertyVisitor visitor) {
        // do nothing
    }

}
