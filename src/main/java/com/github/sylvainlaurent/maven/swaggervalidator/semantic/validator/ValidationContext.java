package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import io.swagger.models.Model;
import io.swagger.models.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidationContext {

    protected List<PathWrapper> paths = new ArrayList<>();
    protected Map<String, Model> definitions;

    public List<PathWrapper> getPaths() {
        return paths;
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
}
