package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableModelFactory.createVisitableModel;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.DefinitionSemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.util.Util;
import io.swagger.models.Model;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

public class InheritanceChainPropertiesValidator extends ModelValidatorTemplate {

    @Override
    public void validate(ModelImplWrapper modelImplWrapper) {
        List<String> objectProperties = new ArrayList<>(modelImplWrapper.getProperties().keySet());
        List<String> requiredProperties = modelImplWrapper.getRequired();

        validateDiscriminator(modelImplWrapper.getDiscriminator(), requiredProperties, objectProperties);
        validateProperties(objectProperties, requiredProperties);

        for (VisitableProperty<? extends Property> property : modelImplWrapper.getProperties().values()) {
            property.accept(this);
        }
    }

    private void validateDiscriminator(String discriminator, List<String> requiredProperties, List<String> properties) {
        if (isEmpty(discriminator)) {
            return;
        }

        if (!properties.contains(discriminator)) {
            validationErrors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                                                             "discriminator \"" + discriminator + "\" is not a property defined at this schema"));
        }

        if (!requiredProperties.contains(discriminator)) {
            validationErrors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                                                             "discriminator property \"" + discriminator + "\" is not marked as required"));
        }
    }

    private void validateProperties(Collection<String> objectProperties, List<String> required) {
        Set<String> duplicates = Util.findDuplicates(objectProperties);
        if (!duplicates.isEmpty()) {
            validationErrors.add(new DefinitionSemanticError(
                    holder.getCurrentPath(), "following properties are already defined in ancestors: " + duplicates));
        }

        duplicates = Util.findDuplicates(required);
        if (!duplicates.isEmpty()) {
            validationErrors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                                                             "required property is defined multiple times: " + duplicates));
        }

        if (objectProperties.containsAll(required)) {
            return;
        }

        List<String> requiredButNotDefinedProperties = new ArrayList<>(required);
        requiredButNotDefinedProperties.removeAll(objectProperties);
        validationErrors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                                                         "required properties are not defined as object properties: "
                                                                 + requiredButNotDefinedProperties));
    }

    @Override
    public void validate(ArrayModelWrapper arrayModelWrapper) {
        if (arrayModelWrapper.getModel().getItems() == null) {
            validationErrors
                    .add(new DefinitionSemanticError(holder.getCurrentPath(), "'items' must be defined for an array"));
            return;
        }
        arrayModelWrapper.getItems().accept(this);
    }

    @Override
    public void validate(ComposedModelWrapper composedModelWrapper) {
        InheritanceChainPropertiesValidator.AllPropertiesCollector allPropertiesCollector =
                new InheritanceChainPropertiesValidator.AllPropertiesCollector(
                composedModelWrapper);
        List<String> objectProperties = allPropertiesCollector.getProperties();
        List<String> requiredProperties = allPropertiesCollector.getRequiredProperties();
        validateProperties(objectProperties, requiredProperties);
        if (composedModelWrapper.getChild() != null) {
            for (VisitableProperty<? extends Property> property : createVisitableModel(null, composedModelWrapper.getChild()).getProperties()
                    .values()) {
                property.accept(this);
            }
        }
    }

    @Override
    public void validate(ObjectPropertyWrapper objectProperty) {
        validateProperties(objectProperty.getProperties().keySet(), objectProperty.getRequiredProperties());

        for (Map.Entry<String, Property> property : objectProperty.getProperties().entrySet()) {
            VisitablePropertyFactory.createVisitableProperty(property.getKey(), property.getValue()).accept(this);
        }
    }

    @Override
    public void validate(ArrayPropertyWrapper arrayProperty) {
        if (arrayProperty.getProperty().getItems() == null) {
            validationErrors
                    .add(new DefinitionSemanticError(holder.getCurrentPath(), "'items' must be defined for an array"));
            return;
        }

        arrayProperty.getItems().accept(this);
    }

    final class AllPropertiesCollector extends ModelValidatorTemplate {
        private List<String> properties = new ArrayList<>();
        private List<String> requiredProperties = new ArrayList<>();

        AllPropertiesCollector(VisitableModel root) {
            root.accept(this);
        }

        @Override
        public void validate(ModelImplWrapper modelImplWrapper) {
            properties.addAll(modelImplWrapper.getProperties().keySet());
            requiredProperties.addAll(modelImplWrapper.getRequired());
        }

        @Override
        public void validate(RefModelWrapper refModelWrapper) {
            String refName = refModelWrapper.getSimpleRef();
            if (holder.getVisitedItems().contains(refName)) {
                InheritanceChainPropertiesValidator.this.validationErrors
                        .add(new DefinitionSemanticError(InheritanceChainPropertiesValidator.this.holder.getCurrentPath(),
                                                         "cyclic reference '" + refName + "'."));
            } else {
                holder.push(refName);
                String ref = refModelWrapper.getSimpleRef();
                Model model = InheritanceChainPropertiesValidator.this.context.getDefinitions().get(ref);
                if (model != null) {
                    createVisitableModel(ref, model).accept(this);
                }
            }

        }

        @Override
        public void validate(ComposedModelWrapper composedModelWrapper) {
            if (composedModelWrapper.getChild() != null) {
                createVisitableModel(null, composedModelWrapper.getChild()).accept(this);
            }
            for (RefModel element : composedModelWrapper.getInterfaces()) {
                createVisitableModel(element.getSimpleRef(), element).accept(this);
            }
        }

        List<String> getProperties() {
            return properties;
        }

        List<String> getRequiredProperties() {
            return requiredProperties;
        }
    }
}