package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import java.util.ArrayList;
import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitedItemsHolder;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.UnhandledPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

public abstract class ModelValidatorTemplate
        implements ModelVisitor, VisitableModelValidator, VisitablePropertyValidator {

    protected List<SemanticError> validationErrors = new ArrayList<>();
    protected VisitedItemsHolder holder = new VisitedItemsHolder();
    protected ValidationContext context;

    @Override
    public void setValidationContext(ValidationContext context) {
        this.context = context;
    }

    @Override
    public List<SemanticError> getErrors() {
        return validationErrors;
    }

    @Override
    public void validate(VisitableModel visitableModel) {
        holder.push(visitableModel);
        visitableModel.accept(this);
        holder.pop();
    }

    @Override
    public void visit(ModelImplWrapper modelImplWrapper) {
        validate(modelImplWrapper);
    }

    @Override
    public void visit(RefModelWrapper refModelWrapper) {
        validate(refModelWrapper);
    }

    @Override
    public void visit(ArrayModelWrapper arrayModelWrapper) {
        validate(arrayModelWrapper);
    }

    @Override
    public void visit(ComposedModelWrapper composedModelWrapper) {
        validate(composedModelWrapper);
    }

    @Override
    public void visit(UnhandledPropertyWrapper unhandledPropertyWrapper) {
        holder.push(unhandledPropertyWrapper);
        validate(unhandledPropertyWrapper);
        holder.pop();
    }

    @Override
    public void visit(ObjectPropertyWrapper objectProperty) {
        holder.push(objectProperty);
        validate(objectProperty);
        holder.pop();
    }

    @Override
    public void visit(ArrayPropertyWrapper arrayProperty) {
        holder.push(arrayProperty);
        validate(arrayProperty);
        holder.pop();
    }

    @Override
    public void visit(RefPropertyWrapper refPropertyWrapper) {
        holder.push(refPropertyWrapper);
        validate(refPropertyWrapper);
        holder.pop();
    }

}
