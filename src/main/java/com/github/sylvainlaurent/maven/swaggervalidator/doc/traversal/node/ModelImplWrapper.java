package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.ModelImpl;
import io.swagger.models.Xml;
import io.swagger.models.properties.Property;

public class ModelImplWrapper extends ModelImpl implements VisitableModel {

    private final String modelName;
    private final ModelImpl model;

    public ModelImplWrapper(String name, ModelImpl model) {
        this.modelName = name;
        this.model = model;
    }

    @Override
    public List<String> getRequired() {
        try {
            return (List<String>) readField(model, "required", true);
        } catch (IllegalAccessException ex) {
            return emptyList();
        }
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    @Override
    public ModelImpl _enum(List<String> value) {
        return model._enum(value);
    }

    @Override
    public ModelImpl _enum(String value) {
        return model._enum(value);
    }

    @Override
    public List<String> getEnum() {
        return model.getEnum();
    }

    @Override
    public void setEnum(List<String> _enum) {
        model.setEnum(_enum);
    }

    @Override
    public ModelImpl discriminator(String discriminator) {
        return model.discriminator(discriminator);
    }

    @Override
    public ModelImpl type(String type) {
        return model.type(type);
    }

    @Override
    public ModelImpl format(String format) {
        return model.format(format);
    }

    @Override
    public ModelImpl name(String name) {
        return model.name(name);
    }

    @Override
    public ModelImpl uniqueItems(Boolean uniqueItems) {
        return model.uniqueItems(uniqueItems);
    }

    @Override
    public ModelImpl allowEmptyValue(Boolean allowEmptyValue) {
        return model.allowEmptyValue(allowEmptyValue);
    }

    @Override
    public ModelImpl description(String description) {
        return model.description(description);
    }

    @Override
    public ModelImpl property(String key, Property property) {
        return model.property(key, property);
    }

    @Override
    public ModelImpl example(Object example) {
        return model.example(example);
    }

    @Override
    public ModelImpl additionalProperties(Property additionalProperties) {
        return model.additionalProperties(additionalProperties);
    }

    @Override
    public ModelImpl required(String name) {
        return model.required(name);
    }

    @Override
    public ModelImpl xml(Xml xml) {
        return model.xml(xml);
    }

    @Override
    public ModelImpl minimum(BigDecimal minimum) {
        return model.minimum(minimum);
    }

    @Override
    public ModelImpl maximum(BigDecimal maximum) {
        return model.maximum(maximum);
    }

    @Override
    public String getDiscriminator() {
        return model.getDiscriminator();
    }

    @Override
    public void setDiscriminator(String discriminator) {
        model.setDiscriminator(discriminator);
    }

    @Override
    @JsonIgnore
    public String getName() {
        return model.getName();
    }

    @Override
    public void setName(String name) {
        model.setName(name);
    }

    @Override
    public String getDescription() {
        return model.getDescription();
    }

    @Override
    public void setDescription(String description) {
        model.setDescription(description);
    }

    @Override
    @JsonIgnore
    public boolean isSimple() {
        return model.isSimple();
    }

    @Override
    public void setSimple(boolean isSimple) {
        model.setSimple(isSimple);
    }

    @Override
    public Property getAdditionalProperties() {
        return model.getAdditionalProperties();
    }

    @Override
    public void setAdditionalProperties(Property additionalProperties) {
        model.setAdditionalProperties(additionalProperties);
    }

    @Override
    public Boolean getAllowEmptyValue() {
        return model.getAllowEmptyValue();
    }

    @Override
    public void setAllowEmptyValue(Boolean allowEmptyValue) {
        model.setAllowEmptyValue(allowEmptyValue);
    }

    @Override
    public String getType() {
        return model.getType();
    }

    @Override
    public void setType(String type) {
        model.setType(type);
    }

    @Override
    public String getFormat() {
        return model.getFormat();
    }

    @Override
    public void setFormat(String format) {
        model.setFormat(format);
    }

    @Override
    public void addRequired(String name) {
        model.addRequired(name);
    }

    @Override
    public void setRequired(List<String> required) {
        model.setRequired(required);
    }

    @Override
    public void addProperty(String key, Property property) {
        model.addProperty(key, property);
    }

    @Override
    public Map<String, Property> getProperties() {
        return model.getProperties();
    }

    @Override
    public void setProperties(Map<String, Property> properties) {
        model.setProperties(properties);
    }

    @Override
    public Object getExample() {
        return model.getExample();
    }

    @Override
    public void setExample(Object example) {
        model.setExample(example);
    }

    @Override
    public Xml getXml() {
        return model.getXml();
    }

    @Override
    public void setXml(Xml xml) {
        model.setXml(xml);
    }

    @Override
    public Object getDefaultValue() {
        return model.getDefaultValue();
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        model.setDefaultValue(defaultValue);
    }

    @Override
    public BigDecimal getMinimum() {
        return model.getMinimum();
    }

    @Override
    public void setMinimum(BigDecimal minimum) {
        model.setMinimum(minimum);
    }

    @Override
    public BigDecimal getMaximum() {
        return model.getMaximum();
    }

    @Override
    public void setMaximum(BigDecimal maximum) {
        model.setMaximum(maximum);
    }

    @Override
    public Boolean getUniqueItems() {
        return model.getUniqueItems();
    }

    @Override
    public boolean equals(Object o) {
        return model.equals(o);
    }

    @Override
    public int hashCode() {
        return model.hashCode();
    }

    @Override
    public void setUniqueItems(Boolean uniqueItems) {
        model.setUniqueItems(uniqueItems);
    }

    @Override
    public Object clone() {
        return model.clone();
    }

    public void accept(ModelVisitor modelVisitor) {
        modelVisitor.visit(this);
    }

}
