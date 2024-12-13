package com.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Графический интерфейс приложения для загрузки текста, ключевых слов и анализа текста.
 * Пользователь может загрузить текст и ключевые слова, а также запустить анализ.
 */
public class TextAnalyzerGUI extends Application {
    private static final Logger logger = LogManager.getLogger(TextAnalyzerGUI.class);
    private TextArea textArea;
    private TextArea resultArea;
    private TextProcessor textProcessor;

    /**
     * Запуск графического интерфейса приложения.
     *
     * @param primaryStage Основная сцена приложения
     */
    @Override
    public void start(Stage primaryStage) {
        logger.info("Launching GUI application");
        initializeUI(primaryStage);
        primaryStage.show();
    }

    /**
     * Инициализация пользовательского интерфейса.
     *
     * @param primaryStage Основная сцена приложения
     */
    private void initializeUI(Stage primaryStage) {
        textArea = new TextArea();
        resultArea = new TextArea();
        resultArea.setEditable(false);

        Button loadTextButton = new Button("Загрузить текст");
        Button loadKeywordsButton = new Button("Загрузить ключевые слова");
        Button analyzeButton = new Button("Анализировать");

        loadTextButton.setOnAction(e -> loadFile(primaryStage));
        loadKeywordsButton.setOnAction(e -> loadKeywords(primaryStage));
        analyzeButton.setOnAction(e -> analyzeText());

        VBox vbox = new VBox(loadTextButton, loadKeywordsButton, textArea, analyzeButton, resultArea);
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Анализатор текста");
    }

    /**
     * Загрузка текстового файла.
     *
     * @param stage Основная сцена приложения
     */
    private void loadFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                textArea.setText(content);
                logger.info("File {} successfully loaded", file.getName());
            } catch (IOException e) {
                resultArea.setText("Ошибка при загрузке файла: " + e.getMessage());
                logger.error("Error loading file: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * Загрузка файла с ключевыми словами.
     *
     * @param stage Основная сцена приложения
     */
    private void loadKeywords(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                textProcessor = new TextProcessor(file.getAbsolutePath());
                resultArea.setText("Ключевые слова успешно загружены из файла: " + file.getName());
                logger.info("Keywords loaded from file: {}", file.getName());
            } catch (IOException e) {
                resultArea.setText("Ошибка при загрузке ключевых слов: " + e.getMessage());
                logger.error("Error loading keywords: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * Запуск анализа текста.
     */
    private void analyzeText() {
        String text = textArea.getText();
        if (text.isEmpty()) {
            resultArea.setText("Пожалуйста, загрузите текст для анализа.");
            logger.warn("Attempt to analyze without text");
            return;
        }
        if (textProcessor == null) {
            resultArea.setText("Пожалуйста, загрузите файл с ключевыми словами перед анализом.");
            logger.warn("Attempt to analyze without loading keywords.");
            return;
        }

        String result = textProcessor.analyzeText(text);
        resultArea.setText(result);
        logger.info("Text analyzed. Result: {}", result);
    }

    /**
     * Точка входа для запуска графического интерфейса приложения.
     *
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }
}
