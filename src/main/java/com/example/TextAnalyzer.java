package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Основной класс для анализа текста из файла с использованием ключевых слов.
 * Осуществляет проверку наличия файла и запускает анализ с помощью других компонентов.
 */
public class TextAnalyzer {
    private static final Logger logger = LogManager.getLogger(TextAnalyzer.class);

    /**
     * Точка входа для анализа текста из командной строки.
     *
     * @param args Аргументы командной строки, где первый аргумент - это путь к файлу
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            logger.error("Please specify the path to the text file");
            return;
        }

        String filePath = args[0];
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                logger.error("File not found: {}" + filePath);
                return;
            }

            KeywordLoader keywordLoader = new KeywordLoader("keywords.txt");
            ThemeDetector themeDetector = new ThemeDetector(keywordLoader.loadKeywords());
            TextParser textParser = new TextParser();

            String text = textParser.parseText(String.valueOf(file));
            String theme = themeDetector.detectTheme(text);

            logger.info("Detected text theme: {}" + theme);
        } catch (IOException e) {
            logger.error("Error processing file: {}" + e.getMessage(), e);
        }
    }
}
