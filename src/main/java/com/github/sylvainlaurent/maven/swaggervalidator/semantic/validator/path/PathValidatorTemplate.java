package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PathVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitedItemsHolder;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;

import java.util.List;

public abstract class PathValidatorTemplate implements PathVisitor, SwaggerPathValidator {

    protected VisitedItemsHolder holder = new VisitedItemsHolder();
    protected ValidationContext context;

    @Override
    public void setValidationContext(ValidationContext context) {
        this.context = context;
    }

    @Override
    public void validate() {
        visit(context.getPaths());
    }

    @Override
    public void visit(List<PathWrapper> paths) {
        holder.push("paths");
        validate(paths);
        for (PathWrapper path : context.getPaths()) {
            visit(path);
        }
        holder.pop();
    }

    @Override
    public void visit(PathWrapper path) {
        holder.push(path.getObjectPath());
        validate(path);
        path.getParameters().forEach(this::validate);
        path.getOperations().values().forEach(this::visit);
        holder.pop();
    }

    @Override
    public void visit(OperationWrapper operation) {
        holder.push(operation.getObjectPath());
        validate(operation);
        operation.getParameters().forEach(this::visit);
        operation.getResponses().forEach(this::visit);
        holder.pop();
    }

    @Override
    public void visit(ResponseWrapper response) {
        holder.push(response.getObjectPath());
        validate(response);
        holder.pop();
    }

    @Override
    public void visit(VisitableParameter parameter) {
        holder.push(parameter.getObjectPath());
        validate(parameter);
        holder.pop();
    }

}
