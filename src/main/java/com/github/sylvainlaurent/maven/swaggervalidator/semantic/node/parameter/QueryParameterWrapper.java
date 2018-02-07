package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import io.swagger.models.parameters.QueryParameter;

public class QueryParameterWrapper extends SerializableParameterWrapper<QueryParameter> {
    public QueryParameterWrapper(QueryParameter parameter) {
        super(parameter);
        super.setIn("query");
    }
}
