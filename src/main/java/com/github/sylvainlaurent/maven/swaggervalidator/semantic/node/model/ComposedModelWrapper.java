package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import io.swagger.models.ComposedModel;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String getDiscriminator() {
        if (model.getChild() == null || !(model.getChild() instanceof ModelImpl)) {
            return null;
        }
        return ((ModelImpl) model.getChild()).getDiscriminator();
    }

    public List<String> getRequired() {
        if (model.getChild() == null || !(model.getChild() instanceof ModelImpl)) {
            return new ArrayList<>();
        }
        // looks like it's always of this type
        List<String> required = ((ModelImpl) model.getChild()).getRequired();

        return required == null ? new ArrayList<>() : required;
    }

    // returns only properties from this model, not parents
    public Map<String, Property> getProperties() {
        if (model.getChild() == null || model.getChild().getProperties() == null) {
            return new HashMap<>();
        }
        return model.getChild().getProperties();
    }
}
