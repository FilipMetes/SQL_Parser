package database;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnectionWindow extends Application {
    private final VBox root;

    public ConnectionWindow() {
        this.root = new VBox(10);
    }
    @Override
    public void start(Stage primaryStage) {

        Button walletConnection = new Button("Connect without wallet");
        Button nonWalletConnection = new Button("Connect with wallet");

        this.root.getChildren().add(nonWalletConnection);
        this.root.getChildren().add(walletConnection);

        walletConnection.setOnAction(e -> {
            this.root.getChildren().remove(walletConnection);
            this.initializeNonWalletConnection();
        });

        nonWalletConnection.setOnAction(e -> {
            this.root.getChildren().remove(nonWalletConnection);
            this.initializeWalletConnection();
        });

        Scene scene = new Scene(root, 400, 300);
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
        TextArea password = this.createTextArea("Password");

        Button submitButton = new Button("Connect");

        root.getChildren().addAll(host, port, sid, user, password, submitButton);

        submitButton.setOnAction(e -> {
            String userHost = host.getText();
            String userPort = port.getText();
            String userSid = sid.getText();
            String userUser = user.getText();
            String userPassword = password.getText();
            Connect connect = new Connect(userHost, userPort, userSid, userUser, userPassword);
            root.getChildren().removeAll(host, port, sid, user, password, submitButton);
        });

    }

    public void initializeWalletConnection() {
        TextArea user = this.createTextArea("User");
        TextArea password = this.createTextArea("Password");
        TextArea walletPath = this.createTextArea("WalletPath");
        TextArea tsnAlias = this.createTextArea("tsn_Alias");

        Button submitButton = new Button("Connect");
        root.getChildren().addAll(user, password, walletPath, tsnAlias, submitButton);

        submitButton.setOnAction(e -> {
            String userUser = user.getText();
            String userPassword = password.getText();
            String userWalletPath = walletPath.getText();
            String userTsnAlias = tsnAlias.getText();
            WalletConnect walletConnect = new WalletConnect(userUser, userPassword, userWalletPath, userTsnAlias);
            root.getChildren().removeAll(user, password, walletPath, tsnAlias, submitButton);
        });

    }
}


