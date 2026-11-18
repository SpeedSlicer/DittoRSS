plugins {
    id("java")
    id("application")
}

group = "dev.speedslicer.ditto.rss.client"
version = "1.0-SNAPSHOT"
description = "TUI RSS Feed"

application {
    mainClass = "dev.speedslicer.ditto.rss.client.Main"
}

repositories {
    mavenCentral()
}

tasks.register<Exec>("runTestTerminal") {
    group = "application"
    description = "Compiles the project and launches the TUI inside Kitty"
    dependsOn("classes")

    val classpath = sourceSets.main.get().runtimeClasspath.asPath
    val mainClassName = application.mainClass.get()

    commandLine(
        "/home/shaun/.local/kitty.app/bin/kitty",
        "--hold",
        "--directory", projectDir.absolutePath,
        "java", "-cp", classpath, mainClassName
    )
}

dependencies {
    implementation("com.williamcallahan:tui4j:0.3.5")
    implementation("com.apptasticsoftware:rssreader:3.12.0")
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}