plugins {
    java
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"
}
repositories { maven("https://repo.papermc.io/repository/maven-public/") }
dependencies {
    paperweight.paperDevBundle("26.3.build.+")
    compileOnly(project(":common"))
}
java { toolchain { languageVersion.set(JavaLanguageVersion.of(25)) } }
tasks.withType<JavaCompile> { options.release.set(25); options.encoding = "UTF-8" }
