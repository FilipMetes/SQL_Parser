package database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionWindow extends Application {
    private final VBox window;
    private final HBox header;
    private final HBox footer;
    private final VBox root;
    private final Button submitButton;
    private final Button walletConnection;
    private final Button nonWalletConnection;
    private final Button returnButton;
    private ImageView imageView;

    public ConnectionWindow() throws FileNotFoundException {
        this.window = new VBox();

        //Initialize main layout box
        this.root = new VBox(10);
        this.root.setFillWidth(false);
        this.root.setPadding(new Insets(0, 0, 0, 20));
        VBox.setVgrow(this.root, javafx.scene.layout.Priority.ALWAYS);
        VBox.setMargin(this.root, new Insets(10, 10 , 10, 5));

        this.header = new HBox();
        this.footer = new HBox();

        //Add root, footer, and header to the window
        this.window.getChildren().addAll(this.header, new Separator(), this.root, new Separator(), this.footer);

        //Adding a logo to the root
        Image logo = new Image(new FileInputStream("Logo.png"));
        this.imageView = new ImageView(logo);
        this.imageView.setFitWidth(250);
        this.imageView.setPreserveRatio(true);

        // Add the imageContainer to VBox
        this.imageView.setTranslateX(55);
        //this.root.setAlignment(Pos.CENTER);
        this.root.getChildren().add(this.imageView);

        //Initialize submitButton
        this.submitButton = new Button("Connect"); //submit button
        this.footer.getChildren().addAll(this.submitButton);
        HBox.setMargin(this.submitButton, new Insets(10, 20, 10, 10));

        //Initialize connection buttons
        this.walletConnection = new Button("Connect with wallet");
        this.nonWalletConnection = new Button("Connect without wallet");
        this.returnButton = new Button("Return");
        this.header.getChildren().addAll(this.nonWalletConnection, this.walletConnection, this.returnButton);
        HBox.setMargin(this.nonWalletConnection, new Insets(5, 5, 10, 5));
        HBox.setMargin(this.walletConnection, new Insets(5, 20, 10, 5));
        HBox.setMargin(this.returnButton, new Insets(5, 10, 10, 10));
        this.header.setAlignment(Pos.CENTER);
    }
    @Override
    public void start(Stage primaryStage) {

        //Button listeners
        this.returnButton.setOnAction(e -> {
            this.footer.getChildren().removeIf(node -> node instanceof Label);
            this.clearRoot();
            this.root.getChildren().add(this.imageView);
        });

        this.walletConnection.setOnAction(e -> {
            this.clearRoot();
            this.initializeWalletConnection();
        });

        this.nonWalletConnection.setOnAction(e -> {
            this.clearRoot();
            this.initializeNonWalletConnection();
        });

        //Display the connection Window
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

    public void clearRoot() {
        if (!this.root.getChildren().isEmpty()) {
            this.root.getChildren().clear();
        }
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
        data.put("inputHost", host);
        data.put("inputPort", port);
        data.put("inputSid", sid);
        data.put("inputUser", user);
        data.put("inputPassword", password);

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
        data.put("inputUser", user);
        data.put("inputPassword", password);
        data.put("inputWalletPath", walletPath);
        data.put("inputTnsAlias", tnsAlias);
        this.callSubmitButton(data, "wallet");


    }

    public void callSubmitButton(HashMap<String, TextInputControl> data, String identificator) {

        // Creating infoLabel label
        this.footer.getChildren().removeIf(node -> node instanceof Label);
        Label infoLabel = new Label();
        infoLabel.setStyle("-fx-text-fill: red");

        //Editing infoLabel Label to fit the footer
        HBox.setHgrow(infoLabel, Priority.ALWAYS);
        infoLabel.setMaxWidth(Double.MAX_VALUE);
        infoLabel.setMaxHeight(Double.MAX_VALUE);
        infoLabel.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(infoLabel, new Insets(8, 10, 12, 10));

        //listener for submit button
        this.submitButton.setOnAction(e -> {

            if (this.root.getChildren().isEmpty()) {
                return;
            }

            boolean isConnected = false;
            // Remove previous empty label if present
            this.footer.getChildren().removeIf(node -> node instanceof Label);

            // if there is any text field empty show an infoLabel
            for (Map.Entry<String, TextInputControl> entry : data.entrySet()) {
                if (entry.getValue().getText().isEmpty()) {
                    infoLabel.setText("Fill all fields!!");
                    this.footer.getChildren().add(infoLabel);
                    return;
                }
            }

            // Opening a nonWallet connection
            if (identificator.equals("nonWallet")) {
                String inputHost = data.get("inputHost").getText();
                String inputPort = data.get("inputPort").getText();
                String inputSid = data.get("inputSid").getText();
                String inputUser = data.get("inputUser").getText();
                String inputPassword = data.get("inputPassword").getText();

                NonWalletConnect nonWalletConnection = new NonWalletConnect(inputHost, inputPort, inputSid, inputUser, inputPassword);
                if (nonWalletConnection.connect()) {
                    isConnected = true;
                }
            //Opening a wallet connection
            } else if (identificator.equals("wallet")) {
                String inputUser = data.get("inputUser").getText();
                String inputPassword = data.get("inputPassword").getText();
                String inputWalletPath = data.get("inputWalletPath").getText();
                String inputTnsAlias = data.get("inputTnsAlias").getText();

                WalletConnect walletConnection = new WalletConnect(inputUser, inputPassword, inputWalletPath, inputTnsAlias);
                if (walletConnection.connect()) {
                    isConnected = true;
                }
            }

            if (isConnected) {
                infoLabel.setText("Connected");
            } else {
                infoLabel.setText("Error!!");
            }
            this.footer.getChildren().add(infoLabel);

            this.root.getChildren().clear();
        });
    }
}


