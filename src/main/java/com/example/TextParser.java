package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Класс для чтения текста из файла.
 */
public class TextParser {

    /**
     * Читает текст из файла по указанному пути.
     *
     * @param filePath Путь к файлу для чтения
     * @return Содержимое файла в виде строки
     * @throws IOException Если файл не может быть прочитан
     */
    public String parseText(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
