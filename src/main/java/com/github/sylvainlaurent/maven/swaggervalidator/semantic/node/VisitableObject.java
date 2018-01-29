package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node;

public interface VisitableObject<T> {
    void accept(T visitor);
}
