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
    sign(publishing.publications["geomez-core-mavenKotlin"])
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.21")
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