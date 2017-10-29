package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefPropertyWrapper;

public abstract class AbstractVisitor implements ModelVisitor, PropertyVisitor {

    protected VisitedItemsHolder holder = new VisitedItemsHolder();

    @Override
    public void visit(ModelImplWrapper modelImplWrapper) {
        holder.push(modelImplWrapper);
        handle(modelImplWrapper);
        holder.pop();
    }

    @Override
    public void visit(RefModelWrapper refModelWrapper) {
        holder.push(refModelWrapper);
        handle(refModelWrapper);
        holder.pop();
    }

    @Override
    public void visit(ArrayModelWrapper arrayModelWrapper) {
        holder.push(arrayModelWrapper);
        handle(arrayModelWrapper);
        holder.pop();
    }

    @Override
    public void visit(ComposedModelWrapper composedModelWrapper) {
        holder.push(composedModelWrapper);
        handle(composedModelWrapper);
        holder.pop();
    }

    @Override
    public void visit(ObjectPropertyWrapper objectProperty) {
        holder.push(objectProperty);
        handle(objectProperty);
        holder.pop();
    }

    @Override
    public void visit(ArrayPropertyWrapper arrayProperty) {
        holder.push(arrayProperty);
        handle(arrayProperty);
        holder.pop();
    }

    @Override
    public void visit(RefPropertyWrapper refPropertyWrapper) {
        holder.push(refPropertyWrapper);
        handle(refPropertyWrapper);
        holder.pop();
    }

    abstract protected void handle(ModelImplWrapper modelImplWrapper);

    abstract protected void handle(RefModelWrapper refModelWrapper);

    abstract protected void handle(ArrayModelWrapper arrayModelWrapper);

    abstract protected void handle(ComposedModelWrapper composedModelWrapper);

    abstract protected void handle(ObjectPropertyWrapper objectProperty);

    abstract protected void handle(ArrayPropertyWrapper arrayProperty);

    abstract protected void handle(RefPropertyWrapper refPropertyWrapper);

}
