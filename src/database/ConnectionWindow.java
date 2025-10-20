package database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectionWindow extends Application {
    private final VBox window;
    private final HBox header;
    private final HBox footer;
    private final VBox root;
    private final Button submitButton;

    public ConnectionWindow() {
        this.window = new VBox();

        this.root = new VBox(10);
        this.root.setFillWidth(false);
        this.root.setPadding(new Insets(0, 0, 0, 20));
        VBox.setVgrow(this.root, javafx.scene.layout.Priority.ALWAYS);
        VBox.setMargin(this.root, new Insets(10, 10 , 10, 5));

        this.header = new HBox();
        this.footer = new HBox();

        this.window.getChildren().addAll(this.header, new Separator(), this.root, new Separator(), this.footer);

        this.submitButton = new Button("Connect"); //submit button
        this.footer.getChildren().addAll(this.submitButton);
        HBox.setMargin(this.submitButton, new Insets(10, 20, 10, 10));
    }
    @Override
    public void start(Stage primaryStage) {

        Button walletConnection = new Button("Connect with wallet");
        Button nonWalletConnection = new Button("Connect without wallet");
        Button returnButton = new Button("Return");

        this.header.getChildren().addAll(nonWalletConnection, walletConnection, returnButton);

        HBox.setMargin(nonWalletConnection, new Insets(5, 5, 10, 5)); // top, right, bottom, left
        HBox.setMargin(walletConnection, new Insets(5, 20, 10, 5));
        HBox.setMargin(returnButton, new Insets(5, 10, 10, 10));

        this.header.setAlignment(Pos.CENTER);


        returnButton.setOnAction(e -> {
            this.footer.getChildren().removeIf(node -> node instanceof Label);
            this.clearRoot();
        });

        walletConnection.setOnAction(e -> {
            this.clearRoot();
            this.initializeWalletConnection();
        });

        nonWalletConnection.setOnAction(e -> {
            this.clearRoot();
            this.initializeNonWalletConnection();
        });


        Scene scene = new Scene(this.window, 400, 300);

        primaryStage.setTitle("SQL-Parser_DEMO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public TextArea createTextArea(String text) {
        TextArea textArea = new TextArea();
        textArea.setPromptText(text);
        textArea.setWrapText(true);

        return textArea;
    }

    public void initializeNonWalletConnection() {
        TextArea host = this.createTextArea("Host");
        TextArea port = this.createTextArea("Port");
        TextArea sid = this.createTextArea("Sid");
        TextArea user = this.createTextArea("User");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        this.root.getChildren().addAll(user, password, host, port, sid);
        this.setRootComponents(25, 150);

        HashMap<String, TextInputControl> data =  new HashMap<>();
        data.put("userHost", host);
        data.put("userPort", port);
        data.put("userSid", sid);
        data.put("userUser", user);
        data.put("userPassword", password);

        this.callSubmitButton(data, "nonWallet");
    }

    public void initializeWalletConnection() {
        TextArea user = this.createTextArea("User");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        TextArea walletPath = this.createTextArea("WalletPath");
        TextArea tnsAlias = this.createTextArea("tns_Alias");

        this.root.getChildren().addAll(user, password, walletPath, tnsAlias);
        this.setRootComponents(25, 150);

        HashMap<String, TextInputControl> data =  new HashMap<>();
        data.put("userUser", user);
        data.put("userPassword", password);
        data.put("userWalletPath", walletPath);
        data.put("userTnsAlias", tnsAlias);
        this.callSubmitButton(data, "wallet");


    }

    public void clearRoot() {
        if (!this.root.getChildren().isEmpty()) {
            this.root.getChildren().clear();
        }
    }

    public void setRootComponents(int height, int width) {
        for (Node node : this.root.getChildren()) {
            if (node instanceof Button) {
                continue;
            } else if (node instanceof PasswordField) {
                ((PasswordField) node).setMinHeight(20);
                ((PasswordField) node).setPrefHeight(height + 2);
                continue;
            }
            var textField = ((TextInputControl) node);
            textField.setMinHeight(20);
            textField.setPrefHeight(height);
            textField.setPrefWidth(width);
        }
    }

    public void callSubmitButton(HashMap<String, TextInputControl> data, String identificator) {
        // Create a reusable "Empty lines" label
        this.footer.getChildren().removeIf(node -> node instanceof Label);
        Label emptyLinesWarning = new Label("Empty lines");
        emptyLinesWarning.setStyle("-fx-text-fill: red");

        // Let the label expand to fill the HBox space
        HBox.setHgrow(emptyLinesWarning, Priority.ALWAYS);
        emptyLinesWarning.setMaxWidth(Double.MAX_VALUE);
        emptyLinesWarning.setMaxHeight(Double.MAX_VALUE);

// Align text inside the label to center
        emptyLinesWarning.setAlignment(Pos.CENTER_RIGHT);

// Optional: add padding/margin
        HBox.setMargin(emptyLinesWarning, new Insets(8, 10, 12, 10));

        this.submitButton.setOnAction(e -> {
            // Remove previous empty label if present
            this.footer.getChildren().removeIf(node -> node instanceof Label);

            // Check for empty fields
            for (Map.Entry<String, TextInputControl> entry : data.entrySet()) {
                if (entry.getValue().getText().isEmpty()) {
                    this.footer.getChildren().add(emptyLinesWarning);
                    return; // stop execution if any field is empty
                }
            }

            // All fields filled → proceed
            if (identificator.equals("nonWallet")) {
                String userHost = data.get("userHost").getText();
                String userPort = data.get("userPort").getText();
                String userSid = data.get("userSid").getText();
                String userUser = data.get("userUser").getText();
                String userPassword = data.get("userPassword").getText();

                Connect connect = new Connect(userHost, userPort, userSid, userUser, userPassword);

            } else if (identificator.equals("wallet")) {
                String userUser = data.get("userUser").getText();
                String userPassword = data.get("userPassword").getText();
                String userWalletPath = data.get("userWalletPath").getText();
                String userTnsAlias = data.get("userTnsAlias").getText();

                WalletConnect walletConnect = new WalletConnect(userUser, userPassword, userWalletPath, userTnsAlias);
            }

            // Clear the root (hide the form)
            this.root.getChildren().clear();
        });
    }
}


