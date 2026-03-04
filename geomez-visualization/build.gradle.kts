plugins {
    kotlin("jvm") version "2.2.21"
    `java-library`
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "2.1.0"
}

kotlin {
    jvmToolchain(21)
}


group = "com.geomez"
version = "1.0.0"

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
        create<MavenPublication>("geomez-visualization-mavenKotlin") {
            artifactId = "geomez-visualization"
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
                name.set("geomez-visualization")
                description.set("Visualization tools to geomez")
                url.set("https://github.com/daanrsantiago/GeomEz")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("daniel-santiago")
                        name.set("Daniel Ribeiro Santiago")
                        email.set("daanrsantiago@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/daanrsantiago/GeomEz")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri(rootProject.layout.buildDirectory.dir("staging-deploy").get().asFile)
        }
    }
}

signing {
    sign(publishing.publications["geomez-visualization-mavenKotlin"])
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    implementation("org.ejml:ejml-simple:0.41")
    implementation("io.github.daniel-tucano:matplotlib4k:0.3.0")

    implementation(project(":geomez-core"))
}

tasks.test {
    useJUnitPlatform()
}