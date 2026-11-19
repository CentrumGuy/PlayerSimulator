import org.gradle.api.tasks.SourceSetContainer

plugins {
    java
    id("com.gradleup.shadow") version "9.2.2"
}
repositories { maven("https://repo.papermc.io/repository/maven-public/") }

evaluationDependsOn(":nms-1_21_11")
evaluationDependsOn(":nms-26")

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    implementation(project(":common"))
}
java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }
tasks.withType<JavaCompile> { options.release.set(21); options.encoding = "UTF-8" }

tasks.shadowJar {
    archiveBaseName.set("PlayerSimulator")
    archiveClassifier.set("")
    dependsOn(":nms-1_21_11:classes", ":nms-26:classes")
    from(project(":nms-1_21_11").the<SourceSetContainer>()["main"].output)
    from(project(":nms-26").the<SourceSetContainer>()["main"].output)

    manifest { attributes("paperweight-mappings-namespace" to "mojang") }
}
tasks.build { dependsOn(tasks.shadowJar) }
tasks.jar { enabled = false }
