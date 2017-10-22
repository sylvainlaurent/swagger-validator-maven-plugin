package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.ModelVisitor;

import io.swagger.models.Model;

public interface VisitableModel extends Model {

    public String getModelName();

    public void accept(ModelVisitor visitor);
}
