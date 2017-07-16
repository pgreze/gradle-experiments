package fr.pgreze.gradle

import org.gradle.api.Plugin
import org.gradle.api.plugins.JavaPlugin

class ApplicationPlugin extends BasePlugin {

    @Override
    String getPluginId() {
        return "java"
    }

    @Override
    Class<? extends Plugin> getPluginType() {
        return JavaPlugin.class
    }
}
