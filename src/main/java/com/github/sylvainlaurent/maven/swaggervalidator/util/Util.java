package com.github.sylvainlaurent.maven.swaggervalidator.util;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class Util {

    public static <T> Set<T> findDuplicates(Collection<T> list) {
        Set<T> duplicates = new LinkedHashSet<>();
        Set<T> uniques = new HashSet<>();

        for (T elem : emptyIfNull(list)) {
            if (!uniques.add(elem)) {
                duplicates.add(elem);
            }
        }

        return duplicates;
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> createInstances(String packageName, Class validatorClass) {
        Set<T> customValidators = new HashSet<>();
        Set<Class> customValidatorClasses = getClassesFromPackage(packageName, validatorClass);

        for (Class<T> clazz : customValidatorClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                customValidators.add(createInstance(clazz));
            }
        }
        return customValidators;
    }

    private static Set<Class> getClassesFromPackage(String packageName, final Class superClass) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forPackage(packageName)));
        return reflections.getSubTypesOf(superClass);
    }

    private static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot instantiate validator " + clazz.getCanonicalName(), e);
        }
    }
}
