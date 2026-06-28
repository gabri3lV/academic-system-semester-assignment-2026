package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.academic.system.controller.AuthenticationController;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private final AuthenticationController authController = new AuthenticationController();

    @FXML
    public void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        boolean success = authController.authenticate(username, password);

        if (success) {
            loadMainScreen();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro de Autenticação", "Usuário ou senha inválidos.");
        }
    }

    private void loadMainScreen() {
        try {
            // Carrega o FXML da Tela Principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/academic/system/view/MainScreen.fxml"));
            Parent root = loader.load();

            // Pega a janela atual (Stage) a partir do campo de texto
            Stage stage = (Stage) txtUsername.getScene().getWindow();

            // Troca a cena da janela para a Tela Principal
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Academic System - Dashboard");
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro Fatal", "Não foi possível carregar a tela principal.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}