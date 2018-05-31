package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;

import io.swagger.models.parameters.Parameter;

public interface PathVisitor {
    void visit(List<PathWrapper> paths);

    void visit(PathWrapper path);

    void visit(OperationWrapper operation);

    void visit(ResponseWrapper response);

    <T extends Parameter> void visit(VisitableParameter<T> parameter);
}
