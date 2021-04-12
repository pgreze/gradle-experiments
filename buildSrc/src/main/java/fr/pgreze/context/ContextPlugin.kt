package fr.pgreze.context

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.Date

open class ContextPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("context", ContextPrinter::class.java) {
            now = Date()
            hostname = try {
                InetAddress.getLocalHost().hostName
            } catch (e: UnknownHostException) {
                null
            }
        }
    }
}
