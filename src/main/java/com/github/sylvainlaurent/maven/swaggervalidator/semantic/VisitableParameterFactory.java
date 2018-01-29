package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

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

    public static com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter createParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            return new BodyParameterWrapper((BodyParameter) parameter);
        }
        if (parameter instanceof CookieParameter) {
            return new CookieParameterWrapper((CookieParameter) parameter);
        }
        if (parameter instanceof FormParameter) {
            return new FormParameterWrapper((FormParameter) parameter);
        }
        if (parameter instanceof HeaderParameter) {
            return new HeaderParameterWrapper((HeaderParameter) parameter);
        }
        if (parameter instanceof PathParameter) {
            return new PathParameterWraper((PathParameter) parameter);
        }
        if (parameter instanceof QueryParameter) {
            return new QueryParameterWrapper((QueryParameter) parameter);
        }
        if (parameter instanceof RefParameter) {
            return new RefParameterWrapper((RefParameter) parameter);
        }
        return new ParameterWrapper<>(parameter);
    }
}
