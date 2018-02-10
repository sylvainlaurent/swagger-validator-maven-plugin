package com.github.sylvainlaurent.maven.swaggervalidator.util.validators.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.VisitableModelValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import java.util.List;

public class ModelValidatorImpl implements VisitableModelValidator {

    @Override
    public List<SemanticError> getErrors() {
        return null;
    }

    @Override
    public void setValidationContext(ValidationContext context) {

    }

    @Override
    public void validate() {

    }

    @Override
    public void validate(VisitableModel visitableModel) {

    }

    @Override
    public void validate(ModelImplWrapper modelImplWrapper) {

    }

    @Override
    public void validate(RefModelWrapper refModelWrapper) {

    }

    @Override
    public void validate(ArrayModelWrapper arrayModelWrapper) {

    }

    @Override
    public void validate(ComposedModelWrapper composedModelWrapper) {

    }
}
