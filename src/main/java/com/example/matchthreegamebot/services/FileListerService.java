package com.example.matchthreegamebot.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileListerService {
    public List<String> listFilesInDirectory(String directoryPath) {
        try (Stream<Path> pathsStream = Files.list(Paths.get(directoryPath))) {
            return pathsStream
                    .filter(Files::isRegularFile)
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении директории: " + e.getMessage());
            return List.of();
        }
    }
}