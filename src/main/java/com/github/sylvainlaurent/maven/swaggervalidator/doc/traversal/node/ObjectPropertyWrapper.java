package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

import io.swagger.models.Xml;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;

@SuppressWarnings("hiding")
public class ObjectPropertyWrapper extends ObjectProperty implements VisitableProperty {

    private final ObjectProperty objectProperty;
    private final String propertyName;

    public ObjectPropertyWrapper(String propertyName, ObjectProperty objectProperty) {
        this.objectProperty = objectProperty;
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

    @Override
    public ObjectProperty vendorExtension(String key, Object obj) {
        return objectProperty.vendorExtension(key, obj);
    }

    public static boolean isType(String type) {
        return ObjectProperty.isType(type);
    }

    public static boolean isType(String type, String format) {
        return ObjectProperty.isType(type, format);
    }

    @Override
    public ObjectProperty properties(Map<String, Property> properties) {
        return objectProperty.properties(properties);
    }

    @Override
    public ObjectProperty property(String name, Property property) {
        return objectProperty.property(name, property);
    }

    @Override
    public ObjectProperty access(String access) {
        return objectProperty.access(access);
    }

    @Override
    public ObjectProperty description(String description) {
        return objectProperty.description(description);
    }

    @Override
    public ObjectProperty name(String name) {
        return objectProperty.name(name);
    }

    @Override
    public ObjectProperty title(String title) {
        return objectProperty.title(title);
    }

    @Override
    public ObjectProperty _default(String _default) {
        return objectProperty._default(_default);
    }

    @Override
    public ObjectProperty readOnly(boolean readOnly) {
        return objectProperty.readOnly(readOnly);
    }

    @Override
    public ObjectProperty required(boolean required) {
        return objectProperty.required(required);
    }

    @Override
    public ObjectProperty readOnly() {
        return objectProperty.readOnly();
    }

    @Override
    public Map<String, Property> getProperties() {
        return objectProperty.getProperties();
    }

    @Override
    @JsonGetter("required")
    public List<String> getRequiredProperties() {
        return objectProperty.getRequiredProperties();
    }

    @Override
    @JsonSetter("required")
    public void setRequiredProperties(List<String> required) {
        objectProperty.setRequiredProperties(required);
    }

    @Override
    public void setProperties(Map<String, Property> properties) {
        objectProperty.setProperties(properties);
    }

    @Override
    public ObjectProperty xml(Xml xml) {
        return objectProperty.xml(xml);
    }

    @Override
    public ObjectProperty example(String example) {
        return objectProperty.example(example);
    }
}
