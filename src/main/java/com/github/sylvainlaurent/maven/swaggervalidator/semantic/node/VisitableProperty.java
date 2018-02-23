package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PropertyVisitor;
import io.swagger.models.Xml;
import io.swagger.models.properties.Property;

import java.util.Map;

public interface VisitableProperty<T extends Property> extends VisitableObject<PropertyVisitor>, PathObject {

    T getProperty();

    String getType();

    String getFormat();

    String getTitle();

    String getDescription();

    Boolean getAllowEmptyValue();

    boolean getRequired();

    Object getExample();

    boolean getReadOnly();

    Integer getPosition();

    Xml getXml();

    String getAccess();

    Map<String, Object> getVendorExtensions();
}
