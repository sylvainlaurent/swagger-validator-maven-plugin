package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import static com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.VisitableModelFactory.createVisitableModel;

import java.util.Collections;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticErrorsCollector;

import io.swagger.models.Model;
import io.swagger.models.Swagger;

public class SemanticValidator {

    private final RequiredPropertiesValidator requiredPropertiesValidator;
    private final PathsValidator pathsValidator;

    private final Map<String, Model> definitions;
    private final ReferenceValidator referenceValidator;
    private final InheritanceChainPropertiesValidator inheritanceChainPropertiesValidator;

    private final SemanticErrorsCollector errorCollector = new SemanticErrorsCollector();

    public SemanticValidator(Swagger swagger) {
        definitions = swagger.getDefinitions() != null ? swagger.getDefinitions() : Collections.<String, Model>emptyMap();
        referenceValidator = new ReferenceValidator(definitions, errorCollector);
        requiredPropertiesValidator = new RequiredPropertiesValidator(errorCollector);
        inheritanceChainPropertiesValidator = new InheritanceChainPropertiesValidator(definitions, errorCollector);
        pathsValidator = new PathsValidator(swagger.getPaths(), errorCollector);
    }

    public SemanticValidationResult validate() {
        pathsValidator.validate();

        for (Map.Entry<String, Model> definition : definitions.entrySet()) {
            VisitableModel visitableModel = createVisitableModel(definition.getKey(), definition.getValue());
            visitableModel.accept(referenceValidator);
            visitableModel.accept(requiredPropertiesValidator);
            visitableModel.accept(inheritanceChainPropertiesValidator);
        }

        return errorCollector.semanticValidationResult();
    }
}
