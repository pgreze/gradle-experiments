package fr.pgreze.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class BonjourPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("bonjour", BonjourPrinter.class, task -> {
            task.setContext("bonjour plugin");
        });
    }
}
