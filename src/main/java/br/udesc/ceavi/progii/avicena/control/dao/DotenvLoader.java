package br.udesc.ceavi.progii.avicena.control.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

final class DotenvLoader {

    static Map<String, String> load(Path path) throws IOException {
        Map<String, String> entries = new HashMap<>();
        for (String line : Files.readAllLines(path)) {
            String trimmed = line.strip();
            int separator = trimmed.indexOf('=');
            if (trimmed.startsWith("#") || separator < 0) continue;
            entries.put(
                    trimmed.substring(0, separator).strip(),
                    trimmed.substring(separator + 1).strip());
        }
        return entries;
    }
}
