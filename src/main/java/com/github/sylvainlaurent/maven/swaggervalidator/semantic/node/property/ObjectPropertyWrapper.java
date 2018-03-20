package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.ObjectPropertyRequiredStore;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;

import java.util.List;
import java.util.Map;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

public class ObjectPropertyWrapper extends AbstractPropertyWrapper<ObjectProperty> {

    public ObjectPropertyWrapper(String propertyName, ObjectProperty property) {
        super(propertyName, property);
    }

    @Override
    public void accept(ModelVisitor visitor) {
        visitor.visit(this);
    }

    public List<String> getRequiredProperties() {
        return emptyIfNull(ObjectPropertyRequiredStore.get(property));
    }

    public Map<String, Property> getProperties() {
        return property.getProperties();
    }

}
