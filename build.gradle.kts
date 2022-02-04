plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.skyslycer.particledisplay"
version = "1.0.0"

val shadePattern = "$group.shade"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.7.0")
    implementation("me.mattstudios.utils:matt-framework:1.4")
    implementation("net.kyori:adventure-text-minimessage:4.10.0-SNAPSHOT")
    implementation("org.imgscalr:imgscalr-lib:4.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    shadowJar {
        relocate("me.mattstudios.mf", "$shadePattern.mf")
        val nullClassifier: String? = null
        archiveClassifier.set(nullClassifier)
    }

    build {
        dependsOn(shadowJar)
    }
}

bukkit {
    main = "de.skyslycer.particledisplay.ParticleDisplay"
    apiVersion = "1.18"
    softDepend = listOf("ProtocolLib")
    name = "ParticleDisplay"
    authors = listOf("Skyslycer")
}