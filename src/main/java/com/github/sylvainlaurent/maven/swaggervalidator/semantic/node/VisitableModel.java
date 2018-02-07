package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import io.swagger.models.Model;

public interface VisitableModel extends VisitableObject<ModelVisitor>, PathObject {

    Model getModel();

}
