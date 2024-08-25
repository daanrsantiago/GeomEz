import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm") version "1.5.21"
}

group = "io.github.daniel-tucano"
version = "0.2.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
    withSourcesJar()
}

val ossrhUsername: String by project
val ossrhPassword: String by project

publishing {
    publications {
        create<MavenPublication>("geomez-core-mavenKotlin") {
            artifactId = "geomez-core"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set("geomez-core")
                description.set("An easy way to build geometric elements")
                url.set("https://github.com/daniel-tucano/GeomEz")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("daniel-tucano")
                        name.set("Daniel Ribeiro Santiago")
                        email.set("daanrsantiago@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/daniel-tucano/GeomEz")
                }
            }
        }
    }
    repositories {
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            val releasesRepoUrl = URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = URI("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
}

signing {
    sign(publishing.publications["geomez-core-mavenKotlin"])
}

dependencies {
    api("space.kscience:kmath-core:0.2.1")
    implementation("space.kscience:kmath-ejml:0.2.1")
    implementation("org.ejml:ejml-simple:0.41")

    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation(kotlin("test"))
    implementation(kotlin("script-runtime"))

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}