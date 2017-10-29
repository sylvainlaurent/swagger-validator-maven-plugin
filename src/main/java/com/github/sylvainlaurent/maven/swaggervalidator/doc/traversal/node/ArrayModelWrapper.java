package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.ArrayModel;
import io.swagger.models.properties.Property;

public class ArrayModelWrapper implements VisitableModel {

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

    @Override
    public void accept(ModelVisitor modelVisitor) {
        modelVisitor.visit(this);
    }

    public Property getItems() {
        return arrayModel.getItems();
    }
}
