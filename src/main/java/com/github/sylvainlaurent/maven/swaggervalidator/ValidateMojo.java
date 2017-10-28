package com.github.sylvainlaurent.maven.swaggervalidator;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;

import com.github.sylvainlaurent.maven.swaggervalidator.instrumentation.Instrumentation;

@Mojo(name = "validate", defaultPhase = LifecyclePhase.PROCESS_SOURCES, threadSafe = true)
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

    private final ValidationService validationService = new ValidationService();

    @Override
    public void execute() throws MojoExecutionException {

        Instrumentation.init();

        final File[] files = getFiles();
        boolean encounteredError = false;

        for (final File file : files) {
            if (verbose) {
                getLog().info("Processing file " + file);
            }
            final ValidationResult result = validationService.validate(file);
            if (result.hasError()) {
                encounteredError = true;
            }
            for (final String msg : result.getMessages()) {
                getLog().warn(msg);
            }
        }

        if (encounteredError) {
            throw new MojoExecutionException("Some files are not valid, see previous logs");
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
