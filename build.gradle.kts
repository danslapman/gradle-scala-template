buildscript {
    dependencies {
        classpath("com.adtran:scala-multiversion-plugin:1.+")
    }
}

plugins {
    scala
}

allprojects {
    apply(plugin = "scala")
    apply(plugin = "com.adtran.scala-multiversion-plugin")

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        implementation("org.scala-lang:scala-library:%scala-version%")
        testImplementation("org.scalatest:scalatest_%%:3.0.8")
        testImplementation("junit:junit:4.12")
    }
}

subprojects {
    group = "danslapman"
    version = "0.1"

    tasks.withType<ScalaCompile>().configureEach {
        scalaCompileOptions.additionalParameters.apply {
            listOf("-feature", "-Xfatal-warnings")
        }
    }
}

tasks.wrapper {
    gradleVersion = "6.0"
    distributionType = Wrapper.DistributionType.ALL
}