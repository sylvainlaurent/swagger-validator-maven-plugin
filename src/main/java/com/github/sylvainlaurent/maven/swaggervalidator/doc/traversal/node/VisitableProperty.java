package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.PropertyVisitor;

public interface VisitableProperty {

    public String getPropertyName();

    public void accept(PropertyVisitor visitor);
}
