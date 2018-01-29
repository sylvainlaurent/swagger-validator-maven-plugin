package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.FormParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class FormDataValidator extends PathValidatorTemplate {

    private List<SemanticError> validationErrors = new ArrayList<>();
    private OperationWrapper currentOperation;

    @Override
    public void validate(PathWrapper path) {
        path.getParameters().forEach(this::validate);
    }

    @Override
    public void validate(OperationWrapper operation) {
        currentOperation = operation;
        operation.getParameters().forEach(this::validate);
    }

    @Override
    public void validate(VisitableParameter parameter) {
        String in = parameter.getIn();
        if (in.equals("formData")) {
            String type = ((FormParameterWrapper) parameter).getType();
            List<String> consumes = currentOperation.getConsumes();
            if (type.equals("file") && !consumes.contains("`multipart/form-data")) {
                validationErrors.add(new SemanticError(holder.getCurrentPath(),
                        "Operations with parameters of 'type: file' must include 'multipart/form-data' in their 'consumes' property."));
            }
            if (consumes.isEmpty()) {
                validationErrors.add(new SemanticError(holder.getCurrentPath(),
                        "Operations with Parameters of 'in: formData' must include 'application/x-www-form-urlencoded' or 'multipart/form-data' in their 'consumes' property."));
            }
        }
    }

    @Override
    public List<SemanticError> getErrors() {
        return validationErrors;
    }
}
