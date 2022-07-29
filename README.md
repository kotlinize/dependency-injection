# Dependency Injection `v0.0.3`
[![Java CI with Maven](https://github.com/kotlinize/dependency-injection/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/kotlinize/dependency-injection/actions/workflows/maven.yml)
[![Unit Tests](https://github.com/kotlinize/dependency-injection/actions/workflows/test.yml/badge.svg)](https://github.com/kotlinize/dependency-injection/actions/workflows/test.yml)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.kotlinizer/dependency-injection/0.0.3)](https://search.maven.org/artifact/com.kotlinizer/dependency-injection/0.0.3/jar)

Simple, Lightweight ```Dependency Injection``` solution for Kotlin / Android(Kotlin) that reduces boiler-plate code used in most Dependency Injection Solutions.

## Table of contents
<!--- TOC -->
* [Introduction and references](#introduction-and-references)
* [Setup](#setup)
  * [Kotlin DSL](#kotlin-dsl)
  * [Groovy DSL](#groovy-dsl)
<!--- END -->

## Introduction and references

An example of how to Setup and `register` objects into the Dependency Injector. 

> A good practice is to initialize the Dependency Injector in the early Lifecycle Stages, such as the `onCreate()` method of the Application.

```kotlin
import android.app.Application
import com.kotlinizer.dependencyInjection.IDependencyInjector
import com.kotlinizer.dependencyInjection.Injector
class MainApplication : Application() {
	
	private val injector: IDependencyInjector by lazy { Injector.instance }
	override fun onCreate() {
		super.onCreate()
		
		// Load Dependencies
		val firstName = "Kotlin"
		val lastName = "Dependency-Injection"
		val phoneNumber = 5555555555L
		
		injector.register(type = String::class.java, provider = firstName, identifier = "FIRST_NAME")
		injector.register(type = String::class.java, provider = lastName, identifier = "LAST_NAME")
		injector.register(type = Long::class.java, provider = phoneNumber)
		
	}
}
```

An example of how to retrieve, or ```resolve```, the object from the Dependency.

```kotlin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlinizer.dependencyInjection.IDependencyInjector
import com.kotlinizer.dependencyInjection.Injector
class MainActivity : AppCompatActivity() {
	private val injector: IDependencyInjector by lazy { Injector.instance }
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		// Retrieve the data from the Dependency Injector.
		val firstName: String? = injector.resolve(type = String::class.java, identifier = "FIRST_NAME")
		val lastName: String? = injector.resolve(type = String::class.java, identifier = "LAST_NAME")
		// No identifier is passed, as we didn't use one when being registered.
		val phoneNumber: Long? = injector.resolve(type = Long::class.java)
	}
}
```

> Full example can be found [here](https://github.com/kotlinize/dependency-injection-example)

In Action:

<img src="https://github.com/kotlinize/dependency-injection/blob/master/Dependency%20Injection%20Example.gif" width="275" height="600"/>

## Setup

To setup, simply add this dependency to your project and everything should be ready to go!

### Kotlin DSL
```kotlin
dependencies {
    implementation("com.kotlinizer:dependency-injection:0.0.3")
}
```

### Groovy DSL

```gradle
dependencies {
    implementation 'com.kotlinizer:dependency-injection:0.0.3'
}
```
