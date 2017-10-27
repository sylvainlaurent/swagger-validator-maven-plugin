package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

import io.swagger.models.properties.RefProperty;
import io.swagger.models.refs.RefFormat;

public class RefPropertyWrapper extends RefProperty implements VisitableProperty {

    private final RefProperty refProperty;
    private final String propertyName;

    public RefPropertyWrapper(String propertyName, RefProperty refProperty) {
        this.refProperty = refProperty;
        this.propertyName = propertyName;
        this.setType("ref");
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public void accept(PropertyVisitor visitor) {
        visitor.visit(this);
    }

    public static boolean isType(String type, String format) {
        return RefProperty.isType(type, format);
    }

    @Override
    public RefProperty asDefault(String ref) {
        return refProperty.asDefault(ref);
    }

    @Override
    public RefProperty description(@SuppressWarnings("hiding") String description) {
        return refProperty.description(description);
    }

    @Override
    @JsonIgnore
    public String getType() {
        return refProperty.getType();
    }

    @Override
    @JsonIgnore
    public void setType(String type) {
        if (refProperty != null) {
            refProperty.setType(type);
        }
    }

    @Override
    public String get$ref() {
        return refProperty.get$ref();
    }

    @Override
    public void set$ref(String ref) {
        refProperty.set$ref(ref);
    }

    @Override
    @JsonIgnore
    public RefFormat getRefFormat() {
        return refProperty.getRefFormat();
    }

    @Override
    @JsonIgnore
    public String getSimpleRef() {
        return refProperty.getSimpleRef();
    }

    @Override
    public int hashCode() {
        return refProperty.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return refProperty.equals(obj);
    }

}
