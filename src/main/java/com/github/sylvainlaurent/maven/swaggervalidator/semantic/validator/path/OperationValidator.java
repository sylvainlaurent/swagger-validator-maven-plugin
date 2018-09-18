package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.DefinitionSemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import io.swagger.models.parameters.Parameter;

import static com.github.sylvainlaurent.maven.swaggervalidator.util.Util.findDuplicates;

public class OperationValidator extends PathValidatorTemplate {

    private List<SemanticError> errors = new ArrayList<>();
    private Set<String> duplicateOperationIds;

    @Override
    public void validate(OperationWrapper operation) {
        List<VisitableParameter<Parameter>> bodyParameters = operation.getParameters("body");
        List<VisitableParameter<Parameter>> formDataParameters = operation.getParameters("formData");

        if (bodyParameters.size() > 1) {
            errors.add(
                    new DefinitionSemanticError(holder.getCurrentPath(), "Multiple body parameters are not allowed."));
        }

        if (!bodyParameters.isEmpty() && !formDataParameters.isEmpty()) {
            errors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                    "Parameters cannot contain both 'body' and 'formData' types."));
        }

        if (operation.getOperationId() != null && duplicateOperationIds.contains(operation.getOperationId())) {
            errors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                    "Operations must have unique operationIds. Duplicate: " + operation.getOperationId()));
        }
    }

    @Override
    public void validate(List<PathWrapper> paths) {
        // save duplicates at this point to have pretty error messages added later
        collectDuplicateOperationIds(paths);
    }

    private void collectDuplicateOperationIds(List<PathWrapper> paths) {
        List<String> operationIds = paths.stream().flatMap(path -> path.getOperations().values().stream()
                .map(OperationWrapper::getOperationId))
                .collect(Collectors.toList());
        duplicateOperationIds = findDuplicates(operationIds);
    }

    @Override
    public List<SemanticError> getErrors() {
        return errors;
    }

}
