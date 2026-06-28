package org.example.academic.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.academic.system.security.Session;

public class JavaFXMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Agora o sistema começa pela tela de Login!
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/academic/system/view/LoginScreen.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Academic System - Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false); // Trava o tamanho da janela de login
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}