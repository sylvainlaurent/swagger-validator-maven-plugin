package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import static com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.VisitableModelFactory.createVisitableModel;
import static com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.VisitablePropertyFactory.createVisitableProperty;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.DISCRIMINATOR_NOT_DEFINED_AS_PROPERTY;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.DISCRIMINATOR_NOT_DEFINED_AS_REQUIRED_PROPERTY;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.ITEMS_PROPERTY_IS_NOT_DEFINED_IN_ARRAY;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.REQUIRED_PROPERTIES_ARE_DUPLICATED;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.REQUIRED_PROPERTIES_NOT_DEFINED_AS_OBJECT_PROPERTIES;
import static com.github.sylvainlaurent.maven.swaggervalidator.util.Util.findDuplicates;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class RequiredPropertiesValidator extends AbstractVisitor {

    private final SemanticErrorsCollector errorCollector;

    public RequiredPropertiesValidator(SemanticErrorsCollector errorCollector) {
        this.errorCollector = errorCollector;
    }

    private void validate(Collection<String> objectProperties, List<String> requiredProperties) {
        Collection<String> required = requiredProperties == null ? Collections.<String>emptyList() : requiredProperties;

        Set<String> duplicates = findDuplicates(required);
        if (!duplicates.isEmpty()) {
            errorCollector.addDefinitionError(
                REQUIRED_PROPERTIES_ARE_DUPLICATED,
                holder.getCurrentPath(),
                "required property is defined multiple times: " + duplicates);
        }

        if (objectProperties.containsAll(required)) {
            return;
        }

        List<String> requiredButNotDefinedProperties = new ArrayList<>(required);
        requiredButNotDefinedProperties.removeAll(objectProperties);
        errorCollector.addDefinitionError(
            REQUIRED_PROPERTIES_NOT_DEFINED_AS_OBJECT_PROPERTIES,
            holder.getCurrentPath(),
            "required properties are not defined as object properties: " + requiredButNotDefinedProperties);
    }

    private void validateDiscriminator(String discriminator, List<String> requiredProperties, List<String> properties) {

        if (isEmpty(discriminator)) {
            return;
        }

        if (!properties.contains(discriminator)) {
            errorCollector.addDefinitionError(
                DISCRIMINATOR_NOT_DEFINED_AS_PROPERTY,
                holder.getCurrentPath(),
                "discriminator \"" + discriminator + "\" is not a property defined at this schema");
        }

        if (!requiredProperties.contains(discriminator)) {
            errorCollector.addDefinitionError(
                DISCRIMINATOR_NOT_DEFINED_AS_REQUIRED_PROPERTY,
                holder.getCurrentPath(),
                "discriminator property \"" + discriminator + "\" is not marked as required");
        }
    }

    @Override
    protected void handle(ModelImplWrapper modelImplWrapper) {
        List<String> objectProperties =
            modelImplWrapper.getProperties() == null ? Collections.<String>emptyList() : new ArrayList<>(modelImplWrapper.getProperties().keySet());
        List<String> requiredProperties = modelImplWrapper.getRequired();

        validateDiscriminator(modelImplWrapper.getDiscriminator(), requiredProperties, objectProperties);
        validate(objectProperties, requiredProperties);

        if (modelImplWrapper.getProperties() != null) {
            for (Map.Entry<String, Property> property : modelImplWrapper.getProperties().entrySet()) {
                createVisitableProperty(property.getKey(), property.getValue()).accept(this);
            }
        }
    }

    @Override
    protected void handle(RefModelWrapper refModelWrapper) {
        // EMPTY
    }

    @Override
    protected void handle(ArrayModelWrapper arrayModelWrapper) {
        if (arrayModelWrapper.getItems() == null) {
            errorCollector.addDefinitionError(ITEMS_PROPERTY_IS_NOT_DEFINED_IN_ARRAY, holder.getCurrentPath(), "'items' must be defined for an array");
            return;
        }

        createVisitableProperty(arrayModelWrapper.getItems().getName(), arrayModelWrapper.getItems()).accept(this);
    }

    @Override
    protected void handle(ComposedModelWrapper composedModelWrapper) {
        for (Model element : composedModelWrapper.getAllOf()) {
            String modelName = element instanceof RefModel ? ((RefModel) element).getSimpleRef() : "child";
            createVisitableModel(modelName, element).accept(this);
        }
    }

    @Override
    protected void handle(ObjectPropertyWrapper objectProperty) {
        validate(objectProperty.getProperties().keySet(), objectProperty.getRequiredProperties());
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
        //EMPTY
    }
}
