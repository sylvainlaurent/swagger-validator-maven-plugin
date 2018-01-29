package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import io.swagger.models.ComposedModel;
import io.swagger.models.Model;
import io.swagger.models.RefModel;

import java.util.List;

public class ComposedModelWrapper  extends AbstractModelWrapper<ComposedModel> {

    public ComposedModelWrapper(String name, ComposedModel model) {
        super(name, model);
    }

    @Override
    public void accept(ModelVisitor modelVisitor) {
        modelVisitor.visit(this);
    }

    public List<RefModel> getInterfaces() {
        return model.getInterfaces();
    }

    public List<Model> getAllOf() {
        return model.getAllOf();
    }

    public Model getChild() {
        return model.getChild();
    }

    public Model getParent() {
        return model.getParent();
    }

    public String getDescription() {
        return model.getDescription();
    }

    public Object getExample() {
        return model.getExample();
    }
}
