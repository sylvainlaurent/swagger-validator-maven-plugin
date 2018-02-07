package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import io.swagger.models.parameters.CookieParameter;

public class CookieParameterWrapper extends SerializableParameterWrapper<CookieParameter> {
    public CookieParameterWrapper(CookieParameter parameter) {
        super(parameter);
        super.setIn("cookie");
    }
}
