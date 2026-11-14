package br.udesc.ceavi.progii.avicena.control.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DotenvLoaderTest {

    @TempDir
    Path directory;

    private Map<String, String> load(String content) throws Exception {
        Path path = directory.resolve(".env");
        Files.writeString(path, content);
        return DotenvLoader.load(path);
    }

    @Test
    void returnsEmptyMapForEmptyFile() throws Exception {
        assertEquals(Map.of(), load(""));
    }

    @Test
    void parsesASingleKeyValueLineIntoAMap() throws Exception {
        assertEquals(Map.of("DB_PASS", "secret"), load("DB_PASS=secret\n"));
    }

    @Test
    void skipsBlankLines() throws Exception {
        assertEquals(Map.of("DB_PASS", "secret"), load("\nDB_PASS=secret\n"));
    }

    @Test
    void skipsWhitespaceOnlyLines() throws Exception {
        assertEquals(Map.of("DB_PASS", "secret"), load("   \nDB_PASS=secret\n"));
    }

    @Test
    void skipsCommentLines() throws Exception {
        assertEquals(Map.of("DB_PASS", "secret"), load("# comment\nDB_PASS=secret\n"));
    }

    @Test
    void skipsIndentedCommentLines() throws Exception {
        assertEquals(Map.of("DB_PASS", "secret"), load("  # comment\nDB_PASS=secret\n"));
    }

    @Test
    void keepsEqualsSignsInsideTheValue() throws Exception {
        assertEquals(Map.of("DB_PASS", "a=b=c"), load("DB_PASS=a=b=c\n"));
    }

    @Test
    void allowsAnEmptyValue() throws Exception {
        assertEquals(Map.of("DB_PASS", ""), load("DB_PASS=\n"));
    }

    @Test
    void skipsALineWithoutAnEqualsSign() throws Exception {
        assertEquals(Map.of("DB_PASS", "secret"), load("NOT_A_PAIR\nDB_PASS=secret\n"));
    }

    @Test
    void trimsTheKeyAndTheValue() throws Exception {
        assertEquals(Map.of("DB_PASS", "secret"), load("  DB_PASS = secret  \n"));
    }

    @Test
    void handlesWindowsLineEndings() throws Exception {
        assertEquals(
                Map.of("DB_USER", "postgres", "DB_PASS", "secret"), load("DB_USER=postgres\r\nDB_PASS=secret\r\n"));
    }
}
