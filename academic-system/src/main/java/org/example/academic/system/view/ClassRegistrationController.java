package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.controller.ControllerFactory;

public class ClassRegistrationController {

    @FXML private TextField txtCourseCode;
    @FXML private TextField txtTitle;

    // Instancia o conector do backend
    private final AcademicSystemController systemController =
            ControllerFactory.getAcademicSystemController();

    @FXML
    public void handleSaveClass() {
        String courseCode = txtCourseCode.getText();
        String title = txtTitle.getText();

        try {
            systemController.registerClass(courseCode, title);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Turma registrada com sucesso!");
            clearFields();
        } catch (Exception e) {
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