package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * Класс для обработки текста с использованием ключевых слов.
 * Выполняет анализ текста и определение темы.
 */
public class TextProcessor {
    private static final Logger logger = LogManager.getLogger(TextProcessor.class);
    private Map<String, String[]> keywords;

    /**
     * Конструктор для создания объекта обработки текста с указанием пути к файлу с ключевыми словами.
     *
     * @param keywordFilePath Путь к файлу с ключевыми словами
     * @throws IOException Если файл с ключевыми словами не может быть прочитан
     */
    public TextProcessor(String keywordFilePath) throws IOException {
        logger.info("Loading keywords from file: {}", keywordFilePath);
        loadKeywordsFromFile(keywordFilePath);
    }

    /**
     * Загружает ключевые слова из файла.
     *
     * @param keywordFilePath Путь к файлу с ключевыми словами
     * @throws IOException Если файл не может быть прочитан
     */
    public void loadKeywordsFromFile(String keywordFilePath) throws IOException {
        KeywordLoader loader = new KeywordLoader(keywordFilePath);
        this.keywords = loader.loadKeywords();
        logger.info("Loaded keywords: {}", keywords.size());
    }

    /**
     * Анализирует текст, определяя тему на основе ключевых слов.
     *
     * @param text Текст для анализа
     * @return Результат анализа текста
     */
    public String analyzeText(String text) {
        if (keywords == null || keywords.isEmpty()) {
            logger.error("Keywords are not loaded");
            return "Ошибка: ключевые слова не загружены.";
        }
        ThemeDetector themeDetector = new ThemeDetector(keywords);
        return themeDetector.detectTheme(text);
    }
}
