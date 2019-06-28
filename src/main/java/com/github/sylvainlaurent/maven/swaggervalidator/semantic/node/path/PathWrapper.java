package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.OperationConstants.*;
import static java.util.Arrays.asList;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableParameterFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.PathObject;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;

import io.swagger.models.Path;
import io.swagger.models.parameters.Parameter;

public class PathWrapper implements PathObject {

    private static final String SLASH = "/";
    private final List<PathElement> pathElements;

    private static final Pattern PATTERN = Pattern.compile("\\{((\\w+)?)\\}");
    private final String name;
    private Path path;
    private Map<String, OperationWrapper> operations = new HashMap<>();
    private List<VisitableParameter<Parameter>> parameters = new ArrayList<>();
    private List<String> requiredPathParameters = new ArrayList<>();

    public PathWrapper(String name, Path path) {
        this.pathElements = tokenize(name);

        this.name = name;
        this.path = path;
        if (path.getGet() != null) {
            operations.put(OPERATION_TYPE_GET, new OperationWrapper(OPERATION_TYPE_GET, path.getGet(), this));
        }
        if (path.getPut() != null) {
            operations.put(OPERATION_TYPE_PUT, new OperationWrapper(OPERATION_TYPE_PUT, path.getPut(), this));
        }
        if (path.getPost() != null) {
            operations.put(OPERATION_TYPE_POST, new OperationWrapper(OPERATION_TYPE_POST, path.getPost(), this));
        }
        if (path.getHead() != null) {
            operations.put(OPERATION_TYPE_HEAD, new OperationWrapper(OPERATION_TYPE_HEAD, path.getHead(), this));
        }
        if (path.getDelete() != null) {
            operations.put(OPERATION_TYPE_DELETE, new OperationWrapper(OPERATION_TYPE_DELETE, path.getDelete(), this));
        }
        if (path.getPatch() != null) {
            operations.put(OPERATION_TYPE_PATCH, new OperationWrapper(OPERATION_TYPE_PATCH, path.getPatch(), this));
        }
        if (path.getOptions() != null) {
            operations.put(OPERATION_TYPE_OPTIONS, new OperationWrapper(OPERATION_TYPE_OPTIONS, path.getOptions(), this));
        }

        if (path.getParameters() != null) {
            for (Parameter parameter : path.getParameters()) {
                parameters.add(VisitableParameterFactory.createParameter(parameter));
            }
        }

        initRequiredPathParameters();
    }

    private void initRequiredPathParameters() {
        Matcher matcher = PATTERN.matcher(this.name);
        while (matcher.find()) {
            requiredPathParameters.add(matcher.group(1));
        }
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String getName() {
        return name;
    }

    public OperationWrapper getGet() {
        return operations.get(OPERATION_TYPE_GET);
    }

    public OperationWrapper getPut() {
        return operations.get(OPERATION_TYPE_PUT);
    }

    public OperationWrapper getPost() {
        return operations.get(OPERATION_TYPE_POST);
    }

    public OperationWrapper getHead() {
        return operations.get(OPERATION_TYPE_HEAD);
    }

    public OperationWrapper getDelete() {
        return operations.get(OPERATION_TYPE_DELETE);
    }

    public OperationWrapper getPatch() {
        return operations.get(OPERATION_TYPE_PATCH);
    }

    public OperationWrapper getOptions() {
        return operations.get(OPERATION_TYPE_OPTIONS);
    }

    public List<VisitableParameter<Parameter>> getParameters() {
        return parameters;
    }

    public List<String> getRequiredPathParameters() {
        return requiredPathParameters;
    }

    public Map<String, OperationWrapper> getOperations() {
        return operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PathWrapper otherPath = (PathWrapper) o;

        return Objects.equals(pathElements, otherPath.pathElements);
    }

    @Override
    public int hashCode() {
        return pathElements != null ? pathElements.hashCode() : 0;
    }

    private interface PathElement {
        // marker interface
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

            return Objects.equals(token, pathToken.token);
        }

        @Override
        public int hashCode() {
            return token != null ? token.hashCode() : 0;
        }
    }

    private List<PathElement> tokenize(String inpath) {

        List<PathElement> pathPathElements = new ArrayList<>();

        String result = inpath;
        if (result.startsWith(SLASH)) {
            result = inpath.substring(1);
        }
        if (result.endsWith(SLASH)) {
            result = result.substring(0, inpath.length() - 1);
        }

        List<String> tokens = asList(result.split(SLASH));
        for (String token : tokens) {
            if (token.startsWith("{")) {
                pathPathElements.add(new PathParam());
            } else {
                pathPathElements.add(new PathToken(token));
            }
        }

        return pathPathElements;
    }

}
