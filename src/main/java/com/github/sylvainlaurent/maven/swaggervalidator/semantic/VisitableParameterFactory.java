package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.BodyParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.CookieParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.FormParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.HeaderParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.ParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.PathParameterWraper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.QueryParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.RefParameterWrapper;

import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.CookieParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.parameters.RefParameter;

public class VisitableParameterFactory {

    private VisitableParameterFactory() {
        // private constructor
    }

    @SuppressWarnings("unchecked")
    public static <T extends Parameter> VisitableParameter<T> createParameter(T parameter) {
        if (parameter instanceof BodyParameter) {
            return (VisitableParameter<T>) new BodyParameterWrapper((BodyParameter) parameter);
        }
        if (parameter instanceof CookieParameter) {
            return (VisitableParameter<T>) new CookieParameterWrapper((CookieParameter) parameter);
        }
        if (parameter instanceof FormParameter) {
            return (VisitableParameter<T>) new FormParameterWrapper((FormParameter) parameter);
        }
        if (parameter instanceof HeaderParameter) {
            return (VisitableParameter<T>) new HeaderParameterWrapper((HeaderParameter) parameter);
        }
        if (parameter instanceof PathParameter) {
            return (VisitableParameter<T>) new PathParameterWraper((PathParameter) parameter);
        }
        if (parameter instanceof QueryParameter) {
            return (VisitableParameter<T>) new QueryParameterWrapper((QueryParameter) parameter);
        }
        if (parameter instanceof RefParameter) {
            return (VisitableParameter<T>) new RefParameterWrapper((RefParameter) parameter);
        }
        return new ParameterWrapper<>(parameter);
    }
}
