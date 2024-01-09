plugins {
    java
    id("com.github.weave-mc.weave-gradle") version "649dba7468"
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

group = "me.ballmc"
version = "1.0"

minecraft.version("1.8.9")

configurations {
    implementation {
        extendsFrom(configurations["shadow"])
    }
}

repositories {
    maven("https://jitpack.io")
    maven("https://repo.spongepowered.org/maven/")
}

dependencies {
    compileOnly("com.github.weave-mc:weave-loader:v0.2.5")
    compileOnly("org.spongepowered:mixin:0.8.5")
    implementation("io.github.llamalad7:mixinextras-common:0.3.2")
}

tasks.shadowJar {
    configurations = listOf(project.configurations["shadow"])
    relocate("com.llamalad7.mixinextras", "me.ballmc.mixinextras")
    mergeServiceFiles() 
}

tasks.compileJava {
    options.release.set(11)
}