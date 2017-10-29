package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefModelWrapper;

public interface ModelVisitor {

    void visit(ModelImplWrapper modelImplWrapper);

    void visit(RefModelWrapper refModelWrapper);

    void visit(ArrayModelWrapper arrayModelWrapper);

    void visit(ComposedModelWrapper composedModelWrapper);
}
