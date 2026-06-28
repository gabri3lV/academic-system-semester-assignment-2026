package org.example.academic.system.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.academic.system.AcademicSystem;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;

import java.util.List;

public class VisualizationController {

    @FXML private TableView<AcademicClass> tableClasses;
    @FXML private TableColumn<AcademicClass, String> colClassCode;
    @FXML private TableColumn<AcademicClass, String> colClassTitle;

    @FXML private TableView<AssessmentDTO> tableAssessments;
    @FXML private TableColumn<AssessmentDTO, String> colAssessmentType;
    @FXML private TableColumn<AssessmentDTO, Double> colAssessmentValue;
    @FXML private TableColumn<AssessmentDTO, Double> colAssessmentWeight;

    @FXML
    public void initialize() {
        colClassCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colClassTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        colAssessmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAssessmentValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colAssessmentWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        loadClasses();

        tableClasses.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    if (selected != null) updateAssessmentTable(selected);
                });
    }

    private void loadClasses() {
        List<AcademicClass> classes = AcademicSystem.getInstance().getClasses();
        tableClasses.setItems(FXCollections.observableArrayList(classes));
    }

    private void updateAssessmentTable(AcademicClass selected) {
        ObservableList<AssessmentDTO> dtos = FXCollections.observableArrayList();
        for (Assessment a : selected.getAssessments()) {
            dtos.add(new AssessmentDTO(
                    a.getClass().getSimpleName(),
                    a.getValue(),
                    a.getWeight()
            ));
        }
        tableAssessments.setItems(dtos);
    }

    public static class AssessmentDTO {
        private String type;
        private double value;
        private double weight;

        public AssessmentDTO(String type, double value, double weight) {
            this.type = type;
            this.value = value;
            this.weight = weight;
        }

        public String getType() { return type; }
        public double getValue() { return value; }
        public double getWeight() { return weight; }
    }
}