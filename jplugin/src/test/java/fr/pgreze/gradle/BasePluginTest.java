package fr.pgreze.gradle;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class BasePluginTest {

    @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder();
    private File buildFile;

    abstract String getPluginId();

    @Before
    public void setup() throws IOException {
        buildFile = testProjectDir.newFile("build.gradle");

        String buildFileContent = "plugins { id '" + getPluginId() + "' }";
        writeFile(buildFile, buildFileContent);
    }

    @Test
    public void testIsJavaCompatible() throws IOException {
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("build")
                .withPluginClasspath()
                .build();

        assertEquals(result.task(":build").getOutcome(), SUCCESS);
    }

    @Test
    public void testContainsCommonLibs() {
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("dependencies")
                .withPluginClasspath()
                .build();

        assertTrue(result.getOutput().contains("junit"));
    }

    private void writeFile(File destination, String content) throws IOException {
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(destination));
            output.write(content);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
}