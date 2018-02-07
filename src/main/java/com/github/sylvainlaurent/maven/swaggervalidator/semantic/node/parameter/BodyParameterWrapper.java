package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import io.swagger.models.parameters.BodyParameter;

public class BodyParameterWrapper extends ParameterWrapper<BodyParameter> {
    public BodyParameterWrapper(BodyParameter parameter) {
        super(parameter);
        super.setIn("body");
    }
}
