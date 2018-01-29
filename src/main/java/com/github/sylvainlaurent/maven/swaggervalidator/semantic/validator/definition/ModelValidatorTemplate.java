package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PropertyVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitedItemsHolder;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelValidatorTemplate implements ModelVisitor, PropertyVisitor, VisitableModelValidator {

    protected List<SemanticError> validationErrors = new ArrayList<>();
    protected VisitedItemsHolder holder = new VisitedItemsHolder();

    @Override
    public List<SemanticError> getErrors() {
        return validationErrors;
    }

    @Override
    public void validate(VisitableModel visitableModel) {
        visitableModel.accept(this);
    }

    @Override
    public void visit(ModelImplWrapper modelImplWrapper) {
        holder.push(modelImplWrapper);
        validate(modelImplWrapper);
        holder.pop();
    }

    @Override
    public void visit(RefModelWrapper refModelWrapper) {
        holder.push(refModelWrapper);
        validate(refModelWrapper);
        holder.pop();
    }

    @Override
    public void visit(ArrayModelWrapper arrayModelWrapper) {
        holder.push(arrayModelWrapper);
        validate(arrayModelWrapper);
        holder.pop();
    }

    @Override
    public void visit(ComposedModelWrapper composedModelWrapper) {
        holder.push(composedModelWrapper);
        validate(composedModelWrapper);
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

    protected void validate(ModelImplWrapper modelImplWrapper) {}

    protected void validate(RefModelWrapper refModelWrapper) {}

    protected void validate(ArrayModelWrapper arrayModelWrapper) {}

    protected void validate(ComposedModelWrapper composedModelWrapper) {}

    protected abstract void validate(ObjectPropertyWrapper objectProperty);

    protected abstract void validate(ArrayPropertyWrapper arrayProperty);

    protected abstract void validate(RefPropertyWrapper refPropertyWrapper);

}
