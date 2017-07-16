package fr.pgreze.gradle

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.*

class BonjourTest extends Specification {

    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "bonjour task prints bonjour"() {
        given:
        buildFile << """
            plugins {
                id 'fr.pgreze.gradle.bonjour'
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('bonjour')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains('Bonjour from bonjour plugin')
        result.task(":bonjour").outcome == SUCCESS
    }
}