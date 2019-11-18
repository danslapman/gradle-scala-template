subprojects {
    group = "danslapman"
    version = "0.1"
}

tasks.wrapper {
    gradleVersion = "6.0"
    distributionType = Wrapper.DistributionType.ALL
}

plugins {
    scala
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        implementation("org.scala-lang:scala-library:2.12.10")
        testImplementation("org.scalatest:scalatest_2.12:3.0.8")
        testImplementation("junit:junit:4.12")
    }

    tasks.withType<ScalaCompile>().configureEach {
        scalaCompileOptions.additionalParameters.apply {
            listOf("-feature", "-Xfatal-warnings")
        }
    }
}

