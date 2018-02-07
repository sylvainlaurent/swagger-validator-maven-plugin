package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import io.swagger.models.parameters.PathParameter;

public class PathParameterWraper extends SerializableParameterWrapper<PathParameter> {
    public PathParameterWraper(PathParameter parameter) {
        super(parameter);
        super.setIn("path");
    }
}
