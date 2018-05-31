package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node;

import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;

import io.swagger.models.Model;
import io.swagger.models.properties.Property;

public interface VisitableModel extends VisitableObject<ModelVisitor>, PathObject {

    Model getModel();

    Map<String, VisitableProperty<? extends Property>> getProperties();
}
