package database;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI1 extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Multi-line editable text area
        TextArea textArea = new TextArea();
        textArea.setPromptText("Type your text here...");
        textArea.setWrapText(true); // wraps lines

        // Button to read user input
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userInput = textArea.getText();
            Connect connect = new Connect("obelix.fri.uniza.sk" , "1521", "orcl", "metes", "Hulkcica17");
        });

        // Layout container
        VBox root = new VBox(10); // 10px spacing
        root.getChildren().addAll(textArea, submitButton);

        // Scene and stage setup
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("GUI-DEMO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

