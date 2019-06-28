package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;

import io.swagger.models.properties.Property;

public class VisitedItemsHolder {

    private final Deque<String> visitedItems = new ArrayDeque<>();

    public void push(VisitableProperty<? extends Property> property) {
        visitedItems.push(property.getName());
    }

    public void push(VisitableModel model) {
        visitedItems.push(model.getName());
    }

    public void push(String item) {
        visitedItems.push(item);
    }

    public void pop() {
        visitedItems.pop();
    }

    public String getCurrentPath() {
        return String.join(".", (Iterable<String>) visitedItems::descendingIterator);
    }

    public Collection<String> getVisitedItems() {
        return Collections.unmodifiableCollection(visitedItems);
    }
}
