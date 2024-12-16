plugins {
    id("java")


    application
    id("org.openjfx.javafxplugin") version "0.1.0"

}
javafx {
    version = "23.0.1"
    modules = listOf("javafx.controls", "javafx.graphics", "javafx.fxml", "javafx.media", "javafx.swing")
}
group = "ies.pedro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    //interfaz gráfica
    implementation("io.github.palexdev:materialfx:11.17.0")
    implementation("org.controlsfx:controlsfx:11.2.1")

    implementation("fr.brouillard.oss:cssfx:11.5.1")
    implementation("com.dlsc.formsfx:formsfx-core:11.6.0")
    implementation("com.dlsc.formsfx:formsfx:11.6.0")
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-antdesignicons-pack:12.3.1")

    testImplementation("org.apache.derby:derby:10.16.1.1")
// Conexión local.
    implementation("org.apache.derby:derby:10.16.1.1")
// Conexión remota
    implementation("org.apache.derby:derbyclient:10.16.1.1")
    //base de datos

    //eventbus
    implementation("com.google.guava:guava:33.3.1-jre")
}

tasks.test {
    useJUnitPlatform()
}

application {
    // Define the main class for the application.
    mainClass = "ies.accesodatos.Main"
}