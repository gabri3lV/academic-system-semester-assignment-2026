package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.controller.ControllerFactory;

public class AssessmentRegistrationController {

    @FXML private ComboBox<String> cbCourseCode;
    @FXML private ComboBox<String> cbAssessmentType;
    @FXML private TextField txtValue;
    @FXML private TextField txtWeight;

    private final AcademicSystemController systemController =
            ControllerFactory.getAcademicSystemController();

    @FXML
    public void initialize() {
        // Busca turmas reais do sistema
        cbCourseCode.getItems().clear();
        ControllerFactory.getAcademicSystemController()
                .getClasses()
                .forEach(c -> cbCourseCode.getItems().add(c.getCode()));

        cbAssessmentType.getItems().addAll(
                "exam", "assignment", "seminar", "practicalassignment"
        );
    }

    @FXML
    public void handleSaveAssessment() {
        String courseCode = cbCourseCode.getValue();
        String type = cbAssessmentType.getValue();

        try {
            double value = Double.parseDouble(txtValue.getText().replace(",", "."));
            double weight = Double.parseDouble(txtWeight.getText().replace(",", "."));

            systemController.registerAssessment(courseCode, type, value, weight);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Avaliação registrada com sucesso!");
            clearFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Entrada", "Por favor, insira valores numéricos válidos.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", e.getMessage());
        }
    }

    private void clearFields() {
        cbCourseCode.getSelectionModel().clearSelection();
        cbAssessmentType.getSelectionModel().clearSelection();
        txtValue.clear();
        txtWeight.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}