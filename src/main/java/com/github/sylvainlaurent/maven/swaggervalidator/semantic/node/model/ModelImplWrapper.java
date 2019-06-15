package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.Property;

public class ModelImplWrapper extends AbstractModelWrapper<ModelImpl> {

    private VisitableProperty<? extends Property> additionalProperties;

    public ModelImplWrapper(String name, ModelImpl model) {
        super(name, model);
        additionalProperties = VisitablePropertyFactory.createVisitableProperty("", model.getAdditionalProperties());
    }

    @SuppressWarnings("unchecked")
    public List<String> getRequired() {
        List<String> requiredProperties;
        try {
            requiredProperties = new ArrayList<>(emptyIfNull((List<String>) readField(model, "required", true)));
        } catch (IllegalAccessException ex) {
            requiredProperties = new ArrayList<>();
        }
        return requiredProperties;
    }

    public List<String> getReadOlyProperties() {
        return getProperties().values().stream().filter(VisitableProperty::getReadOnly).map(VisitableProperty::getName)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(ModelVisitor modelVisitor) {
        super.accept(modelVisitor);
        modelVisitor.visit(this);
    }

    public String getDiscriminator() {
        return model.getDiscriminator();
    }

    @Override
    public ModelImpl getModel() {
        return model;
    }

    public String getType() {
        return model.getType();
    }

    public String getFormat() {
        return model.getFormat();
    }

    public Boolean getAllowEmptyValue() {
        return model.getAllowEmptyValue();
    }

    public Boolean getUniqueItems() {
        return model.getUniqueItems();
    }

    public boolean isSimple() {
        return model.isSimple();
    }

    public String getDescription() {
        return model.getDescription();
    }

    public Object getExample() {
        return model.getExample();
    }

    public VisitableProperty<? extends Property> getAdditionalProperties() {
        return additionalProperties;
    }

    public Object getDefaultValue() {
        return model.getDefaultValue();
    }

    public List<String> get_enum() {
        return model.getEnum();
    }

    public BigDecimal getMinimum() {
        return model.getMinimum();
    }

    public BigDecimal getMaximum() {
        return model.getMaximum();
    }
}
