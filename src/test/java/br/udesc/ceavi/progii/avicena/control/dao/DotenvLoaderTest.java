package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DotenvLoaderTest {

    @Test
    void returnsEmptyMapForEmptyFile() throws Exception {
        Path path = Files.createTempFile("dotenv", ".env");

        assertEquals(Map.of(), DotenvLoader.load(path));
    }

    @Test
    void parsesASingleKeyValueLineIntoAMap() throws Exception {
        Path path = Files.createTempFile("dotenv", ".env");
        Files.writeString(path, "DB_PASS=secret\n");

        assertEquals(Map.of("DB_PASS", "secret"), DotenvLoader.load(path));
    }
}
