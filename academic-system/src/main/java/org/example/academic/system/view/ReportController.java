package org.example.academic.system.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.controller.ControllerFactory;
import org.example.academic.system.security.Session;

public class ReportController {

    @FXML private Button btnPersistenceReport;
    @FXML private TextArea txtReportOutput;

    private final AcademicSystemController systemController =
            ControllerFactory.getAcademicSystemController();

    @FXML
    public void initialize() {
        if (Session.getCurrentRole() != null
                && Session.getCurrentRole().name().equals("PROFESSOR")) {
            btnPersistenceReport.setVisible(false);
            btnPersistenceReport.setManaged(false);
        }
    }

    @FXML
    public void handleClassSummaryReport() {
        String report = systemController.generateClassAssessmentSummaryReport();
        txtReportOutput.setText(report);
    }

    @FXML
    public void handleWeightValidationReport() {
        String report = systemController.generateAssessmentWeightReport();
        txtReportOutput.setText(report);
    }

    @FXML
    public void handlePersistenceReport() {
        // Dupla verificação de segurança (Boa prática)
        if ("PROFESSOR".equals(Session.getInstance().getCurrentUserRole())) {
            txtReportOutput.setText("Acesso Negado: Você não tem permissão para visualizar este relatório.");
            return;
        }

        String report = systemController.generatePersistenceConfigurationReport();
        txtReportOutput.setText(report);
    }
}