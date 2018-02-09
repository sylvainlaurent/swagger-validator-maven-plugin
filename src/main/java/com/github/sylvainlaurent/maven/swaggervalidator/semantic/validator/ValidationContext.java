package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableParameterFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationContext {

    protected List<PathWrapper> paths = new ArrayList<>();
    protected Map<String, Model> definitions;
    private Swagger swagger;

    public List<PathWrapper> getPaths() {
        return paths;
    }

    public void setSwagger(Swagger swagger) {
        this.swagger = swagger;
        setPaths(swagger.getPaths());
        setDefinitions(swagger.getDefinitions());
    }

    public Swagger getSwagger() {
        return swagger;
    }

    public void setPaths(Map<String, Path> paths) {
        this.paths.clear();
        for (Map.Entry<String, Path> pathEntry : paths.entrySet()) {
            PathWrapper path = new PathWrapper(pathEntry.getKey(), pathEntry.getValue());
            this.paths.add(path);
        }
    }

    public Map<String, Model> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<String, Model> definitions) {
        this.definitions = definitions;
    }

    public Map<String, VisitableParameter> getParameters() {
        Map<String, Parameter> parameters = swagger.getParameters();
        Map<String, VisitableParameter> visitableParameters = new HashMap<>();
        for (Map.Entry<String,Parameter> entry : parameters.entrySet()) {
            VisitableParameter visitableParameter = VisitableParameterFactory.createParameter(entry.getValue());
            visitableParameters.put(entry.getKey(), visitableParameter);
        }
        return visitableParameters;
    }
}
