package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для загрузки ключевых слов из файла.
 *
 */
public class KeywordLoader {

    private static final Logger logger = LogManager.getLogger(KeywordLoader.class);
    private final String filePath;

    /**
     * Конструктор для инициализации пути к файлу.
     *
     * @param filePath Путь к файлу с ключевыми словами.
     */
    public KeywordLoader(String filePath) {
        this.filePath = filePath;
        logger.info("Initializing KeywordLoader with file: {}", filePath);
    }

    /**
     * Загружает ключевые слова из файла.
     *
     * @return Карта, где ключ — это тема, а значение — массив ключевых слов.
     * @throws IOException Если не удается прочитать файл.
     */
    public Map<String, String[]> loadKeywords() throws IOException {
        Map<String, String[]> keywords = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    // Приводим ключ и значения к нижнему регистру
                    String theme = parts[0].trim().toLowerCase();
                    String[] words = parts[1].trim().split(",\\s*");
                    keywords.put(theme, words);
                    logger.debug("Loaded keywords for theme: {}", theme);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading file: {}", filePath, e);
            throw e;
        }
        return keywords;
    }
}
