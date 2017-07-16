package fr.pgreze.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class BasePlugin implements Plugin<Project> {

    abstract String getPluginId()
    abstract Class<? extends Plugin> getPluginType()

    @Override
    void apply(Project project) {
        project.plugins.withType(getPluginType()) {
            project.sourceCompatibility = 1.7
            project.targetCompatibility = 1.7

            project.repositories {
                jcenter()
            }
            project.dependencies {
                testImplementation 'junit:junit:4.12'
            }
        }

        project.plugins.apply(getPluginId())
    }
}
