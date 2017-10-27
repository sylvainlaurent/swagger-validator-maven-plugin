package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.ComposedModel;
import io.swagger.models.Model;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

public class ComposedModelWrapper extends ComposedModel implements VisitableModel {

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

    @Override
    public ComposedModel parent(Model model) {
        return composedModel.parent(model);
    }

    @Override
    public ComposedModel child(Model model) {
        return composedModel.child(model);
    }

    @Override
    public ComposedModel interfaces(List<RefModel> interfaces) {
        return composedModel.interfaces(interfaces);
    }

    @Override
    public String getDescription() {
        return composedModel.getDescription();
    }

    @Override
    public void setDescription(String description) {
        composedModel.setDescription(description);
    }

    @Override
    public Map<String, Property> getProperties() {
        return composedModel.getProperties();
    }

    @Override
    public void setProperties(Map<String, Property> properties) {
        composedModel.setProperties(properties);
    }

    @Override
    public Object getExample() {
        return composedModel.getExample();
    }

    @Override
    public void setExample(Object example) {
        composedModel.setExample(example);
    }

    @Override
    public List<Model> getAllOf() {
        return composedModel.getAllOf();
    }

    @Override
    public void setAllOf(List<Model> allOf) {
        composedModel.setAllOf(allOf);
    }

    @Override
    public Model getParent() {
        return composedModel.getParent();
    }

    @Override
    @JsonIgnore
    public void setParent(Model model) {
        composedModel.setParent(model);
    }

    @Override
    public Model getChild() {
        return composedModel.getChild();
    }

    @Override
    @JsonIgnore
    public void setChild(Model model) {
        composedModel.setChild(model);
    }

    @Override
    public List<RefModel> getInterfaces() {
        return composedModel.getInterfaces();
    }

    @Override
    @JsonIgnore
    public void setInterfaces(List<RefModel> interfaces) {
        composedModel.setInterfaces(interfaces);
    }

    @Override
    public Object clone() {
        return composedModel.clone();
    }

    @Override
    public int hashCode() {
        return composedModel.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return composedModel.equals(obj);
    }
}
