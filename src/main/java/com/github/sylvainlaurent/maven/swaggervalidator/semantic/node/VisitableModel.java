package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import io.swagger.models.Model;
import io.swagger.models.properties.Property;

import java.util.Map;

public interface VisitableModel extends VisitableObject<ModelVisitor>, PathObject {

    Model getModel();

    Map<String, Property> getProperties();
}
