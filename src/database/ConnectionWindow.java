package database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
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
    private final HBox root;
    private final VBox leftRoot;
    private final VBox rightRoot;
    private final Button submitButton;
    private final Button walletButton;
    private final Button nonWalletButton;
    private final Button returnButton;
    private ImageView imageView;

    public ConnectionWindow() throws FileNotFoundException {
        this.window = new VBox();

        //Initialize main layout box
        this.root = new HBox(10);
        this.root.setPadding(new Insets(0, 0, 0, 20));
        this.root.setAlignment(Pos.CENTER);
        VBox.setVgrow(this.root, Priority.ALWAYS);

        //initializing root boxes
        this.leftRoot = new VBox();
        this.rightRoot = new VBox();
        this.leftRoot.setAlignment(Pos.CENTER);
        this.leftRoot.setSpacing(10);
        this.rightRoot.setAlignment(Pos.CENTER);

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
        this.root.getChildren().add(this.imageView);

        //Initialize submitButton
        this.submitButton = new Button("Connect"); //submit button
        this.footer.getChildren().addAll(this.submitButton);
        HBox.setMargin(this.submitButton, new Insets(10, 20, 10, 10));

        //Initialize connection buttons
        this.walletButton = new Button("Connect with wallet");
        this.nonWalletButton = new Button("Connect without wallet");
        this.returnButton = new Button("Return");
        this.header.getChildren().addAll(this.nonWalletButton, this.walletButton, this.returnButton);
        HBox.setMargin(this.nonWalletButton, new Insets(5, 5, 10, 5));
        HBox.setMargin(this.walletButton, new Insets(5, 20, 10, 5));
        HBox.setMargin(this.returnButton, new Insets(5, 10, 10, 10));
        this.header.setAlignment(Pos.CENTER);
    }
    @Override
    public void start(Stage primaryStage) {

        //Button listeners
        this.returnButton.setOnAction(e -> {
            this.footer.getChildren().removeIf(node -> node instanceof Label);
            this.clearRoot();
            this.imageView.setFitWidth(250);
            this.root.getChildren().add(this.imageView);
        });

        this.walletButton.setOnAction(e -> {
            this.clearRoot();
            this.root.getChildren().addAll(this.leftRoot, this.rightRoot);
            this.initializeNonWalletWindow();
        });

        this.nonWalletButton.setOnAction(e -> {
            this.clearRoot();
            this.root.getChildren().addAll(this.leftRoot, this.rightRoot);
            this.initializeWalletWindow();
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
        for (Node node : this.leftRoot.getChildren()) {
            if (node instanceof PasswordField passwordField) {
                passwordField.setMinHeight(20);
                passwordField.setPrefHeight(height + 2);
            } else if (node instanceof TextInputControl textField) {
                textField.setMinHeight(20);
                textField.setPrefHeight(height);
                textField.setPrefWidth(width);
            }
        }
    }

    public void clearRoot() {
        if (!this.root.getChildren().isEmpty()) {
            this.root.getChildren().clear();
            this.leftRoot.getChildren().clear();
            this.rightRoot.getChildren().clear();
        }
    }


    public void initializeWalletWindow() {
        TextArea host = this.createTextArea("Host");
        TextArea port = this.createTextArea("Port");
        TextArea sid = this.createTextArea("Sid");
        TextArea user = this.createTextArea("User");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        //adding textfield to leftroot and image to right root
        this.leftRoot.getChildren().addAll(user, password, host, port, sid);
        this.rightRoot.getChildren().add(this.imageView);
        this.imageView.setFitWidth(150);
        this.setRootComponents(25, 150);

        HashMap<String, TextInputControl> data =  new HashMap<>();
        data.put("inputHost", host);
        data.put("inputPort", port);
        data.put("inputSid", sid);
        data.put("inputUser", user);
        data.put("inputPassword", password);

        this.callSubmitButton(data, "nonWallet");
    }

    public void initializeNonWalletWindow() {
        TextArea user = this.createTextArea("User");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        TextArea walletPath = this.createTextArea("WalletPath");
        TextArea tnsAlias = this.createTextArea("tns_Alias");

        //adding textfield to leftroot and image to right root
        this.leftRoot.getChildren().addAll(user, password, walletPath, tnsAlias);
        this.rightRoot.getChildren().add(this.imageView);
        this.imageView.setFitWidth(150);
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

            if (this.leftRoot.getChildren().isEmpty()) {
                return;
            }

            // Remove previous empty label if present
            this.footer.getChildren().removeIf(node -> node instanceof Label);

            boolean isConnected = false;

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
                NonWalletConnect nonWalletConnection = new NonWalletConnect(data);
                if (nonWalletConnection.connect()) {
                    isConnected = true;
                }
            //Opening a wallet connection
            } else if (identificator.equals("wallet")) {

                WalletConnect walletConnection = new WalletConnect(data);
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
            //clearing root after connectino
            this.clearRoot();
        });
    }
}


