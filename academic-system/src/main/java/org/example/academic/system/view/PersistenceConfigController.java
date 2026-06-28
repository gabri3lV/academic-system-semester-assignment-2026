package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.example.academic.system.controller.AcademicSystemController;

public class PersistenceConfigController {

    @FXML private ToggleGroup persistenceGroup;

    private final AcademicSystemController systemController = new AcademicSystemController();

    @FXML
    public void handleSaveConfig() {
        // Pega o RadioButton que está marcado atualmente
        RadioButton selectedRadioButton = (RadioButton) persistenceGroup.getSelectedToggle();

        if (selectedRadioButton != null) {
            // Extrai a String ("TXT", "XML" ou "JSON") que definimos no userData do FXML
            String selectedType = selectedRadioButton.getUserData().toString();

            try {
                boolean success = systemController.configurePersistence(selectedType);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Configuração de persistência alterada para " + selectedType + "!");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Atenção", "Selecione uma opção antes de aplicar.");
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