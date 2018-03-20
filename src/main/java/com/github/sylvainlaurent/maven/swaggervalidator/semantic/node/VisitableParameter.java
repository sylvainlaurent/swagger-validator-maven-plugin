package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PathVisitor;
import io.swagger.models.parameters.Parameter;

public interface VisitableParameter<T extends Parameter> extends VisitableObject<PathVisitor>, PathObject {

    T getParameter();

    String getIn();

    String getDescription();

    boolean isRequired();

    String getAccess();

    String getPattern();

    Boolean getAllowEmptyValue();

    Boolean getReadOnly();

}
