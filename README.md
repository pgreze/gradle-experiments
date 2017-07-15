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

