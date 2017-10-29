package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.RefModel;

public class RefModelWrapper implements VisitableModel {

    private final String modelName;
    private RefModel refModel;

    public RefModelWrapper(String name, RefModel refModel) {
        this.modelName = name;
        this.refModel = refModel;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    @Override
    public void accept(ModelVisitor modelVisitor) {
        modelVisitor.visit(this);
    }

    public String getSimpleRef() {
        return refModel.getSimpleRef();
    }
}
