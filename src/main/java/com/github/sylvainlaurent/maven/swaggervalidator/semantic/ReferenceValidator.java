package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import static com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.VisitableModelFactory.createVisitableModel;
import static com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.VisitablePropertyFactory.createVisitableProperty;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.ITEMS_PROPERTY_IS_NOT_DEFINED_IN_ARRAY;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.REFERENCE_DOESNT_POINT_TO_AN_EXISTING_DEFINITION;

import java.util.Collections;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.AbstractVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticErrorsCollector;

import io.swagger.models.Model;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

public class ReferenceValidator extends AbstractVisitor {

    private final Map<String, Model> definitions;
    private final SemanticErrorsCollector errorCollector;

    public ReferenceValidator(Map<String, Model> definitions, SemanticErrorsCollector errorCollector) {
        this.definitions = definitions;
        this.errorCollector = errorCollector;
    }

    private void validateSimpleReferenceToModel(String reference) {
        if (reference == null || !definitions.containsKey(reference)) {
            errorCollector.addDefinitionError(
                REFERENCE_DOESNT_POINT_TO_AN_EXISTING_DEFINITION,
                holder.getCurrentPath(),
                "reference " + reference + " cannot be found");
        }
    }

    @Override
    protected void handle(ModelImplWrapper modelImplWrapper) {
        Map<String, Property> modelProperties =
            modelImplWrapper.getProperties() == null ? Collections.<String, Property>emptyMap() : modelImplWrapper.getProperties();
        for (Map.Entry<String, Property> property : modelProperties.entrySet()) {
            createVisitableProperty(property.getKey(), property.getValue()).accept(this);
        }
    }

    @Override
    protected void handle(RefModelWrapper refModelWrapper) {
        validateSimpleReferenceToModel(refModelWrapper.getSimpleRef());
    }

    @Override
    protected void handle(ArrayModelWrapper model) {
        if (model.getItems() == null) {
            errorCollector.addDefinitionError(ITEMS_PROPERTY_IS_NOT_DEFINED_IN_ARRAY, holder.getCurrentPath(), "items cannot be undefined in an array");
            return;
        }

        createVisitableProperty("items", model.getItems()).accept(this);
    }

    @Override
    protected void handle(ComposedModelWrapper composedModelWrapper) {
        for (RefModel refModel : composedModelWrapper.getInterfaces()) {
            createVisitableModel(refModel.getSimpleRef(), refModel).accept(this);
        }
    }

    @Override
    protected void handle(ObjectPropertyWrapper objectProperty) {
        for (Map.Entry<String, Property> property : objectProperty.getProperties().entrySet()) {
            createVisitableProperty(property.getKey(), property.getValue()).accept(this);
        }
    }

    @Override
    protected void handle(ArrayPropertyWrapper arrayProperty) {
        if (arrayProperty.getItems() == null) {
            errorCollector.addDefinitionError(ITEMS_PROPERTY_IS_NOT_DEFINED_IN_ARRAY, holder.getCurrentPath(), "items cannot be undefined in an array");
            return;
        }
        createVisitableProperty("items", arrayProperty.getItems()).accept(this);
    }

    @Override
    protected void handle(RefPropertyWrapper refProperty) {
        validateSimpleReferenceToModel(refProperty.getSimpleRef());
    }
}
