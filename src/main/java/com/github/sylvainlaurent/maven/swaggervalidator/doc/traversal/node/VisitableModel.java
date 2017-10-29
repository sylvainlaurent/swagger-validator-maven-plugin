package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

public interface VisitableModel {

    String getModelName();

    void accept(ModelVisitor visitor);
}
