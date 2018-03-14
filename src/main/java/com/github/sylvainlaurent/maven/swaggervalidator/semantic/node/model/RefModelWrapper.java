package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import io.swagger.models.ExternalDocs;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

import java.util.HashMap;
import java.util.Map;

public class RefModelWrapper implements VisitableModel {

    private final String name;
    private RefModel model;
    private final Map<String, VisitableProperty> properties = new HashMap<>();

    public RefModelWrapper(String name, RefModel model) {
        this.name = name;
        this.model = model;

        if (model.getProperties() != null) {
            for (Map.Entry<String, Property> entry : model.getProperties().entrySet()) {
                properties.put(entry.getKey(), VisitablePropertyFactory.createVisitableProperty(entry.getKey(), entry.getValue()));
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RefModel getModel() {
        return model;
    }

    @Override
    public void accept(ModelVisitor modelVisitor) {
        for (VisitableProperty p : properties.values()) {
            p.accept(modelVisitor);
        }
        modelVisitor.visit(this);
    }

    public String getSimpleRef() {
        return model.getSimpleRef();
    }

    public String getDescription() {
        return model.getDescription();
    }

    public ExternalDocs getExternalDocs() {
        return model.getExternalDocs();
    }

    public Map<String, VisitableProperty> getProperties() {
        return properties;
    }

    public Object getExample() {
        return model.getExample();
    }

    public String getTitle() {
        return model.getTitle();
    }
}
