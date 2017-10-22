package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.ExternalDocs;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;
import io.swagger.models.refs.RefFormat;

public class RefModelWrapper extends RefModel implements VisitableModel {

    private final String modelName;
    private RefModel refModel;

    public RefModelWrapper(String name, RefModel refModel) {
        this.modelName = name;
        this.refModel = refModel;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    public void accept(ModelVisitor modelVisitor) {
        modelVisitor.visit(this);
    }

    @Override
    public RefModel asDefault(String ref) {
        return refModel.asDefault(ref);
    }

    @Override
    @JsonIgnore
    public String getTitle() {
        return refModel.getTitle();
    }

    @Override
    public void setTitle(String title) {
        refModel.setTitle(title);
    }

    @Override
    @JsonIgnore
    public String getDescription() {
        return refModel.getDescription();
    }

    @Override
    public void setDescription(String description) {
        refModel.setDescription(description);
    }

    @Override
    @JsonIgnore
    public Map<String, Property> getProperties() {
        return refModel.getProperties();
    }

    @Override
    public void setProperties(Map<String, Property> properties) {
        refModel.setProperties(properties);
    }

    @Override
    @JsonIgnore
    public String getSimpleRef() {
        return refModel.getSimpleRef();
    }

    @Override
    public String get$ref() {
        return refModel.get$ref();
    }

    @Override
    public void set$ref(String ref) {
        refModel.set$ref(ref);
    }

    @Override
    @JsonIgnore
    public RefFormat getRefFormat() {
        return refModel.getRefFormat();
    }

    @Override
    @JsonIgnore
    public Object getExample() {
        return refModel.getExample();
    }

    @Override
    public void setExample(Object example) {
        refModel.setExample(example);
    }

    @Override
    @JsonIgnore
    public ExternalDocs getExternalDocs() {
        return refModel.getExternalDocs();
    }

    @Override
    public void setExternalDocs(ExternalDocs value) {
        refModel.setExternalDocs(value);
    }

    @Override
    public Object clone() {
        return refModel.clone();
    }

    @Override
    @JsonIgnore
    public Map<String, Object> getVendorExtensions() {
        return refModel.getVendorExtensions();
    }

    @Override
    public int hashCode() {
        return refModel.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return refModel.equals(obj);
    }

    @Override
    @JsonIgnore
    public String getReference() {
        return refModel.getReference();
    }

    @Override
    public void setReference(String reference) {
        refModel.setReference(reference);
    }
}
