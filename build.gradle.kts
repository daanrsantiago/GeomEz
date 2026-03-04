import java.util.Base64
import java.net.URI
import java.net.HttpURLConnection

plugins {
    kotlin("jvm") version "2.2.21"
    id("org.jetbrains.dokka") version "2.1.0"
}

repositories {
    mavenCentral()
}

dokka {
    moduleName.set("GeomEz")
    dokkaPublications.html {
        suppressInheritedMembers.set(true)
        failOnWarning.set(true)
    }
}

val ossrhUsername: String by project
val ossrhPassword: String by project

tasks.register("publishToCentral") {
    group = "publishing"
    description = "Stages all modules locally, bundles them, and uploads to Maven Central"

    dependsOn(":geomez-core:publish", ":geomez-visualization:publish")

    doLast {
        val stagingDir = layout.buildDirectory.dir("staging-deploy").get().asFile
        val zipFile = layout.buildDirectory.file("geomez-bundle.zip").get().asFile

        zipFile.delete()

        ant.withGroovyBuilder {
            "zip"("destfile" to zipFile.absolutePath, "basedir" to stagingDir.absolutePath)
        }

        logger.lifecycle("Bundle created: ${zipFile.absolutePath} (${zipFile.length()} bytes)")

        val token = Base64.getEncoder().encodeToString("$ossrhUsername:$ossrhPassword".toByteArray())
        val boundary = "GradlePublish${System.currentTimeMillis()}"

        val url = URI("https://central.sonatype.com/api/v1/publisher/upload?name=geomez-1.0.0&publishingType=USER_MANAGED").toURL()
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            setRequestProperty("Authorization", "Bearer $token")
            setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")

            outputStream.use { out ->
                out.write("--$boundary\r\n".toByteArray())
                out.write("Content-Disposition: form-data; name=\"bundle\"; filename=\"${zipFile.name}\"\r\n".toByteArray())
                out.write("Content-Type: application/zip\r\n\r\n".toByteArray())
                out.write(zipFile.readBytes())
                out.write("\r\n--$boundary--\r\n".toByteArray())
            }

            val code = responseCode
            val body = (if (code < 400) inputStream else errorStream)?.bufferedReader()?.readText() ?: ""

            logger.lifecycle("Upload response: HTTP $code")
            if (body.isNotBlank()) logger.lifecycle(body)

            if (code !in 200..299) {
                throw GradleException("Upload failed: HTTP $code\n$body")
            }

            logger.lifecycle("Upload successful! Visit https://central.sonatype.com/publishing/deployments to review and publish.")
        }
    }
}
