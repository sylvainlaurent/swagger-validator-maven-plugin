package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import io.swagger.models.Xml;
import io.swagger.models.properties.Property;

import java.util.Map;

public abstract class AbstractPropertyWrapper<T extends Property> implements VisitableProperty<T> {

    protected final T property;
    protected final String propertyName;

    public AbstractPropertyWrapper(String propertyName, T property) {
        this.property = property;
        this.propertyName = propertyName;
    }

    @Override
    public T getProperty() {
        return property;
    }

    @Override
    public String getType() {
        return property.getType();
    }

    @Override
    public String getFormat() {
        return property.getFormat();
    }

    @Override
    public String getTitle() {
        return property.getTitle();
    }

    @Override
    public String getDescription() {
        return property.getDescription();
    }

    @Override
    public Boolean getAllowEmptyValue() {
        return property.getAllowEmptyValue();
    }

    @Override
    public boolean getRequired() {
        return property.getRequired();
    }

    @Override
    public Object getExample() {
        return property.getExample();
    }

    @Override
    public boolean getReadOnly() {
        if (property.getReadOnly() == null) {
            return false;
        }
        return property.getReadOnly();
    }

    @Override
    public Integer getPosition() {
        return property.getPosition();
    }

    @Override
    public Xml getXml() {
        return property.getXml();
    }

    @Override
    public String getAccess() {
        return property.getAccess();
    }

    @Override
    public Map<String, Object> getVendorExtensions() {
        return property.getVendorExtensions();
    }

    @Override
    public String getName() {
        return propertyName;
    }

}
