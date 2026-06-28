package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.academic.system.controller.AcademicSystemController;

public class ClassRegistrationController {

    @FXML private TextField txtCourseCode;
    @FXML private TextField txtTitle;

    // Instancia o conector do backend
    private final AcademicSystemController systemController = new AcademicSystemController();

    @FXML
    public void handleSaveClass() {
        String courseCode = txtCourseCode.getText();
        String title = txtTitle.getText();

        try {
            // A interface é "burra", ela apenas passa os dados para o backend trabalhar
            boolean success = systemController.registerClass(courseCode, title);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Turma registrada com sucesso!");
                clearFields();
            }
        } catch (Exception e) {
            // Captura qualquer erro de validação do backend (ex: campos vazios, código duplicado)
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", e.getMessage());
        }
    }

    private void clearFields() {
        txtCourseCode.clear();
        txtTitle.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}