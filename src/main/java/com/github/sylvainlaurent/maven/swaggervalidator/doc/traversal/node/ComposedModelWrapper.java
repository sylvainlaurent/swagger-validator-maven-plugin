package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.ComposedModel;
import io.swagger.models.Model;
import io.swagger.models.RefModel;

public class ComposedModelWrapper implements VisitableModel {

    private final String modelName;
    private ComposedModel composedModel;

    public ComposedModelWrapper(String name, ComposedModel composedModel) {
        this.modelName = name;
        this.composedModel = composedModel;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    @Override
    public void accept(ModelVisitor modelVisitor) {
        modelVisitor.visit(this);
    }

    public List<RefModel> getInterfaces() {
        return composedModel.getInterfaces();
    }

    public List<Model> getAllOf() {
        return composedModel.getAllOf();
    }

    public Model getChild() {
        return composedModel.getChild();
    }
}
