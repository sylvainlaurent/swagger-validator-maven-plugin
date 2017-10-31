package com.github.sylvainlaurent.maven.swaggervalidator;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.github.sylvainlaurent.maven.swaggervalidator.instrumentation.Instrumentation;

public class ValidatorJunitRunner extends BlockJUnit4ClassRunner {

    public ValidatorJunitRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        Instrumentation.init();
        super.run(notifier);
    }
}
