package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PathVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import io.swagger.models.parameters.Parameter;

public class ParameterWrapper<T extends Parameter> implements VisitableParameter<T> {

    private String in;
    protected T parameter;

    public ParameterWrapper(T parameter) {
        this.parameter = parameter;
        this.in = parameter.getIn();
    }

    @Override
    public T getParameter() {
        return parameter;
    }

    @Override
    public String getObjectPath() {
        return parameter.getName();
    }

    @Override
    public String getName() {
        return parameter.getName();
    }

    @Override
    public String getIn() {
        return in;
    }

    protected void setIn(String in) {
        this.in = in;
    }

    @Override
    public String getDescription() {
        return parameter.getDescription();
    }

    @Override
    public boolean isRequired() {
        return parameter.getRequired();
    }

    @Override
    public String getAccess() {
        return parameter.getAccess();
    }

    @Override
    public String getPattern() {
        return parameter.getPattern();
    }

    @Override
    public Boolean getAllowEmptyValue() {
        return parameter.getAllowEmptyValue();
    }

    @Override
    public Boolean getReadOnly() {
        return parameter.isReadOnly();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ParameterWrapper that = (ParameterWrapper) o;

        if (!in.equals(that.in))
            return false;
        return parameter.getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result = in.hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public void accept(PathVisitor visitor) {
        visitor.visit(this);
    }
}
