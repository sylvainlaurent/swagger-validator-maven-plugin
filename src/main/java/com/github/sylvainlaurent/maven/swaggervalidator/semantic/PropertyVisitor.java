package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.RefPropertyWrapper;

public interface PropertyVisitor {

    void visit(ObjectPropertyWrapper objectProperty);

    void visit(ArrayPropertyWrapper arrayProperty);

    void visit(RefPropertyWrapper refPropertyWrapper);
}
