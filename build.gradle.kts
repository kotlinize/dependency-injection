import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "1.6.21"
    `maven-publish`
    signing
}

object App {
    object Group {
        const val ID = "com.kotlinizer"
        const val URL = "www.kotlinizer.com"
    }
    object Artifact {
        const val NAME = "dependency-injection"
        const val VERSION = "0.0.3"
        const val GIT_URL = "scm:git:git://github.com/kotlinize/dependency-injection.git"
        const val SOURCE_CODE_URL = "https://github.com/kotlinize/dependency-injection"
        const val DESCRIPTION = "Library for Kotlin Dependency Injection"
    }
    object Developer {
        const val NAME = "Michael Reynolds"
        const val EMAIL = "mreynolds@kotlinizer.com"
        const val USER_ID = "mreynolds25"
    }
    object Publish {
        const val URL = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
    }
    object License {
        const val NAME = "The Apache License, Version 2.0"
        const val URL = "http://www.apache.org/licenses/LICENSE-2.0.txt"
    }
}

group = App.Group.ID
version = App.Artifact.VERSION

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = App.Group.ID
            artifactId = App.Artifact.NAME
            version = App.Artifact.VERSION

            from(components["java"])

            pom {
                name.set(App.Artifact.NAME)
                description.set(App.Artifact.DESCRIPTION)
                url.set(App.Group.URL)
                licenses {
                    license {
                        name.set(App.License.NAME)
                        url.set(App.License.URL)
                    }
                }
                developers {
                    developer {
                        id.set(App.Developer.USER_ID)
                        name.set(App.Developer.NAME)
                        email.set(App.Developer.EMAIL)
                    }
                }
                scm {
                    connection.set(App.Artifact.GIT_URL)
                    developerConnection.set(App.Artifact.GIT_URL)
                    url.set(App.Artifact.SOURCE_CODE_URL)
                }
            }
        }
        repositories {
            maven {
                credentials {
                    username = project.findProperty("NEXUS_USERNAME") as? String? ?: System.getenv("NEXUS_USERNAME")
                    password = project.findProperty("NEXUS_PASSWORD") as? String? ?: System.getenv("NEXUS_PASSWORD")
                }
                name = App.Artifact.NAME
                url = URI.create(App.Publish.URL)
            }
        }
    }
}

signing {
    signing {
        sign(publishing.publications)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}