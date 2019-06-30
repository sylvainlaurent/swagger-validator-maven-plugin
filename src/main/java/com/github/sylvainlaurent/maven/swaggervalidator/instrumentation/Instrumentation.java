package com.github.sylvainlaurent.maven.swaggervalidator.instrumentation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.pool.TypePool;

import static net.bytebuddy.matcher.ElementMatchers.named;

public final class Instrumentation {

    private static final String CLASS_TO_BE_INSTRUMENTED = "io.swagger.models.properties.ObjectProperty";
    private static final String METHOD_TO_BE_INSTRUMENTED = "setRequiredProperties";
    private static boolean initialized = false;

    private Instrumentation() {
        // EMPTY
    }

    public static synchronized void init() {

        if (initialized) {
            return;
        }

        try {
            ClassLoader classLoader = Instrumentation.class.getClassLoader();
            TypePool typePool = TypePool.Default.of(classLoader);

            new ByteBuddy().rebase(typePool.describe(CLASS_TO_BE_INSTRUMENTED).resolve(), ClassFileLocator.ForClassLoader.of(classLoader))
                           .method(named(METHOD_TO_BE_INSTRUMENTED))
                           .intercept(MethodDelegation.to(ObjectPropertyRequiredInterceptor.class))
                           .make()
                           .load(classLoader, ClassLoadingStrategy.Default.INJECTION);
            initialized = true;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
