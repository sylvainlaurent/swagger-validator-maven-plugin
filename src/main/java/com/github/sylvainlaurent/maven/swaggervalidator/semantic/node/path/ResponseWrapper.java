package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path;

import java.util.HashMap;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.PathObject;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;

import io.swagger.models.Response;
import io.swagger.models.properties.Property;

public class ResponseWrapper implements PathObject {

    private Response response;
    private Map<String, VisitableProperty<? extends Property>> headers = new HashMap<>();
    private String objectPath;

    public ResponseWrapper(String operationType, Response response) {
        this.objectPath = operationType;
        this.response = response;
        if (response != null && response.getHeaders() != null) {
            for (Map.Entry<String, Property> entry : response.getHeaders().entrySet()) {
                this.headers.put(entry.getKey(),
                        VisitablePropertyFactory.createVisitableProperty("", entry.getValue()));
            }
        }
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public String getName() {
        return objectPath;
    }

    public VisitableProperty<? extends Property> getSchema() {
        return response.getSchema() == null ? null
                : VisitablePropertyFactory.createVisitableProperty("schema", response.getSchema());
    }

    public String getDescription() {
        return response == null ? null : response.getDescription();
    }

    public Map<String, Object> getExamples() {
        return response == null ? null : response.getExamples();
    }

    public Map<String, VisitableProperty<? extends Property>> getHeaders() {
        return headers;
    }

    public Map<String, Object> getVendorExtensions() {
        return response == null ? null : response.getVendorExtensions();
    }
}
