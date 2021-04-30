package com.github.pgreze

import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke

//
// Reference: https://gist.github.com/bamboo/f29e738c2a17a36e87c814b7452afe31
//
// Example of java command when running a script with kotlinc:
// java -Xmx256M -Xms32M -noverify \
//      -cp /usr/local/lib/kotlin-preloader.jar org.jetbrains.kotlin.preloading.Preloader \
//      -cp /usr/local/lib/kotlin-compiler.jar org.jetbrains.kotlin.cli.jvm.K2JVMCompiler \
//      -script ../mercari-android/.circleci/test-lab-run.main.kts -- arg1
//

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
    println("Args: ${args.joinToString(" ")}")
}

// -Dfile.encoding=UTF-8 -Duser.country=JP -Duser.language=en -Duser.variant -cp /Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-compiler-embeddable/1.4.32/b4fc9be460ca88eecea2303e142ce2920bd14775/kotlin-compiler-embeddable-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-reflect/1.4.32/ce852b166d97f0f1991b5130c2bb02e2ef6c554e/kotlin-reflect-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-scripting-compiler-embeddable/1.4.32/9bae347fdca329efc899ffc0187b394dd9ef1fc7/kotlin-scripting-compiler-embeddable-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-scripting-compiler-impl-embeddable/1.4.32/6c1e06e471e1d2ef26d0120712c1fcefb8cc0ce9/kotlin-scripting-compiler-impl-embeddable-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-scripting-jvm/1.4.32/49656d531bfab9d6e45d3f27bc4d03b542d6766/kotlin-scripting-jvm-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-scripting-common/1.4.32/6abda0fe69677f0e46e7539fd185e4bd093b7258/kotlin-scripting-common-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlinx/kotlinx-coroutines-core/1.3.8/f62be6d4cbf27781c2969867b4ed952f38378492/kotlinx-coroutines-core-1.3.8.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.4.32/461367948840adbb0839c51d91ed74ef4a9ccb52/kotlin-stdlib-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.4.32/ef50bfa2c0491a11dcc35d9822edbfd6170e1ea2/kotlin-stdlib-common-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains/annotations/13.0/919f0dfe192fb4e063e7dacadee7f8bb9a2672a9/annotations-13.0.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-script-runtime/1.4.32/bac50b0748be017dbc13fc1cb7231b1c9da0e106/kotlin-script-runtime-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-daemon-embeddable/1.4.32/b40c5de5adea26ebb4e5fc36945c482ac20acee2/kotlin-daemon-embeddable-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.intellij.deps/trove4j/1.0.20181211/216c2e14b070f334479d800987affe4054cd563f/trove4j-1.0.20181211.jar org.jetbrains.kotlin.cli.jvm.K2JVMCompiler -kotlin-home /Users/pgreze/git/gradle-experiments/build/kotlin -classpath /Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.4.32/461367948840adbb0839c51d91ed74ef4a9ccb52/kotlin-stdlib-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.4.32/ef50bfa2c0491a11dcc35d9822edbfd6170e1ea2/kotlin-stdlib-common-1.4.32.jar:/Users/pgreze/.gradle/caches/modules-2/files-2.1/org.jetbrains/annotations/13.0/919f0dfe192fb4e063e7dacadee7f8bb9a2672a9/annotations-13.0.jar -script /Users/pgreze/git/gradle-experiments/hello.main.kts
