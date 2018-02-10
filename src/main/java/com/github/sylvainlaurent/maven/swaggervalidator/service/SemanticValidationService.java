package com.github.sylvainlaurent.maven.swaggervalidator.service;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.SwaggerValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.InheritanceChainPropertiesValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.ReferenceValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.RequiredPropertiesValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.VisitableModelValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.*;
import com.github.sylvainlaurent.maven.swaggervalidator.util.Util;
import io.swagger.models.Model;
import io.swagger.models.Swagger;

import java.util.*;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableModelFactory.createVisitableModel;

public class SemanticValidationService {

    private ValidationContext context;

    private final Set<SwaggerValidator> validators = new HashSet<>();
    private final Set<VisitableModelValidator> modelValidators = new HashSet<>();

    public SemanticValidationService(Swagger swagger) {
        context = new ValidationContext(swagger);

        modelValidators.add(new ReferenceValidator());
        modelValidators.add(new RequiredPropertiesValidator());
        modelValidators.add(new InheritanceChainPropertiesValidator());
        validators.add(new ResponseValidator());
        validators.add(new FormDataValidator());
        validators.add(new PathValidator());
        validators.add(new OperationValidator());
        validators.add(new OperationParametersReferenceValidator());
        validators.add(new SecurityValidator());
    }

    public SemanticValidationService(Swagger swagger, String validatorsPackageName, String pathValidatorsPackageName) {
        this(swagger);
        if (validatorsPackageName != null) {
            modelValidators.addAll(Util.createInstances(validatorsPackageName, VisitableModelValidator.class));
        }
        if (pathValidatorsPackageName != null) {
            validators.addAll(Util.createInstances(pathValidatorsPackageName, SwaggerPathValidator.class));
        }
    }

    public List<SemanticError> validate() {
        Set<SemanticError> uniqueValidationErrors = new HashSet<>();

        for (SwaggerValidator validator : validators) {
            validator.setValidationContext(context);
            validator.validate();
            uniqueValidationErrors.addAll(validator.getErrors());
        }

        for (Map.Entry<String, Model> definition : context.getDefinitions().entrySet()) {
            VisitableModel visitableModel = createVisitableModel(definition.getKey(), definition.getValue());
            for (VisitableModelValidator validator : modelValidators) {
                validator.setValidationContext(context);
                validator.validate(visitableModel);
                uniqueValidationErrors.addAll(validator.getErrors());
            }
        }

        return new ArrayList<>(uniqueValidationErrors);
    }
}
