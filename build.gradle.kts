plugins {
    application
    id("com.diffplug.spotless") version "8.10.0"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("br.udesc.ceavi.progii.avicena.main.AvicenaMain")
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:7.4.5.Final")
    implementation("org.flywaydb:flyway-core:13.1.0")
    implementation("org.flywaydb:flyway-database-postgresql:13.1.0")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("com.itextpdf:itextpdf:5.5.13.6")

    testImplementation(platform("org.junit:junit-bom:6.1.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation(platform("org.testcontainers:testcontainers-bom:2.0.5"))
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    // Testcontainers' bundled docker-java client negotiates down to API 1.32 by
    // default, which recent Docker Engine versions reject. Pinning a version both
    // old and new engines support avoids relying on negotiation.
    systemProperty("api.version", "1.41")
}

spotless {
    java {
        target("src/**/*.java")
        palantirJavaFormat()
        removeUnusedImports()
    }
}
