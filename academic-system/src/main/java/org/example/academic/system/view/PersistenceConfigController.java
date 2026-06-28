package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.controller.ControllerFactory;

public class PersistenceConfigController {

    @FXML private ToggleGroup persistenceGroup;

    private final AcademicSystemController systemController =
            ControllerFactory.getAcademicSystemController();

    @FXML
    public void handleSaveConfig() {
        RadioButton selectedRadioButton = (RadioButton) persistenceGroup.getSelectedToggle();

        if (selectedRadioButton != null) {
            String selectedType = selectedRadioButton.getUserData().toString();
            try {
                systemController.configurePersistence(selectedType);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso",
                        "Configuração alterada para " + selectedType + "!");
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