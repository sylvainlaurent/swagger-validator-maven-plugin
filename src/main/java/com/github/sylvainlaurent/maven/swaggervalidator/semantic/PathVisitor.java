package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;

import java.util.List;

public interface PathVisitor {
    void visit(List<PathWrapper> paths);
    void visit(PathWrapper path);
    void visit(OperationWrapper operation);
    void visit(ResponseWrapper response);
    void visit(VisitableParameter parameter);
}
