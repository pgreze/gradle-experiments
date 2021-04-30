package com.github.pgreze

import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke

configurations {
    create("kotlinc")
    create("scripts")
}

val kotlinVersion = "1.4.32"
dependencies {
    "kotlinc"("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    "kotlinc"("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    "kotlinc"("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
    "kotlinc"("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:$kotlinVersion")

    "scripts"("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
}

val kotlinHome = file("$buildDir/kotlin")

val prepareKotlinHome = tasks.register("prepareKotlinHome", Copy::class.java) {
    from(configurations.named("kotlinc"))
    into("$kotlinHome/lib")
    rename {
        // strip the version suffix to satisfy the compiler
        it.replace("-$kotlinVersion.jar", ".jar")
    }
}

tasks.register("runKotlin", JavaExec::class.java) {
    group = "other"

    dependsOn(prepareKotlinHome)
    mainClass.set("org.jetbrains.kotlin.cli.jvm.K2JVMCompiler")
    classpath(configurations.named("kotlinc"))
    args = listOf(
        "-kotlin-home", kotlinHome.absolutePath,
        "-classpath", configurations.named("scripts").get().asPath,
        "-script", "/Users/pgreze/git/gradle-experiments/hello.main.kts"
    )
}
