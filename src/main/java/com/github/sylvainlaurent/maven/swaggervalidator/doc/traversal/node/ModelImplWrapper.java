package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;

import java.util.List;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.ModelImpl;
import io.swagger.models.properties.Property;

public class ModelImplWrapper implements VisitableModel {

    private final String modelName;
    private final ModelImpl model;

    public ModelImplWrapper(String name, ModelImpl model) {
        this.modelName = name;
        this.model = model;
    }

    @SuppressWarnings("unchecked")
    public List<String> getRequired() {
        try {
            return (List<String>) readField(model, "required", true);
        } catch (IllegalAccessException ex) {
            return emptyList();
        }
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    public void accept(ModelVisitor modelVisitor) {
        modelVisitor.visit(this);
    }

    public Map<String, Property> getProperties() {
        return model.getProperties();
    }

    public String getDiscriminator() {
        return model.getDiscriminator();
    }
}
