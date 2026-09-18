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
            String[] parts = line.split("=");
            entries.put(parts[0], parts[1]);
        }
        return entries;
    }
}
