import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.text.DateFormat;
import java.util.Date;

public class ContextPrinter extends DefaultTask {

    private Date now;
    private String hostname;

    @TaskAction
    void printContext() {
        String hour = DateFormat.getTimeInstance(DateFormat.SHORT).format(now);
        System.out.println(String.format("Task started at %s on %s", hour, hostname));
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
