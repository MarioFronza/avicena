package br.udesc.ceavi.progii.avicena;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/** Layer rules from CLAUDE.md, checked against the source so they can't drift. */
class ArchitectureTest {

    private static final Path MAIN = Path.of("src/main/java/br/udesc/ceavi/progii/avicena");

    @Test
    void onlyPersistenceTouchesJpa() {
        assertEquals(
                List.of(),
                violations(
                        file -> !file.contains("/infrastructure/persistence/") && !file.contains("/control/dao/"),
                        Pattern.compile("^import (jakarta\\.persistence\\.|.*\\.persistence\\.\\w+Entity;)")));
    }

    @Test
    void controllersKnowNoUi() {
        assertEquals(
                List.of(),
                violations(
                        file -> file.contains("/infrastructure/controller/"),
                        Pattern.compile("^import (javax\\.swing|java\\.awt|.*\\.infrastructure\\.ui\\.)")));
    }

    @Test
    void domainAndUseCasesDependOnNothingOutward() {
        assertEquals(
                List.of(),
                violations(
                        file -> file.contains("/domain/") || file.contains("/usecase/"),
                        Pattern.compile(
                                "^import (jakarta\\.|javax\\.|java\\.awt|.*\\.infrastructure\\.|.*\\.control\\.)")));
    }

    private static List<String> violations(Predicate<String> scope, Pattern forbidden) {
        try (Stream<Path> files = Files.walk(MAIN)) {
            return files.filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> scope.test(path.toString().replace('\\', '/')))
                    .flatMap(path -> lines(path)
                            .filter(line -> forbidden.matcher(line).find())
                            .map(line -> MAIN.relativize(path) + ": " + line))
                    .sorted()
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Stream<String> lines(Path path) {
        try {
            return Files.readAllLines(path).stream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
