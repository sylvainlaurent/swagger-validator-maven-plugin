package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal;

import java.util.Stack;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.VisitableProperty;

public class VisitedItemsHolder {

    private final Stack<String> visitedItems = new Stack<>();

    void push(VisitableProperty property) {
        visitedItems.push(property.getPropertyName());
    }

    void push(VisitableModel model) {
        visitedItems.push(model.getModelName());
    }

    void pop() {
        visitedItems.pop();
    }

    public String getCurrentPath() {
        String path = "";
        for (int i = 0; i < visitedItems.size(); i++) {
            path += visitedItems.get(i);
            if (i < visitedItems.size() - 1) {
                path += ".";
            }
        }

        return path;
    }
}
