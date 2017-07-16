package fr.pgreze.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

public class BonjourPrinter extends DefaultTask {

    @Internal
    private String context;

    @TaskAction
    void bonjour() {
        System.out.println("Bonjour from " + context);
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
