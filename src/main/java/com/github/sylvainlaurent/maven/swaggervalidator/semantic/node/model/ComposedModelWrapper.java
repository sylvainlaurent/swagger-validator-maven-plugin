package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import java.util.List;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;

import io.swagger.models.ComposedModel;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

public class ComposedModelWrapper extends AbstractModelWrapper<ComposedModel> {

    public ComposedModelWrapper(String name, ComposedModel model) {
        super(name, model);

        if (model.getChild() != null && model.getChild().getProperties() != null) {
            for (Map.Entry<String, Property> entry : model.getChild().getProperties().entrySet()) {
                properties.put(entry.getKey(),
                        VisitablePropertyFactory.createVisitableProperty(entry.getKey(), entry.getValue()));
            }
        }
    }

    @Override
    public void accept(ModelVisitor modelVisitor) {
        super.accept(modelVisitor);
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

    // returns only properties from this model, not parents
    @Override
    public Map<String, VisitableProperty<? extends Property>> getProperties() {
        return properties;
    }
}
