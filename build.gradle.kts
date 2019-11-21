@file:Suppress("DEPRECATION")

buildscript {
    dependencies {
        classpath("gradle.plugin.com.github.maiflai:gradle-scalatest:0.25")
    }
}

plugins {
    id("com.github.prokod.gradle-crossbuild").version("0.9.1")
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }

    configurations {
        create("scalaCompilerPlugin") {
            setTransitive(false)
        }
    }
}

subprojects {
    group = "danslapman"
    version = "0.1"

    apply(plugin = "scala")
    apply(plugin = "com.github.prokod.gradle-crossbuild")
    apply(plugin = "com.github.maiflai.scalatest")

    crossBuild {
        scalaVersionsCatalog = mapOf(
                "2.12" to "2.12.10",
                "2.13" to "2.13.0"
        )

        builds {
            create("v212")
        }
    }

    dependencies {
        compile("org.scala-lang:scala-library:2.12.10")
        testCompile("org.scalatest:scalatest_2.12:3.0.8")
        "scalaCompilerPlugin" ("com.olegpy:better-monadic-for_2.12:0.3.1")
        testRuntime("org.pegdown:pegdown:1.4.2")
    }

    val copyPlugins by tasks.registering(Copy::class) {
        from(configurations["scalaCompilerPlugin"])
        into("$buildDir/scalac-plugins")
    }

    tasks.withType<ScalaCompile>().configureEach {
        val plugins = File("$buildDir/scalac-plugins").listFiles()?.let {
            "-Xplugin:" + it.joinToString(",")
        }

        scalaCompileOptions.additionalParameters =
            listOf("-feature", "-Xfatal-warnings") + listOfNotNull(plugins)
    }
}

tasks.wrapper {
    gradleVersion = "6.0"
    distributionType = Wrapper.DistributionType.ALL
}