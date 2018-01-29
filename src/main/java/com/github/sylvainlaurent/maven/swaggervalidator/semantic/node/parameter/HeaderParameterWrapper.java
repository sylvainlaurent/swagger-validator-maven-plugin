package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import io.swagger.models.parameters.HeaderParameter;

public class HeaderParameterWrapper extends SerializableParameterWrapper<HeaderParameter> {
    public HeaderParameterWrapper(HeaderParameter parameter) {
        super(parameter);
        super.setIn("header");
    }
}
