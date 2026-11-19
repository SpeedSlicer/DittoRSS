plugins {
    id("java")
    id("application")
}

group = "dev.speedslicer"
version = "1.0-SNAPSHOT"
description = "TUI RSS Feed"

application {
    mainClass = "dev.speedslicer.ditto.rss.client.Main"
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}

repositories {
    mavenCentral()
}

tasks.register<Exec>("runTestTerminalKitty") {
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

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}

dependencies {
    implementation("com.williamcallahan:tui4j:0.3.5")
    implementation("org.jline:jline-terminal-jni:3.26.1")
    implementation("com.apptasticsoftware:rssreader:3.12.0")
    implementation("com.squareup.moshi:moshi:1.15.2")
    implementation("com.squareup.moshi:moshi-adapters:1.15.2")
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}