package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import static com.github.sylvainlaurent.maven.swaggervalidator.util.Util.findDuplicates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.DefinitionSemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.util.Util;

import io.swagger.models.parameters.Parameter;

public class PathValidator extends PathValidatorTemplate {

    private PathWrapper currentPath;
    private List<SemanticError> errors = new ArrayList<>();
    private static final String TEMPLATE_REGEX = "\\{.*?\\}";
    private static final Pattern TEMPLATE_PATTERN = Pattern.compile(TEMPLATE_REGEX);

    @Override
    public void validate(PathWrapper path) {
        currentPath = path;
        validatePathString(path);
        validatePathParameterDefinitions(path);
        validateParametersUniqueness(path.getParameters());
        validateUrlUniqueness();
        validateUrlTemplates(path);
        validateQueryStrings(path);
    }

    /**
     * Paths cannot have query strings. E.g. /path?query=abc is illegal.
     */
    private void validateQueryStrings(PathWrapper path) {
        if (path.getName().contains("?")) {
            errors.add(new DefinitionSemanticError(holder.getCurrentPath(), "Query strings in paths are not allowed."));
        }
    }

    /**
     * Paths cannot have partial templates. E.g. /path/abc{123} is illegal.
     */
    private void validateUrlTemplates(PathWrapper path) {
        List<String> pathSections = Arrays.asList(path.getName().split("/"));
        for (String pathSection : pathSections) {
            Matcher matcher = TEMPLATE_PATTERN.matcher(pathSection);
            if (matcher.find() && !pathSection.replaceAll(TEMPLATE_REGEX, "").isEmpty()) {
                errors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                        "Partial path templating is not allowed."));
            }
        }
    }

    /**
     * /product/{id} and /product/{productId} must cause an error.
     */
    private void validateUrlUniqueness() {
        Set<PathWrapper> duplicatesPath = findDuplicates(context.getPaths());
        if (!duplicatesPath.isEmpty()) {
            errors.add(new DefinitionSemanticError(holder.getCurrentPath(), "Equivalent paths are not allowed."));
        }
    }

    /**
     * /product/{} is not valid.
     */
    private void validatePathString(PathWrapper path) {
        if (path.getRequiredPathParameters().stream().anyMatch(String::isEmpty)) {
            errors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                    "Empty path parameter declarations are not valid"));
        }
    }

    /**
     * Path parameter from path string must be defined at either path or operation level.
     */
    private void validatePathParameterDefinitions(PathWrapper path) {
        List<String> requiredPathParameters = path.getRequiredPathParameters();

        for (String requiredParam : requiredPathParameters) {
            if (!requiredParam.isEmpty() && parameterAbsentInPathParameters(requiredParam, path)) {
                for (OperationWrapper operation : path.getOperations().values()) {
                    if (parameterAbsentInOperation(requiredParam, operation)) {
                        errors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                                "Declared path parameter '" + requiredParam
                                        + "' needs to be defined as a path parameter at either the path "
                                        + "or operation level"));
                    }
                }
            }
        }
    }

    private boolean parameterAbsentInPathParameters(String requiredParam, PathWrapper path) {
        List<String> pathDefinitionParams = getPathDefinitionParameters(path);
        return !pathDefinitionParams.contains(requiredParam);
    }

    private boolean parameterAbsentInOperation(String requiredParam, OperationWrapper operation) {
        List<String> operationParameters = getOperationParameters(operation);
        return !operationParameters.contains(requiredParam);
    }

    private List<String> getOperationParameters(OperationWrapper operation) {
        return operation.getParameters().stream().filter(p -> p.getIn().equals("path")).map(VisitableParameter::getName)
                .collect(Collectors.toList());
    }

    private List<String> getPathDefinitionParameters(PathWrapper path) {
        return path.getParameters().stream().filter(p -> p.getIn().equals("path")).map(VisitableParameter::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void validate(OperationWrapper operation) {
        validateParametersUniqueness(operation.getParameters());
    }

    private void validateParametersUniqueness(List<VisitableParameter<Parameter>> parameters) {
        List<String> duplicates = Util.findDuplicates(parameters).stream().map(VisitableParameter::getName)
                .collect(Collectors.toList());
        if (!duplicates.isEmpty()) {
            errors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                    "should not have duplicate items: " + duplicates));
        }
    }

    @Override
    public void validate(ResponseWrapper response) {
    }

    @Override
    public <T extends Parameter> void validate(VisitableParameter<T> parameter) {
        if (parameter.getIn().equals("path")) {
            validatePathParameter(parameter, parameter.getName());
        }
    }

    private void validatePathParameter(VisitableParameter<? extends Parameter> parameter, String parameterName) {
        List<String> requiredPathParameters = currentPath.getRequiredPathParameters();
        if (!requiredPathParameters.contains(parameterName)) {
            errors.add(new DefinitionSemanticError(holder.getCurrentPath(), "Declared path parameter '" + parameterName
                    + "' needs to be defined as a path parameter at either the path " + "or operation level"));
        }
        if (!parameter.isRequired()) {
            errors.add(new DefinitionSemanticError(holder.getCurrentPath(),
                    "Path parameter '" + parameterName + "' must have 'required: true'."));
        }
    }

    @Override
    public List<SemanticError> getErrors() {
        return errors;
    }
}