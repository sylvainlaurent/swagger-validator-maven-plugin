package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import io.swagger.models.ExternalDocs;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

import java.util.Map;

public class RefModelWrapper implements VisitableModel {

    private final String name;
    private RefModel model;

    public RefModelWrapper(String name, RefModel model) {
        this.name = name;
        this.model = model;
    }

    @Override
    public String getObjectPath() {
        return name;
    }

    @Override
    public RefModel getModel() {
        return model;
    }

    @Override
    public void accept(ModelVisitor modelVisitor) {
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

    public Map<String, Property> getProperties() {
        return model.getProperties();
    }

    public Object getExample() {
        return model.getExample();
    }

    public String getTitle() {
        return model.getTitle();
    }
}
