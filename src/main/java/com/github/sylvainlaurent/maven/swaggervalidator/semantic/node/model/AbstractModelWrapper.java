package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import io.swagger.models.AbstractModel;
import io.swagger.models.ExternalDocs;
import io.swagger.models.properties.Property;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModelWrapper<T extends AbstractModel> implements VisitableModel {

    protected final T model;
    protected final String name;

    AbstractModelWrapper(String name, T model) {
        this.name = name;
        this.model = model;
    }

    @Override
    public T getModel() {
        return model;
    }

    @Override
    public String getObjectPath() {
        return name;
    }

    public ExternalDocs getExternalDocs() {
        return model.getExternalDocs();
    }

    public String getReference() {
        return model.getReference();
    }

    public String getTitle() {
        return model.getTitle();
    }

    public Map<String, Object> getVendorExtensions() {
        return model.getVendorExtensions();
    }

    // returns only properties from this model, not parents
    public Map<String, Property> getProperties() {
        if (model.getProperties() == null) {
            return new HashMap<>();
        }
        return model.getProperties();
    }
}
