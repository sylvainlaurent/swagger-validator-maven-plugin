package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.API_PATH_IS_NOT_UNIQUE;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.OPERATION_CONTAINS_BOTH_FORM_AND_BODY_PARAMETER;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.OPERATION_CONTAINS_DUPLICATE_PARAMETERS;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.OPERATION_CONTAINS_MULTIPLE_BODY_PARAMETERS;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.PATH_PARAMS_DONT_MATCH_DEFINED_OPERATION_PARAMS;
import static com.github.sylvainlaurent.maven.swaggervalidator.util.Util.findDuplicates;
import static com.google.common.collect.FluentIterable.from;
import static com.google.inject.internal.util.Lists.newArrayList;
import static java.util.Arrays.asList;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticErrorsCollector;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import io.swagger.models.Operation;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;

public class PathsValidator {

    private static final Pattern PATH_PARAM_REGEX_PATTERN = compile("\\{(.+?)\\}");

    private final SemanticErrorsCollector errorCollector;
    private final Map<String, io.swagger.models.Path> paths;

    public PathsValidator(Map<String, io.swagger.models.Path> paths, SemanticErrorsCollector errorCollector) {
        this.paths = paths == null ? Collections.<String, io.swagger.models.Path>emptyMap() : paths;
        this.errorCollector = errorCollector;
    }

    public void validate() {

        List<Path> resolvedPaths = new ArrayList<>();
        for (String path : paths.keySet()) {
            resolvedPaths.add(new Path(path));
        }

        Set<Path> duplicatesPath = findDuplicates(resolvedPaths);
        if (!duplicatesPath.isEmpty()) {
            for (final Path duplicatePath : duplicatesPath) {
                List<Path> listsOfDuplicates = newArrayList(Collections2.filter(resolvedPaths, new Predicate<Path>() {
                    @Override
                    public boolean apply(Path path) {
                        return path.equals(duplicatePath);
                    }
                }));

                errorCollector.addError(
                    API_PATH_IS_NOT_UNIQUE,
                    "Issue with paths found: following paths are equal or equivalent, this is not allowed: " + listsOfDuplicates);
            }
        }

        for (Map.Entry<String, io.swagger.models.Path> path : paths.entrySet()) {
            for (Operation operation : path.getValue().getOperations()) {
                validateParameters(path.getKey(), operation);
            }
        }
    }

    private void validateParameters(String path, Operation operation) {

        checkValidityOfPathParams(path, operation);
        checkForDuplicateParameters(operation);
        checkForDuplicateBodyParameters(operation);
        checkForPresenceOfBodyAndFormParameters(operation);
    }

    private void checkForPresenceOfBodyAndFormParameters(Operation operation) {

        boolean bodyParamFound = false;
        boolean formParamFound = false;

        for (Parameter parameter : operation.getParameters()) {
            bodyParamFound |= parameter instanceof BodyParameter;
            formParamFound |= parameter instanceof FormParameter;
        }
        if (bodyParamFound && formParamFound) {
            errorCollector.addOperationError(
                OPERATION_CONTAINS_BOTH_FORM_AND_BODY_PARAMETER,
                operation.getOperationId(),
                "contains form and body parameters at same time");

        }
    }

    private void checkValidityOfPathParams(String path, Operation operation) {
        List<String> definedPathParams = new ArrayList<>();
        Matcher m = PATH_PARAM_REGEX_PATTERN.matcher(path);
        while (m.find()) {
            definedPathParams.add(m.group(1));
        }

        List<String> operationPathParameters = newArrayList(from(operation.getParameters()).filter(PathParameter.class)
            .transform(new Function<Parameter, String>(){
                @Override
                public String apply(Parameter parameter) {
                    return parameter.getName();
                }
            }));

        if (!definedPathParams.containsAll(operationPathParameters)) {
            errorCollector.addOperationError(
                PATH_PARAMS_DONT_MATCH_DEFINED_OPERATION_PARAMS,
                operation.getOperationId(),
                "operation parameters contain unexisting path params: " + findDifferences(
                    operationPathParameters,
                    definedPathParams));
        }
        if (!operationPathParameters.containsAll(definedPathParams)) {
            errorCollector.addOperationError(
                PATH_PARAMS_DONT_MATCH_DEFINED_OPERATION_PARAMS,
                operation.getOperationId(),
                "some path params are not present in the operation parameters: " + findDifferences(
                    definedPathParams,
                    operationPathParameters));
        }

    }

    private void checkForDuplicateBodyParameters(Operation operation) {
        int bodyParamCount = 0;
        for (Parameter parameter : operation.getParameters()) {
            bodyParamCount += parameter instanceof BodyParameter ? 1 : 0;
        }
        if (bodyParamCount > 1) {
            errorCollector.addOperationError(OPERATION_CONTAINS_MULTIPLE_BODY_PARAMETERS, operation.getOperationId(), "contains multiple body params");
        }
    }

    private void checkForDuplicateParameters(Operation operation) {
        List<String> duplicateParameters = findDuplicateParameterNames(operation.getParameters());
        if (!duplicateParameters.isEmpty()) {
            errorCollector.addOperationError(
                OPERATION_CONTAINS_DUPLICATE_PARAMETERS,
                operation.getOperationId(),
                "contains duplicate parameters: " + duplicateParameters);
        }
    }

    private List<String> findDuplicateParameterNames(List<Parameter> parameters) {
        Set<String> parameterKeys = new HashSet<>();
        List<String> duplicatesParameter = new ArrayList<>();
        for (Parameter parameter : emptyIfNull(parameters)) {
            String key = parameter.getName() + "_" + parameter.getIn();
            if (parameterKeys.contains(key)) {
                duplicatesParameter.add(parameter.getName());
            }
            parameterKeys.add(key);
        }
        return duplicatesParameter;
    }

    private Set<String> findDifferences(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);
        set1.removeAll(set2);
        return set1;
    }

    private interface PathElement {
        // marker interface
    }

    class Path {

        private static final String SLASH = "/";

        private final List<PathElement> pathElements;
        private final String path;

        Path(String path) {
            this.pathElements = tokenize(path);
            this.path = path;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Path path = (Path) o;

            return pathElements != null ? pathElements.equals(path.pathElements) : path.pathElements == null;
        }

        @Override
        public String toString() {
            return path;
        }

        @Override
        public int hashCode() {
            return pathElements != null ? pathElements.hashCode() : 0;
        }

        class PathParam implements PathElement {

            @Override
            public int hashCode() {
                return "{}".hashCode();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }

                return true;
            }
        }

        class PathToken implements PathElement {
            String token;

            PathToken(String token) {
                this.token = token;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }

                PathToken pathToken = (PathToken) o;

                return token != null ? token.equals(pathToken.token) : pathToken.token == null;
            }

            @Override
            public int hashCode() {
                return token != null ? token.hashCode() : 0;
            }
        }

        private List<PathElement> tokenize(String path) {

            List<PathElement> pathPathElements = new ArrayList<>();

            String result = path;
            if (result.startsWith(SLASH)) {
                result = path.substring(1, path.length());
            }
            if (result.endsWith(SLASH)) {
                result = result.substring(0, path.length() - 1);
            }

            List<String> tokens = asList(result.split(SLASH));
            for (String token : tokens) {
                if (token.startsWith("{")) {
                    pathPathElements.add(new Path.PathParam());
                } else {
                    pathPathElements.add(new Path.PathToken(token));
                }
            }

            return pathPathElements;
        }

    }

}
