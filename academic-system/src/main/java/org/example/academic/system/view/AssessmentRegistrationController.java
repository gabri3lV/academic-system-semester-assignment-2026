package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.academic.system.controller.AcademicSystemController;

public class AssessmentRegistrationController {

    @FXML private ComboBox<String> cbCourseCode;
    @FXML private ComboBox<String> cbAssessmentType;
    @FXML private TextField txtValue;
    @FXML private TextField txtWeight;

    private final AcademicSystemController systemController = new AcademicSystemController();

    @FXML
    public void initialize() {
        // Mock de turmas (No futuro, você buscará isso do backend/repositório)
        cbCourseCode.getItems().addAll("CC001", "CC002", "CC003");

        // Tipos de avaliação definidos no domínio
        cbAssessmentType.getItems().addAll("Exam", "PracticalAssignment", "Seminar", "Assignment");
    }

    @FXML
    public void handleSaveAssessment() {
        String courseCode = cbCourseCode.getValue();
        String type = cbAssessmentType.getValue();

        try {
            // Tenta converter os textos em números (Double)
            double value = Double.parseDouble(txtValue.getText().replace(",", "."));
            double weight = Double.parseDouble(txtWeight.getText().replace(",", "."));

            // Envia para o backend processar
            boolean success = systemController.registerAssessment(courseCode, type, value, weight);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Avaliação registrada com sucesso!");
                clearFields();
            }
        } catch (NumberFormatException e) {
            // Captura o erro se o usuário digitar letras no lugar de números
            showAlert(Alert.AlertType.ERROR, "Erro de Entrada", "Por favor, insira valores numéricos válidos para Valor e Peso.");
        } catch (Exception e) {
            // Captura as validações de regra de negócio do backend
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