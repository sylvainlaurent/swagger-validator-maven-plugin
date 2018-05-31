package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import java.math.BigDecimal;
import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;

import io.swagger.models.parameters.SerializableParameter;
import io.swagger.models.properties.Property;

public abstract class SerializableParameterWrapper<T extends SerializableParameter> extends ParameterWrapper<T> {

    public SerializableParameterWrapper(T parameter) {
        super(parameter);
    }

    public String getType() {
        return parameter.getType();
    }

    public VisitableProperty<? extends Property> getItems() {
        return VisitablePropertyFactory.createVisitableProperty("items", parameter.getItems());
    }

    public String getFormat() {
        return parameter.getFormat();
    }

    public String getCollectionFormat() {
        return parameter.getCollectionFormat();
    }

    public List<String> getEnum() {
        return parameter.getEnum();
    }

    public List<Object> getEnumValue() {
        return parameter.getEnumValue();
    }

    public Integer getMaxLength() {
        return parameter.getMaxLength();
    }

    public Integer getMinLength() {
        return parameter.getMinLength();
    }

    public Boolean isUniqueItems() {
        return parameter.isUniqueItems();
    }

    public Number getMultipleOf() {
        return parameter.getMultipleOf();
    }

    public Boolean isExclusiveMaximum() {
        return parameter.isExclusiveMaximum();
    }

    public Boolean isExclusiveMinimum() {
        return parameter.isExclusiveMinimum();
    }

    public BigDecimal getMaximum() {
        return parameter.getMaximum();
    }

    public BigDecimal getMinimum() {
        return parameter.getMinimum();
    }

    public Integer getMaxItems() {
        return parameter.getMaxItems();
    }

    public Integer getMinItems() {
        return parameter.getMinItems();
    }
}
