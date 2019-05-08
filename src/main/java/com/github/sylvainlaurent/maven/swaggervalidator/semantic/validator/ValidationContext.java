package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator;

import static org.apache.commons.collections4.MapUtils.emptyIfNull;
import static org.apache.commons.lang3.reflect.FieldUtils.readDeclaredStaticField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitableParameterFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.google.common.net.MediaType;
import edu.emory.mathcs.backport.java.util.Arrays;
import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.models.parameters.Parameter;

public class ValidationContext {
    private final Set<String> definedMimeTypes;
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
        definedMimeTypes = getDefinedMimeTypes();
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

    @SuppressWarnings("unchecked")
    private Set<String> getDefinedMimeTypes() {
        Set<String> set;
        try {
            set = ((Map<MediaType, MediaType>) readDeclaredStaticField(MediaType.class, "KNOWN_TYPES", true)).keySet().stream()
                    .map(x -> x.withoutParameters().toString()).collect(Collectors.toSet());
        } catch (IllegalAccessException e) {
            set = new HashSet<>();
        }
        return set;
    }

    public Set<String> getMimeTypes() {
        return definedMimeTypes;
    }

    public void addCustomMimeTypes(String[] customMimeTypes) {
        if (customMimeTypes != null) {
            definedMimeTypes.addAll(Arrays.asList(customMimeTypes));
        }
    }
}
