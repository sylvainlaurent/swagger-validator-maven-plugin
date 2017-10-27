package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

import io.swagger.models.Xml;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;

@SuppressWarnings("hiding")
public class ArrayPropertyWrapper extends ArrayProperty implements VisitableProperty {

    private final ArrayProperty arrayProperty;
    private final String propertyName;

    public ArrayPropertyWrapper(String propertyName, ArrayProperty arrayProperty) {
        this.arrayProperty = arrayProperty;
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

    public static boolean isType(String type) {
        return ArrayProperty.isType(type);
    }

    @Override
    public ArrayProperty xml(Xml xml) {
        return arrayProperty.xml(xml);
    }

    @Override
    public ArrayProperty uniqueItems() {
        return arrayProperty.uniqueItems();
    }

    @Override
    public ArrayProperty description(String description) {
        return arrayProperty.description(description);
    }

    @Override
    public ArrayProperty title(String title) {
        return arrayProperty.title(title);
    }

    @Override
    public ArrayProperty example(Object example) {
        return arrayProperty.example(example);
    }

    @Override
    public ArrayProperty items(Property items) {
        return arrayProperty.items(items);
    }

    @Override
    public ArrayProperty vendorExtension(String key, Object obj) {
        return arrayProperty.vendorExtension(key, obj);
    }

    @Override
    public ArrayProperty readOnly() {
        return arrayProperty.readOnly();
    }

    @Override
    public Property getItems() {
        return arrayProperty.getItems();
    }

    @Override
    public void setItems(Property items) {
        arrayProperty.setItems(items);
    }

    @Override
    public Boolean getUniqueItems() {
        return arrayProperty.getUniqueItems();
    }

    @Override
    public void setUniqueItems(Boolean uniqueItems) {
        arrayProperty.setUniqueItems(uniqueItems);
    }

    @Override
    public Integer getMaxItems() {
        return arrayProperty.getMaxItems();
    }

    @Override
    public void setMaxItems(Integer maxItems) {
        arrayProperty.setMaxItems(maxItems);
    }

    @Override
    public Integer getMinItems() {
        return arrayProperty.getMinItems();
    }

    @Override
    public void setMinItems(Integer minItems) {
        arrayProperty.setMinItems(minItems);
    }

    @Override
    public boolean equals(Object o) {
        return arrayProperty.equals(o);
    }

    @Override
    public int hashCode() {
        return arrayProperty.hashCode();
    }

}
