package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefModelWrapper;

public abstract class AbstractModelVisitor implements ModelVisitor {

    protected VisitedItemsHolder holder = new VisitedItemsHolder();

    public void visit(ModelImplWrapper modelImplWrapper) {
        holder.push(modelImplWrapper);
        handle(modelImplWrapper);
        holder.pop();
    }

    public void visit(RefModelWrapper refModelWrapper) {
        holder.push(refModelWrapper);
        handle(refModelWrapper);
        holder.pop();
    }

    public void visit(ArrayModelWrapper arrayModelWrapper) {
        holder.push(arrayModelWrapper);
        handle(arrayModelWrapper);
        holder.pop();
    }

    public void visit(ComposedModelWrapper composedModelWrapper) {
        holder.push(composedModelWrapper);
        handle(composedModelWrapper);
        holder.pop();
    }

    abstract protected void handle(ModelImplWrapper modelImplWrapper);

    abstract protected void handle(RefModelWrapper refModelWrapper);

    abstract protected void handle(ArrayModelWrapper arrayModelWrapper);

    abstract protected void handle(ComposedModelWrapper composedModelWrapper);
}
