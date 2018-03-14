package com.github.sylvainlaurent.maven.swaggervalidator;

import com.github.sylvainlaurent.maven.swaggervalidator.instrumentation.Instrumentation;
import com.github.sylvainlaurent.maven.swaggervalidator.service.ValidationService;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;

import java.io.File;

@Mojo(name = "validate", defaultPhase = LifecyclePhase.PROCESS_SOURCES, threadSafe = true,
        requiresDependencyResolution = ResolutionScope.TEST)
public class ValidateMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${project.basedir}", required = true, readonly = true)
    private File basedir;

    @Parameter
    private String[] includes;

    @Parameter
    private String[] excludes;

    @Parameter(defaultValue = "true")
    private boolean verbose;

    @Parameter
    private String customModelValidatorsPackage;

    @Parameter
    private String customPathValidatorsPackage;

    @Parameter(defaultValue = "true")
    private Boolean failOnErrors;

    private final ValidationService validationService = new ValidationService();

    @Override
    public void execute() throws MojoExecutionException {
        validationService.setCustomModelValidatorsPackage(customModelValidatorsPackage);
        validationService.setCustomPathValidatorsPackage(customPathValidatorsPackage);

        Instrumentation.init();

        final File[] files = getFiles();
        boolean encounteredError = false;
        int errorCount = 0;

        for (final File file : files) {
            if (verbose) {
                getLog().info("Processing file " + file);
            }
            final ValidationResult result = validationService.validate(file);
            if (result.hasError()) {
                encounteredError = true;
            }

            Collections.sort(result.getMessages());

            for (final String msg : result.getMessages()) {
                errorCount++;
                if (failOnErrors) {
                    getLog().error(msg);
                } else {
                    getLog().warn(msg);
                }
            }
        }

        if (encounteredError && failOnErrors) {
            throw new MojoExecutionException("Some files are not valid, see previous logs. Encountered " + errorCount + " errors.");
        }
    }

    private File[] getFiles() {
        final DirectoryScanner ds = new DirectoryScanner();
        ds.setBasedir(basedir);
        if (includes != null && includes.length > 0) {
            ds.setIncludes(includes);
        }
        if (excludes != null && excludes.length > 0) {
            ds.setExcludes(excludes);
        }
        ds.scan();
        final String[] filePaths = ds.getIncludedFiles();
        final File[] files = new File[filePaths.length];

        for (int i = 0; i < filePaths.length; i++) {
            files[i] = new File(basedir, filePaths[i]);
        }

        return files;
    }
}
