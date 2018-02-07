package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableParameterFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.PathObject;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import io.swagger.models.Path;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class PathWrapper implements PathObject {

    private static final String SLASH = "/";
    private final List<PathElement> pathElements;

    private static final Pattern PATTERN = Pattern.compile("\\{((\\w+)?)\\}");
    private final String name;
    private Path path;
    private Map<String, OperationWrapper> operations = new HashMap<>();
    private List<VisitableParameter> parameters = new ArrayList<>();
    private List<String> requiredPathParameters = new ArrayList<>();

    public PathWrapper(String name, Path path) {
        this.pathElements = tokenize(name);

        this.name = name;
        this.path = path;
        if (path.getGet() != null) {
            operations.put("get", new OperationWrapper("get", path.getGet()));
        }
        if (path.getPut() != null) {
            operations.put("put", new OperationWrapper("put", path.getPut()));
        }
        if (path.getPost() != null) {
            operations.put("post", new OperationWrapper("post", path.getPost()));
        }
        if (path.getHead() != null) {
            operations.put("head", new OperationWrapper("head", path.getHead()));
        }
        if (path.getDelete() != null) {
            operations.put("delete", new OperationWrapper("delete", path.getDelete()));
        }
        if (path.getPatch() != null) {
            operations.put("patch", new OperationWrapper("patch", path.getPatch()));
        }
        if (path.getOptions() != null) {
            operations.put("options", new OperationWrapper("options", path.getOptions()));
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
        while(matcher.find()) {
            requiredPathParameters.add(matcher.group(1));
        }
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String getObjectPath() {
        return name;
    }

    public OperationWrapper getGet() {
        return operations.get("get");
    }

    public OperationWrapper getPut() {
        return operations.get("put");
    }

    public OperationWrapper getPost() {
        return operations.get("post");
    }

    public OperationWrapper getHead() {
        return operations.get("head");
    }

    public OperationWrapper getDelete() {
        return operations.get("delete");
    }

    public OperationWrapper getPatch() {
        return operations.get("patch");
    }

    public OperationWrapper getOptions() {
        return operations.get("options");
    }

    public List<VisitableParameter> getParameters() {
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

        return pathElements != null ? pathElements.equals(otherPath.pathElements) : otherPath.pathElements == null;
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

            return token != null ? token.equals(pathToken.token) : pathToken.token == null;
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
            result = inpath.substring(1, inpath.length());
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
