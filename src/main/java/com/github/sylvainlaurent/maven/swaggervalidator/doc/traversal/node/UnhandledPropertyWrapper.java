package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

import io.swagger.models.Xml;
import io.swagger.models.properties.Property;

public class UnhandledPropertyWrapper implements Property, VisitableProperty {

    private final Property property;
    private String propertyName;

    public UnhandledPropertyWrapper(String propertyName, Property property) {
        this.property = property;
        this.propertyName = propertyName;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public void accept(PropertyVisitor visitor) {
        // EMPTY
    }

    @Override
    public Property title(String s) {
        return property.title(s);
    }

    @Override
    public Property description(String s) {
        return property.description(s);
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
    public void setTitle(String s) {
        property.setTitle(s);
    }

    @Override
    public String getDescription() {
        return property.getDescription();
    }

    @Override
    public void setDescription(String s) {
        property.setDescription(s);
    }

    @Override
    public Boolean getAllowEmptyValue() {
        return property.getAllowEmptyValue();
    }

    @Override
    public void setAllowEmptyValue(Boolean aBoolean) {
        property.setAllowEmptyValue(aBoolean);
    }

    @Override
    public String getName() {
        return property.getName();
    }

    @Override
    public void setName(String s) {
        property.setName(s);
    }

    @Override
    @JsonIgnore
    public boolean getRequired() {
        return property.getRequired();
    }

    @Override
    public void setRequired(boolean b) {
        property.setRequired(b);
    }

    @Override
    @JsonGetter
    public Object getExample() {
        return property.getExample();
    }

    @Override
    @JsonSetter
    public void setExample(Object o) {
        property.setExample(o);
    }

    @Override
    @Deprecated
    @JsonIgnore
    public void setExample(String s) {
        property.setExample(s);
    }

    @Override
    public Boolean getReadOnly() {
        return property.getReadOnly();
    }

    @Override
    public void setReadOnly(Boolean aBoolean) {
        property.setReadOnly(aBoolean);
    }

    @Override
    public Integer getPosition() {
        return property.getPosition();
    }

    @Override
    public void setPosition(Integer integer) {
        property.setPosition(integer);
    }

    @Override
    public Xml getXml() {
        return property.getXml();
    }

    @Override
    public void setXml(Xml xml) {
        property.setXml(xml);
    }

    @Override
    public void setDefault(String s) {
        property.setDefault(s);
    }

    @Override
    @JsonIgnore
    public String getAccess() {
        return property.getAccess();
    }

    @Override
    @JsonIgnore
    public void setAccess(String s) {
        property.setAccess(s);
    }

    @Override
    public Map<String, Object> getVendorExtensions() {
        return property.getVendorExtensions();
    }

    @Override
    public Property rename(String s) {
        return property.rename(s);
    }
}
