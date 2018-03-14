package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import io.swagger.models.AbstractModel;
import io.swagger.models.ExternalDocs;
import io.swagger.models.properties.Property;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModelWrapper<T extends AbstractModel> implements VisitableModel {

    protected final T model;
    protected final String name;
    protected final Map<String, VisitableProperty> properties = new HashMap<>();

    AbstractModelWrapper(String name, T model) {
        this.name = name;
        this.model = model;

        if (model.getProperties() != null) {
            for (Map.Entry<String, Property> entry : model.getProperties().entrySet()) {
                properties.put(entry.getKey(), VisitablePropertyFactory.createVisitableProperty(entry.getKey(), entry.getValue()));
            }
        }
    }

    @Override
    public T getModel() {
        return model;
    }

    @Override
    public String getName() {
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
    public Map<String, VisitableProperty> getProperties() {
        return properties;
    }

    @Override
    public void accept(ModelVisitor visitor) {
        for (VisitableProperty p : properties.values()) {
            p.accept(visitor);
        }
    }
}
