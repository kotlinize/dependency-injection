import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "1.6.21"

    id("maven-publish")
    id("signing")
}

group = "com.kotlinizer"
version = "0.0.1"

val artifactId = "dependency-injection"

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.kotlinizer"
            artifactId = artifactId
            version = "0.0.1"

            from(components["java"])

            pom {
                name.set(artifactId)
                description.set("Library for Kotlin Dependency Injection")
                url.set("www.kotlinizer.com")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("mreynolds25")
                        name.set("Michael Reynolds")
                        email.set("mreynolds@kotlinizer.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/kotlinize/dependency-injection.git")
                    developerConnection.set("scm:git:git://github.com/kotlinize/dependency-injection.git")
                    url.set("https://github.com/kotlinize/dependency-injection")
                }
            }
        }
        repositories {
            maven {

                credentials {
                    username = "mreynolds25"
                    password = "ARmalite##01"
                }

                name = artifactId
                url = URI.create("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
        }
    }
}

signing {
    signing {
        setRequired {
            // signing is only required if the artifacts are to be published
            gradle.taskGraph.allTasks.any { it.equals(PublishToMavenRepository()) }
        }
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