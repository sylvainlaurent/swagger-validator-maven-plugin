package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.UnhandledPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.VisitableProperty;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;

public class VisitablePropertyFactory {

    public static VisitableProperty createVisitableProperty(String propertyName, Property property) {
        if (property instanceof ObjectProperty) {
            return new ObjectPropertyWrapper(propertyName, (ObjectProperty) property);
        }
        if (property instanceof ArrayProperty) {
            return new ArrayPropertyWrapper(propertyName, (ArrayProperty) property);
        }
        if (property instanceof RefProperty) {
            return new RefPropertyWrapper(propertyName, (RefProperty) property);
        }

        return new UnhandledPropertyWrapper(propertyName, property);
    }

}
