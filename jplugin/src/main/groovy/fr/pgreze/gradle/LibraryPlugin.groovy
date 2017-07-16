package fr.pgreze.gradle

import org.gradle.api.Plugin
import org.gradle.api.plugins.JavaLibraryPlugin

class LibraryPlugin extends BasePlugin {

    @Override
    String getPluginId() {
        return "java-library"
    }

    @Override
    Class<? extends Plugin> getPluginType() {
        return JavaLibraryPlugin.class
    }
}
