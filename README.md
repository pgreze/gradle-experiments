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
