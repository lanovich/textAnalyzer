plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "3.1.0-rc-1"
}

group = "com.example"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    implementation("org.openjfx:javafx-controls:23.0.1")
    implementation("org.openjfx:javafx-fxml:23.0.1")
    implementation("org.openjfx:javafx-graphics:23.0.1")
    implementation("org.openjfx:javafx-base:23.0.1")
}


application {
    mainClass.set("com.example.TextAnalyzerGUI")
    mainModule.set("com.example")
}

javafx {
    version = "23.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

jlink {
    options.addAll(
        "--strip-debug",
        "--compress=2",
        "--no-header-files",
        "--no-man-pages",
        "--add-modules", "javafx.controls,javafx.fxml,javafx.base,javafx.graphics"
    )
    launcher {
        name = "TextAnalyzer"
    }
    imageDir.set(layout.buildDirectory.dir("jlink-image").get().asFile)
    jpackage {
        installerType = "exe"
        installerOptions = listOf("--win-dir-chooser", "--win-shortcut", "--win-menu")
        appVersion = version.toString()
        imageOutputDir = layout.buildDirectory.dir("jpackage").get().asFile
    }
}