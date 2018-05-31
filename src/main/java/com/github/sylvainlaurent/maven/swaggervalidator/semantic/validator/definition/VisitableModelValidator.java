package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.SwaggerValidator;

public interface VisitableModelValidator extends SwaggerValidator {

    default void validate(VisitableModel visitableModel) {
    }

    default void validate(ModelImplWrapper modelImplWrapper) {
    }

    default void validate(RefModelWrapper refModelWrapper) {
    }

    default void validate(ArrayModelWrapper arrayModelWrapper) {
    }

    default void validate(ComposedModelWrapper composedModelWrapper) {
    }
}
