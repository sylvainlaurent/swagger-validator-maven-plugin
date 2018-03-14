package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.UnhandledPropertyWrapper;

public interface ModelVisitor {

    void visit(ModelImplWrapper modelImplWrapper);

    void visit(RefModelWrapper refModelWrapper);

    void visit(ArrayModelWrapper arrayModelWrapper);

    void visit(ComposedModelWrapper composedModelWrapper);

    void visit(ObjectPropertyWrapper objectProperty);

    void visit(ArrayPropertyWrapper arrayProperty);

    void visit(RefPropertyWrapper refPropertyWrapper);

    void visit(UnhandledPropertyWrapper refPropertyWrapper);
}
