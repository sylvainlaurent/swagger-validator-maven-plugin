package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.DefinitionSemanticError;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

import java.util.Map;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableModelFactory.createVisitableModel;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory.createVisitableProperty;

public class ReferenceValidator extends ModelValidatorTemplate {

    protected ValidationContext context;

    private void validateReferenceExistence(String reference) {
        if (reference == null || !context.getDefinitions().containsKey(reference)) {
            validationErrors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                    "reference " + reference + " cannot be found"));
        }
    }

    @Override
    protected void validate(ModelImplWrapper modelImplWrapper) {
        for (Map.Entry<String, VisitableProperty> property : modelImplWrapper.getProperties().entrySet()) {
            property.getValue().accept(this);
        }
    }

    @Override
    protected void validate(RefModelWrapper refModelWrapper) {
        validateReferenceExistence(refModelWrapper.getSimpleRef());
        validateReferencePath(refModelWrapper.getModel());
    }

    private void validateReferencePath(RefModel refModel) {
        if (refModel.get$ref().startsWith("#/_UNRESOLVABLE")) {
            validationErrors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                    "Could not resolve pointer: " + refModel.get$ref() + " does not exist in document"));
        }
    }

    @Override
    protected void validate(ArrayModelWrapper model) {
        if (model.getItems() == null) {
            validationErrors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                    "'items' must be defined for an array"));
        }
    }

    @Override
    protected void validate(ComposedModelWrapper composedModelWrapper) {
        for (RefModel refModel : composedModelWrapper.getInterfaces()) {
            createVisitableModel(refModel.getSimpleRef(), refModel).accept(this);
        }
    }

    @Override
    protected void validate(ObjectPropertyWrapper objectProperty) {
        for (Map.Entry<String, Property> property : objectProperty.getProperties().entrySet()) {
            createVisitableProperty(property.getKey(), property.getValue()).accept(this);
        }
    }

    @Override
    protected void validate(ArrayPropertyWrapper arrayProperty) {
        if (arrayProperty.getItems() == null) {
            validationErrors.add(new DefinitionSemanticError(holder.getCurrentPath(), "'items' must be defined for an array"));
        }
        createVisitableProperty("items", arrayProperty.getItems()).accept(this);
    }

    @Override
    protected void validate(RefPropertyWrapper refProperty) {
        validateReferenceExistence(refProperty.getSimpleRef());
    }

    @Override
    public void setValidationContext(ValidationContext context) {
        this.context = context;
    }
}
