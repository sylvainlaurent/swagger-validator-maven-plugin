package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PropertyVisitor;
import io.swagger.models.properties.RefProperty;

public class RefPropertyWrapper extends AbstractPropertyWrapper<RefProperty> {

    public RefPropertyWrapper(String propertyName, RefProperty property) {
        super(propertyName, property);
    }

    @Override
    public void accept(PropertyVisitor visitor) {
        visitor.visit(this);
    }

    public String getSimpleRef() {
        return property.getSimpleRef();
    }

}
