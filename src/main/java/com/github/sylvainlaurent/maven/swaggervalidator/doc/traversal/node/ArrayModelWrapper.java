package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.ArrayModel;
import io.swagger.models.properties.Property;

public class ArrayModelWrapper extends ArrayModel implements VisitableModel {

    private final String modelName;
    private final ArrayModel arrayModel;

    public ArrayModelWrapper(String name, ArrayModel arrayModel) {
        this.modelName = name;
        this.arrayModel = arrayModel;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    public void accept(ModelVisitor modelVisitor) {
        modelVisitor.visit(this);
    }

    @Override
    public ArrayModel description(String description) {
        return arrayModel.description(description);
    }

    @Override
    public ArrayModel items(Property items) {
        return arrayModel.items(items);
    }

    @Override
    public ArrayModel minItems(int minItems) {
        return arrayModel.minItems(minItems);
    }

    @Override
    public ArrayModel maxItems(int maxItems) {
        return arrayModel.maxItems(maxItems);
    }

    @Override
    public String getType() {
        return arrayModel.getType();
    }

    @Override
    public void setType(String type) {
        arrayModel.setType(type);
    }

    @Override
    public String getDescription() {
        return arrayModel.getDescription();
    }

    @Override
    public void setDescription(String description) {
        arrayModel.setDescription(description);
    }

    @Override
    public Property getItems() {
        return arrayModel.getItems();
    }

    @Override
    public void setItems(Property items) {
        arrayModel.setItems(items);
    }

    @Override
    public Map<String, Property> getProperties() {
        return arrayModel.getProperties();
    }

    @Override
    public void setProperties(Map<String, Property> properties) {
        arrayModel.setProperties(properties);
    }

    @Override
    public Object getExample() {
        return arrayModel.getExample();
    }

    @Override
    public void setExample(Object example) {
        arrayModel.setExample(example);
    }

    @Override
    public Integer getMinItems() {
        return arrayModel.getMinItems();
    }

    @Override
    public void setMinItems(Integer minItems) {
        arrayModel.setMinItems(minItems);
    }

    @Override
    public Integer getMaxItems() {
        return arrayModel.getMaxItems();
    }

    @Override
    public void setMaxItems(Integer maxItems) {
        arrayModel.setMaxItems(maxItems);
    }

    @Override
    public boolean equals(Object o) {
        return arrayModel.equals(o);
    }

    @Override
    public int hashCode() {
        return arrayModel.hashCode();
    }

    @Override
    public Object clone() {
        return arrayModel.clone();
    }
}
