package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DotenvLoaderTest {

    private Path path;

    @BeforeEach
    void createTempFile() throws Exception {
        path = Files.createTempFile("dotenv", ".env");
    }

    @Test
    void returnsEmptyMapForEmptyFile() throws Exception {
        assertEquals(Map.of(), DotenvLoader.load(path));
    }

    @Test
    void parsesASingleKeyValueLineIntoAMap() throws Exception {
        Files.writeString(path, "DB_PASS=secret\n");

        assertEquals(Map.of("DB_PASS", "secret"), DotenvLoader.load(path));
    }

    @Test
    void skipsBlankLines() throws Exception {
        Files.writeString(path, "\nDB_PASS=secret\n");

        assertEquals(Map.of("DB_PASS", "secret"), DotenvLoader.load(path));
    }

    @Test
    void skipsCommentLines() throws Exception {
        Files.writeString(path, "# comment\nDB_PASS=secret\n");

        assertEquals(Map.of("DB_PASS", "secret"), DotenvLoader.load(path));
    }
}
