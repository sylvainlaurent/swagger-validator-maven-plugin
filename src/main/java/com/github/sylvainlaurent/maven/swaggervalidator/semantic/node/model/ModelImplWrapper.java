package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.ModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.Property;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitablePropertyFactory.createVisitableProperty;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;

public class ModelImplWrapper  extends AbstractModelWrapper<ModelImpl> {

    private VisitableProperty additionalProperties;

    public ModelImplWrapper(String name, ModelImpl model) {
        super(name, model);
        additionalProperties = VisitablePropertyFactory.createVisitableProperty("", model.getAdditionalProperties());
        if (model.getProperties() != null) {
            for (Map.Entry<String, Property> property : model.getProperties().entrySet()) {
                properties.put(property.getKey(), createVisitableProperty(property.getKey(), property.getValue()));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getRequired() {
        try {
            return emptyIfNull((List<String>) readField(model, "required", true));
        } catch (IllegalAccessException ex) {
            return emptyList();
        }
    }

    public List<String> getReadOlyProperties() {
        return getVisitableProperties().values().stream()
                .filter(VisitableProperty::getReadOnly)
                .map(VisitableProperty::getName)
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

    public Map<String, VisitableProperty> getVisitableProperties() {
        return properties;
    }

    public String getDiscriminator() {
        return model.getDiscriminator();
    }

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

    public VisitableProperty getAdditionalProperties() {
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
