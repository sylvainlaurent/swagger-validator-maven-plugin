package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

public interface VisitableProperty {

    String getPropertyName();

    void accept(PropertyVisitor visitor);
}
