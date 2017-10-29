package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

import io.swagger.models.properties.RefProperty;

public class RefPropertyWrapper implements VisitableProperty {

    private final RefProperty refProperty;
    private final String propertyName;

    public RefPropertyWrapper(String propertyName, RefProperty refProperty) {
        this.refProperty = refProperty;
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

    public String getSimpleRef() {
        return refProperty.getSimpleRef();
    }

}
