package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import io.swagger.models.properties.RefProperty;

public class RefPropertyWrapper extends AbstractPropertyWrapper<RefProperty> {

    public RefPropertyWrapper(String propertyName, RefProperty property) {
        super(propertyName, property);
    }

    @Override
    public void accept(ModelVisitor visitor) {
        visitor.visit(this);
    }

    public String getSimpleRef() {
        return property.getSimpleRef() == null ? "" : property.getSimpleRef();
    }

}
