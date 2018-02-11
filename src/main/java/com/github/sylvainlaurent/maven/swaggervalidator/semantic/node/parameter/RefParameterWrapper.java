package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import io.swagger.models.parameters.RefParameter;

public class RefParameterWrapper extends ParameterWrapper<RefParameter> {
    public RefParameterWrapper(RefParameter parameter) {
        super(parameter);
    }

    public String get$ref() {
        return getParameter().get$ref();
    }

    @Override
    public String getObjectPath() {
        return get$ref();
    }
}
