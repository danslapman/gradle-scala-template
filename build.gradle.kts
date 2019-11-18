plugins {
    scala
    id("com.adtran.scala-multiversion-plugin") version "1.0.35"
}

allprojects {
    group = "danslapman"
    version = "0.1"

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        implementation("org.scala-lang:scala-library:%scala-version%")
        testImplementation("org.scalatest:scalatest_%%:3.0.8")
        testImplementation("junit:junit:4.12")
    }

    tasks.withType<ScalaCompile>().configureEach {
        scalaCompileOptions.additionalParameters.apply {
            listOf("-feature", "-Xfatal-warnings")
        }
    }
}

subprojects {
    apply(plugin = "scala")
}

tasks.wrapper {
    gradleVersion = "6.0"
    distributionType = Wrapper.DistributionType.ALL
}