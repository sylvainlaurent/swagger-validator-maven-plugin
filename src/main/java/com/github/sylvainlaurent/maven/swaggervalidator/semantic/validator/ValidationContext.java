package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator;

import static org.apache.commons.collections4.MapUtils.emptyIfNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableParameterFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.models.parameters.Parameter;

public class ValidationContext {

    protected List<PathWrapper> paths = new ArrayList<>();
    protected Map<String, Model> definitions = new HashMap<>();
    private Swagger swagger;

    public List<PathWrapper> getPaths() {
        return paths;
    }

    public ValidationContext(Swagger swagger) {
        this.swagger = swagger;
        setPaths(emptyIfNull(swagger.getPaths()));
        setDefinitions(emptyIfNull(swagger.getDefinitions()));
    }

    public Swagger getSwagger() {
        return swagger;
    }

    private void setPaths(Map<String, Path> paths) {
        for (Map.Entry<String, Path> pathEntry : paths.entrySet()) {
            PathWrapper path = new PathWrapper(pathEntry.getKey(), pathEntry.getValue());
            this.paths.add(path);
        }
    }

    public Map<String, Model> getDefinitions() {
        return definitions;
    }

    private void setDefinitions(Map<String, Model> definitions) {
        this.definitions = definitions;
    }

    public Map<String, VisitableParameter<? extends Parameter>> getParameters() {
        Map<String, Parameter> parameters = swagger.getParameters();
        Map<String, VisitableParameter<? extends Parameter>> visitableParameters = new HashMap<>();
        for (Map.Entry<String, Parameter> entry : parameters.entrySet()) {
            VisitableParameter<? extends Parameter> visitableParameter = VisitableParameterFactory
                    .createParameter(entry.getValue());
            visitableParameters.put(entry.getKey(), visitableParameter);
        }
        return visitableParameters;
    }

    public Map<String, SecuritySchemeDefinition> getSecurityDefinitions() {
        return swagger.getSecurityDefinitions();
    }
}
