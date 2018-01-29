package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.SwaggerValidator;

public interface VisitableModelValidator extends SwaggerValidator {
    void validate(VisitableModel visitableModel);

    void validate(ModelImplWrapper modelImplWrapper);

    void validate(RefModelWrapper refModelWrapper);

    void validate(ArrayModelWrapper arrayModelWrapper);

    void validate(ComposedModelWrapper composedModelWrapper);
}
