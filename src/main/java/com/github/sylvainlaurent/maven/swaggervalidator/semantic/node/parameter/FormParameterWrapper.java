package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import io.swagger.models.parameters.FormParameter;

public class FormParameterWrapper extends SerializableParameterWrapper<FormParameter> {

    public FormParameterWrapper(FormParameter parameter) {
        super(parameter);
        super.setIn("formData");
    }
}
