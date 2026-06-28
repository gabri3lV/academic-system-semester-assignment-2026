package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.example.academic.system.security.Session;

import java.io.IOException;

public class MainScreenController {

    @FXML private BorderPane mainContainer;

    @FXML private Button btnClassRegistration;
    @FXML private Button btnAssessmentRegistration;
    @FXML private Button btnVisualization;
    @FXML private Button btnReports;
    @FXML private Button btnPersistenceConfig;
    @FXML private Button btnLogout;

    @FXML
    public void initialize() {
        if (Session.getCurrentRole() != null
                && "PROFESSOR".equals(Session.getCurrentRole().name())) {
            btnClassRegistration.setVisible(false);
            btnClassRegistration.setManaged(false);
            btnPersistenceConfig.setVisible(false);
            btnPersistenceConfig.setManaged(false);
        }
    }

    @FXML
    public void showClassRegistration() {
        System.out.println("Navegando para Registro de Turmas...");
        loadView("ClassRegistrationScreen.fxml"); // Injeta a tela no centro!
    }

    @FXML
    public void showAssessmentRegistration() {
        System.out.println("Navegando para Registro de Avaliações...");
        loadView("AssessmentRegistrationScreen.fxml");
    }

    @FXML
    public void showVisualization() {
        System.out.println("Navegando para Visualização...");
        loadView("VisualizationScreen.fxml");
    }

    @FXML
    public void showReports() {
        System.out.println("Navegando para Relatórios...");
        loadView("ReportScreen.fxml");
    }

    @FXML
    public void showPersistenceConfig() {
        System.out.println("Navegando para Configuração de Persistência...");
        loadView("PersistenceConfigScreen.fxml");
    }

    @FXML
    public void handleLogout() {
        Session.logout();
        // lógica para voltar para a tela de login
    }

    // Motor de injeção de telas no centro
    private void loadView(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/academic/system/view/" + fxmlFileName));
            Parent view = loader.load();
            mainContainer.setCenter(view);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + fxmlFileName);
            e.printStackTrace();
        }
    }
}