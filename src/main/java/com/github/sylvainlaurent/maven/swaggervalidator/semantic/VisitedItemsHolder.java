package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;

import java.util.Stack;

public class VisitedItemsHolder {

    private final Stack<String> visitedItems = new Stack<>();

    public void push(VisitableProperty property) {
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
