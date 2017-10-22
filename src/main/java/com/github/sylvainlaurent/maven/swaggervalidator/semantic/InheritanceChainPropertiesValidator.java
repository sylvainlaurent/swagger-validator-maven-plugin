package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import static com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.VisitableModelFactory.createVisitableModel;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.PROPERTIES_ALREADY_DEFINED_IN_ANCESTORS;
import static java.util.Collections.disjoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.AbstractModelVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.RefModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.doc.traversal.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticErrorsCollector;

import io.swagger.models.Model;
import io.swagger.models.RefModel;
import io.swagger.models.properties.Property;

public class InheritanceChainPropertiesValidator extends AbstractModelVisitor {

    private final Map<String, Model> definitions;
    private final SemanticErrorsCollector errorCollector;

    public InheritanceChainPropertiesValidator(Map<String, Model> definitions, SemanticErrorsCollector errorCollector) {
        this.definitions = definitions;
        this.errorCollector = errorCollector;
    }

    @Override
    public void handle(ModelImplWrapper modelImplWrapper) {

    }

    @Override
    public void handle(RefModelWrapper refModelWrapper) {
        // EMPTY
    }

    @Override
    public void handle(ArrayModelWrapper arrayModelWrapper) {

    }

    @Override
    public void handle(ComposedModelWrapper composedModel) {
        Collection<String> childProperties = composedModel.getChild() == null || composedModel.getChild().getProperties() == null ?
                                             Collections.<String>emptyList() :
                                             composedModel.getChild().getProperties().keySet();

        List<String> parentProperties = new ParentPropertiesCollector(composedModel).allParentProperties();

        boolean childHasPropertiesAlreadyPresentInParent = !disjoint(childProperties, parentProperties);
        if (childHasPropertiesAlreadyPresentInParent) {
            errorCollector.addDefinitionError(
                PROPERTIES_ALREADY_DEFINED_IN_ANCESTORS,
                holder.getCurrentPath(),
                "following properties are already defined in ancestors: " + findCommonProperties(
                    childProperties,
                    parentProperties));
        }
    }

    private List<String> findCommonProperties(Collection<String> propertyNames, Collection<String> parentPropertyNames) {
        List<String> commonProperties = new ArrayList<>(propertyNames);
        commonProperties.retainAll(parentPropertyNames);
        return commonProperties;
    }

    final class ParentPropertiesCollector extends AbstractModelVisitor {

        private List<String> allParentProperties = new ArrayList<>();

        private VisitableModel root;

        ParentPropertiesCollector(VisitableModel root) {
            this.root = root;
        }

        @Override
        public void handle(ModelImplWrapper modelImplWrapper) {
            if (modelImplWrapper.getProperties() != null) {
                allParentProperties.addAll(modelImplWrapper.getProperties().keySet());
            }
        }

        @Override
        public void handle(RefModelWrapper refModelWrapper) {
            String ref = refModelWrapper.getSimpleRef();
            createVisitableModel(ref, definitions.get(ref)).accept(this);
        }

        @Override
        public void handle(ArrayModelWrapper arrayModelWrapper) {
            // EMPTY
        }

        @Override
        public void handle(ComposedModelWrapper composedModelWrapper) {
            if (composedModelWrapper != root) {
                Map<String, Property> childProperties = composedModelWrapper.getChild().getProperties() != null ?
                                                        composedModelWrapper.getChild().getProperties() :
                                                        Collections.<String, Property>emptyMap();
                allParentProperties.addAll(childProperties.keySet());
            }

            for (RefModel element : composedModelWrapper.getInterfaces()) {
                createVisitableModel(element.getSimpleRef(), element).accept(this);
            }
        }

        List<String> allParentProperties() {
            if (allParentProperties.isEmpty()) {
                root.accept(this);
            }
            return allParentProperties;
        }
    }
}
