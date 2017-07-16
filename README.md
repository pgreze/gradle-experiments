# Gradle experiments

## [Creating new gradle build](https://guides.gradle.org/creating-new-gradle-builds/)

Find already downloaded gradle binaries in .gradle/wrapper/dists.
For example:
```
alias gradle=~/.gradle/wrapper/dists/gradle-4.0.1-all/26awvqv6f41r14q9x72t4n0s/gradle-4.0.1/bin/gradle
```

And use to create your first project:
```
# Help on a specific task
gradle help --task wrapper

# New wrapper
gradle wrapper --gradle-version 4.0.1 --distribution-type all
```

Display project info:
```
./gradlew properties
```

Use our first custom task:
```
# Display all tasks
./gradlew tasks --all

# Execute task
./gradlew copy
```

After copy, let's zip our project's src folder:
```
./gradlew zip
unzip -ql build/distributions/hello-1.0.zip
```

## [Creating Multi-project Builds](https://guides.gradle.org/creating-multi-project-builds/)

Let's create our first sub project (wrapper + settings useless here):
```
# Init a new java-library build
mkdir jlib && cd jlib
../gradlew init --type java-library

# Add this new sub project
echo "include ':japp', ':jlib'" > settings.gradle
```

Consider using [gw](https://github.com/dougborg/gdub)
as a nice shortcut for using wrapper from anywhere with ease.

## [Writing gradle tasks](https://guides.gradle.org/writing-gradle-tasks/)

See changes in base/build.gradle and our new base task group when listing tasks :)

## [Writing gradle plugins](https://guides.gradle.org/writing-gradle-plugins/)

Good practice is to configure plugin properties in folder:
buildSrc/src/main/resources/META-INF/gradle-plugins
in order to use the plugin id.

## [Build environment](https://docs.gradle.org/4.0.1/userguide/build_environment.html)

2 ways to declare a project property:
- via command line (like "gradle hello -Pverbose.level=info -PnoLogs")
- via gradle.properties (verbose.level=info) in project directory
- via gradle.properties in gradle user home (usually ~/.gradle)

For a system property, see -Dproperty=value accessible via System.getProperty.
See [this page](https://docs.gradle.org/4.0.1/userguide/build_environment.html#sec:gradle_properties_and_system_properties)
about project/system properties and other ways to set them.

## [Java Gradle Plugin Development Plugin](https://docs.gradle.org/current/userguide/javaGradle_plugin.html)

Enable some validations:
- There is a plugin descriptor defined for the plugin.
- The plugin descriptor contains a valid implementation-class property.
- Each property getter or the corresponding field must be annotated with a property annotation like @InputFile and @OutputDirectory. Properties that don't participate in up-to-date checks should be annotated with @Internal.

Use the gradlePlugin{} block allows also:
- Generate the plugin descriptor (META-INF/my-plugin.properties).
- Configure the Maven publishing plugins to publish a Plugin Marker Artifact for each plugin.
- Easily use [TestKit](https://docs.gradle.org/current/userguide/test_kit.html#sub:test-kit-automatic-classpath-injection)

About plugins DSL, put at the beginning of settings.gradle:
```groovy
// See https://goo.gl/AmmZuy
pluginManagement {
    repositories {
        maven {
            url "file://${System.getProperty("user.home")}/.m2/repository"
        }
        gradlePluginPortal()
    }
}
```

Allow us to use:
```groovy
plugins {
    id 'fr.pgreze.hello' version '1.0'
}
```

Instead of:
```groovy
buildscript {
    dependencies {
        classpath 'fr.pgreze.hello:fr.pgreze.hello.gradle.plugin:0.1'
    }
}
apply plugin: 'fr.pgreze.hello'
```

But I'm too lazy to split each plugins from :jplugin in dedicated projects.

See [this gradle example](https://github.com/gradle/gradle/blob/master/subprojects/docs/src/samples/compositeBuilds/basic/my-utils/build.gradle)
for an example of easy split.
