import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class ContextPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("context", ContextPrinter.class, task -> {
            task.setNow(new Date());
            task.setHostname(getHostname());
        });
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
