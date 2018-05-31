package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;

import io.swagger.models.ArrayModel;
import io.swagger.models.properties.Property;

public class ArrayModelWrapper extends AbstractModelWrapper<ArrayModel> {

    private final VisitableProperty<? extends Property> items;

    public ArrayModelWrapper(String name, ArrayModel model) {
        super(name, model);
        items = VisitablePropertyFactory.createVisitableProperty("items", model.getItems());
    }

    @Override
    public void accept(ModelVisitor modelVisitor) {
        super.accept(modelVisitor);
        items.accept(modelVisitor);
        modelVisitor.visit(this);
    }

    public VisitableProperty<? extends Property> getItems() {
        return items;
    }

    public String getType() {
        return model.getType();
    }

    public String getDescription() {
        return model.getDescription();
    }

    public Object getExample() {
        return model.getExample();
    }

    public Integer getMinItems() {
        return model.getMinItems();
    }

    public Integer getMaxItems() {
        return model.getMaxItems();
    }
}
