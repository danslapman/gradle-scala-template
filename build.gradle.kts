@file:Suppress("DEPRECATION")

buildscript {
    dependencies {
        classpath("gradle.plugin.com.github.maiflai:gradle-scalatest:0.32")
    }
}

plugins {
    id("com.github.prokod.gradle-crossbuild").version("0.16.0")
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
                "2.12" to "2.12.18",
                "2.13" to "2.13.12"
        )

        builds {
            create("v212")
            create("v213")
        }
    }

    dependencies {
        implementation("org.scala-lang:scala-library:2.12.18")
        testImplementation("org.scalatest:scalatest_2.12:3.2.18")
        "scalaCompilerPlugin" ("com.olegpy:better-monadic-for_2.12:0.3.1")
        testImplementation("com.vladsch.flexmark:flexmark-all:0.64.8")
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
    gradleVersion = "8.5"
    distributionType = Wrapper.DistributionType.ALL
}