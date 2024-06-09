plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "vip.testops"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases/")
}

dependencies {
    implementation("com.theokanning.openai-gpt3-java:service:0.18.2")
    implementation("org.apache.poi:poi-ooxml:5.2.5")

}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.1")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(
            "TestNG-J",
            "org.intellij.plugins.markdown",
            "com.intellij.java",
    ))
}

sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("223")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChainFile.set(file("chain.crt"))
        privateKeyFile.set(file("private.pem"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
