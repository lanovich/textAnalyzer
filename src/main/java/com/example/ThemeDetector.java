package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для определения темы текста на основе ключевых слов.
 */
public class ThemeDetector {
    private final Map<String, String[]> keywords;

    /**
     * Конструктор для создания объекта детектора темы.
     *
     * @param keywords Карта ключевых слов для каждой темы
     */
    public ThemeDetector(Map<String, String[]> keywords) {
        this.keywords = keywords;
    }

    /**
     * Определяет тему текста на основе ключевых слов.
     *
     * @param text Текст для анализа
     * @return Определенная тема
     */
    public String detectTheme(String text) {
        String normalizedText = text.toLowerCase();
        Map<String, Integer> keywordStats = collectKeywordStats(normalizedText);
        String detectedTheme = determineTheme(keywordStats);
        return buildResult(keywordStats, detectedTheme);
    }

    /**
     * Собирает статистику по ключевым словам в тексте.
     *
     * @param text Текст для анализа
     * @return Карта статистики по ключевым словам
     */
    private Map<String, Integer> collectKeywordStats(String text) {
        Map<String, Integer> keywordStats = new HashMap<>();
        for (Map.Entry<String, String[]> entry : keywords.entrySet()) {
            String theme = entry.getKey();
            for (String keyword : entry.getValue()) {
                int count = countOccurrences(text, keyword.toLowerCase());
                if (count > 0) {
                    keywordStats.put(theme + " - " + keyword, count);
                }
            }
        }
        return keywordStats;
    }

    /**
     * Определяет тему на основе собранной статистики по ключевым словам.
     *
     * @param keywordStats Статистика по ключевым словам
     * @return Определенная тема
     */
    private String determineTheme(Map<String, Integer> keywordStats) {
        Map<String, Integer> themeCounts = new HashMap<>();
        for (String key : keywordStats.keySet()) {
            String theme = key.split(" - ")[0];
            themeCounts.put(theme, themeCounts.getOrDefault(theme, 0) + keywordStats.get(key));
        }

        return themeCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Тематика не определена");
    }

    /**
     * Строит результат анализа текста, включая статистику по ключевым словам и определенную тему.
     *
     * @param keywordStats Статистика по ключевым словам
     * @param detectedTheme Определенная тема
     * @return Результат анализа
     */
    private String buildResult(Map<String, Integer> keywordStats, String detectedTheme) {
        StringBuilder result = new StringBuilder("Результат анализа текста:\n\n");
        keywordStats.forEach((key, value) -> result.append(key).append(": ").append(value).append("\n"));
        result.append("\nОпределенная тема: ").append(detectedTheme);
        return result.toString();
    }

    /**
     * Считает количество вхождений ключевого слова в тексте.
     *
     * @param text Текст для анализа
     * @param keyword Ключевое слово
     * @return Количество вхождений ключевого слова
     */
    private int countOccurrences(String text, String keyword) {
        Pattern pattern = Pattern.compile(Pattern.quote(keyword));
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
