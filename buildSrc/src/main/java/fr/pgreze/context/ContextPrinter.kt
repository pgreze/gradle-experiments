package fr.pgreze.context

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.text.DateFormat
import java.util.Date

open class ContextPrinter : DefaultTask() {
    init {
        group = "context"
    }

    @get:Input
    var now: Date? = null

    @get:Input
    var hostname: String? = null

    @get:Input
    var extra = ""

    @TaskAction
    fun printContext() {
        val hour = DateFormat.getTimeInstance(DateFormat.SHORT).format(now)
        println("Task started at $hour on $hostname $extra")
    }
}
