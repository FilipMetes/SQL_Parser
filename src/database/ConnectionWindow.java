package database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnectionWindow extends Application {
    private final VBox window;
    private final HBox header;
    private final VBox root;

    public ConnectionWindow() {
        this.window = new VBox();
        this.root = new VBox(10);
        this.root.setFillWidth(false);
        root.setPadding(new Insets(0, 0, 0, 20));
        this.header = new HBox();

        this.window.getChildren().addAll(this.header, this.root);
    }
    @Override
    public void start(Stage primaryStage) {

        Button walletConnection = new Button("Connect with wallet");
        Button nonWalletConnection = new Button("Connect without wallet");
        Button returnButton = new Button("Return");

        this.header.getChildren().addAll(nonWalletConnection, walletConnection, returnButton);

        this.header.setAlignment(Pos.CENTER);


        returnButton.setOnAction(e -> {
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
        host.setPrefWidth(150);
        TextArea port = this.createTextArea("Port");
        port.setPrefWidth(150);
        TextArea sid = this.createTextArea("Sid");
        sid.setPrefWidth(150);
        TextArea user = this.createTextArea("User");
        user.setPrefWidth(150);
        TextArea password = this.createTextArea("Password");
        password.setPrefWidth(150);

        Button submitButton = new Button("Connect");

        root.getChildren().addAll(host, port, sid, user, password, submitButton);

        submitButton.setOnAction(e -> {
            String userHost = host.getText();
            String userPort = port.getText();
            String userSid = sid.getText();
            String userUser = user.getText();
            String userPassword = password.getText();
            Connect connect = new Connect( userHost, userPort, userSid, userUser, userPassword);
            root.getChildren().removeAll(host, port, sid, user, password, submitButton);
        });

    }

    public void initializeWalletConnection() {
        TextArea user = this.createTextArea("User");
        user.setPrefWidth(150);
        TextArea password = this.createTextArea("Password");
        password.setPrefWidth(150);
        TextArea walletPath = this.createTextArea("WalletPath");
        walletPath.setPrefWidth(150);
        TextArea tsnAlias = this.createTextArea("tsn_Alias");
        tsnAlias.setPrefWidth(150);

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

    public void clearRoot() {
        if (!this.root.getChildren().isEmpty()) {
            this.root.getChildren().clear();
        }
    }
}


