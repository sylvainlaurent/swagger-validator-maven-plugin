package com.github.sylvainlaurent.maven.swaggervalidator.service;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableModelFactory.createVisitableModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.MediaType;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.SwaggerValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.InheritanceChainPropertiesValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.ReferenceValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.VisitableModelValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.FormDataValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.MimeTypeValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.OperationParametersReferenceValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.OperationValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.PathValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.ResponseValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.SecurityValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.SwaggerPathValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.util.Util;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;

public class SemanticValidationService {

    private ValidationContext context;

    private final Set<SwaggerValidator> validators = new HashSet<>();
    private final Set<VisitableModelValidator> modelValidators = new HashSet<>();

    public SemanticValidationService(Swagger swagger) {
        context = new ValidationContext(swagger);

        modelValidators.add(new ReferenceValidator());
        modelValidators.add(new InheritanceChainPropertiesValidator());
        validators.add(new ResponseValidator());
        validators.add(new FormDataValidator());
        validators.add(new PathValidator());
        validators.add(new OperationValidator());
        validators.add(new OperationParametersReferenceValidator());
        validators.add(new SecurityValidator());
        validators.add(new MimeTypeValidator());
    }

    public SemanticValidationService(Swagger swagger, String validatorsPackageName, String pathValidatorsPackageName, String[] customMimeTypes) {
        this(swagger);
        if (validatorsPackageName != null) {
            modelValidators.addAll(Util.createInstances(validatorsPackageName, VisitableModelValidator.class));
        }
        if (pathValidatorsPackageName != null) {
            validators.addAll(Util.createInstances(pathValidatorsPackageName, SwaggerPathValidator.class));
        }
        MediaType.addCustomMimeTypes(customMimeTypes);
    }

    public List<SemanticError> validate() {

        Set<SemanticError> uniqueValidationErrors = new HashSet<>();

        MimeTypeValidator mimeTypeValidator = new MimeTypeValidator();
        mimeTypeValidator.setValidationContext(context);
        mimeTypeValidator.validateMimeTypes(new OperationWrapper("swagger-root", new Operation().consumes(context.getSwagger().getConsumes())
                .produces(context.getSwagger().getProduces()), null));
        uniqueValidationErrors.addAll(mimeTypeValidator.getErrors());

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
