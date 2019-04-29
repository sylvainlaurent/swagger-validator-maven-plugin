package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableParameterFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.PathObject;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;

import io.swagger.models.Operation;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

public class OperationWrapper implements PathObject {

    private final String name;
    private final PathWrapper path;
    private Operation operation;

    private Map<String, Object> vendorExtensions = new LinkedHashMap<String, Object>();
    private List<String> tags = new ArrayList<>();
    private String summary;
    private String description;
    private String operationId;
    private List<String> consumes;
    private List<String> produces;
    private List<SchemeWrapper> schemes = new ArrayList<>();
    private List<Map<String, List<String>>> security = new ArrayList<>();
    private Boolean deprecated;
    private List<VisitableParameter<Parameter>> parameters = new ArrayList<>();
    private List<ResponseWrapper> responses = new ArrayList<>();

    public OperationWrapper(String name, Operation operation, PathWrapper path) {
        this.name = name;
        this.operation = operation;
        this.path = path;
        this.summary = operation.getSummary();
        this.description = operation.getDescription();
        this.operationId = operation.getOperationId();
        this.deprecated = operation.isDeprecated() == null ? false : operation.isDeprecated();

        if (operation.getTags() != null) {
            this.tags.addAll(operation.getTags());
        }
        if (operation.getConsumes() != null) {
            this.consumes = new ArrayList<>(operation.getConsumes());
        }
        if (operation.getProduces() != null) {
            this.produces = new ArrayList<>(operation.getProduces());
        }
        if (operation.getSecurity() != null) {
            this.security.addAll(operation.getSecurity());
        }

        for (Parameter parameter : operation.getParameters()) {
            parameters.add(VisitableParameterFactory.createParameter(parameter));
        }
        if (operation.getResponses() != null) {
            for (Map.Entry<String, Response> entry : operation.getResponses().entrySet()) {
                responses.add(new ResponseWrapper(entry.getKey(), entry.getValue()));
            }
        }
    }

    public PathWrapper getPath() {
        return path;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<String, Object> getVendorExtensions() {
        return vendorExtensions;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getOperationId() {
        return operationId;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public List<String> getProduces() {
        return produces;
    }

    public List<SchemeWrapper> getSchemes() {
        return schemes;
    }

    public List<Map<String, List<String>>> getSecurity() {
        return security;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public List<VisitableParameter<Parameter>> getParameters() {
        return parameters;
    }

    public List<VisitableParameter<Parameter>> getParameters(String type) {
        return parameters.stream().filter(p -> p.getIn().equals(type)).collect(Collectors.toList());
    }

    public List<ResponseWrapper> getResponses() {
        return responses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperationWrapper that = (OperationWrapper) o;

        return operation.equals(that.operation);
    }

    @Override
    public int hashCode() {
        return operation.hashCode();
    }
}
