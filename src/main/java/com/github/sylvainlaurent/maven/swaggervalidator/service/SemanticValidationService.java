package com.github.sylvainlaurent.maven.swaggervalidator.service;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.InheritanceChainPropertiesValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.ReferenceValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.RequiredPropertiesValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.VisitableModelValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.FormDataValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.OperationValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.PathValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.ResponseValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.SwaggerPathValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.util.Util;
import io.swagger.models.Model;
import io.swagger.models.Swagger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableModelFactory.createVisitableModel;
import static org.apache.commons.collections4.MapUtils.emptyIfNull;

public class SemanticValidationService {

    private final Map<String, Model> definitions;
    private ValidationContext context = new ValidationContext();

    private final Set<SwaggerPathValidator> pathValidators = new HashSet<>();
    private final Set<VisitableModelValidator> validators = new HashSet<>();

    public SemanticValidationService(Swagger swagger) {
        definitions = emptyIfNull(swagger.getDefinitions());
        validators.add(new ReferenceValidator());
        validators.add(new RequiredPropertiesValidator());
        validators.add(new InheritanceChainPropertiesValidator());
        pathValidators.add(new ResponseValidator());
        pathValidators.add(new FormDataValidator());
        pathValidators.add(new PathValidator());
        pathValidators.add(new OperationValidator());

        context.setPaths(emptyIfNull(swagger.getPaths()));
        context.setDefinitions(definitions);
    }

    public SemanticValidationService(Swagger swagger, String validatorsPackageName, String pathValidatorsPackageName) {
        this(swagger);
        if (validatorsPackageName != null) {
            validators.addAll(Util.createInstances(validatorsPackageName, VisitableModelValidator.class));
        }
        if (pathValidatorsPackageName != null) {
            pathValidators.addAll(Util.createInstances(pathValidatorsPackageName, SwaggerPathValidator.class));
        }
    }

    public List<SemanticError> validate() {
        Set<SemanticError> uniqueValidationErrors = new HashSet<>();

        for (SwaggerPathValidator validator : pathValidators) {
            validator.setValidationContext(context);
            validator.validate();
            uniqueValidationErrors.addAll(validator.getErrors());
        }

        for (Map.Entry<String, Model> definition : definitions.entrySet()) {
            VisitableModel visitableModel = createVisitableModel(definition.getKey(), definition.getValue());
            for (VisitableModelValidator validator : validators) {
                validator.setValidationContext(context);
                validator.validate(visitableModel);
                uniqueValidationErrors.addAll(validator.getErrors());
            }
        }

        return new ArrayList<>(uniqueValidationErrors);
    }
}
