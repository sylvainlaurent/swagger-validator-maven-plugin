package com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefPropertyWrapper;

public interface PropertyVisitor {

    void visit(ObjectPropertyWrapper objectProperty);

    void visit(ArrayPropertyWrapper arrayProperty);

    void visit(RefPropertyWrapper refPropertyWrapper);
}
