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
        TextArea host = this.createTextArea("Host");
        TextArea port = this.createTextArea("Port");
        TextArea sid = this.createTextArea("Sid");
        TextArea user = this.createTextArea("User");
        TextArea password = this.createTextArea("Password");


        // Button to read user input
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userHost = host.getText();
            String userPort = port.getText();
            String userSid = sid.getText();
            String userUser = user.getText();
            String userPassword = password.getText();
            Connect connect = new Connect(userHost, userPort, userSid, userUser, userPassword);
        });

        // Layout container
        VBox root = new VBox(10); // 10px spacing
        root.getChildren().addAll(host, port, sid, user, password, submitButton);

        // Scene and stage setup
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("GUI-DEMO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public TextArea createTextArea(String text) {
        TextArea textArea = new TextArea();
        textArea.setPromptText(text);
        textArea.setWrapText(true);

        return textArea;
    }

}


