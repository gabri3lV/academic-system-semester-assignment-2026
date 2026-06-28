package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.repository.PersistenceConfiguration;

public class ReportService {

    private AcademicSystem academicSystem;

    public ReportService(AcademicSystem academicSystem) {
        this.academicSystem = academicSystem;
    }

    public void generateClassSummaryReport() {
        System.out.println("\n===== CLASS ASSESSMENT SUMMARY REPORT =====");
        if (academicSystem.getClasses().isEmpty()) {
            System.out.println("No classes registered.");
            return;
        }
        for (AcademicClass c : academicSystem.getClasses()) {
            System.out.println("\nClass: " + c.getCode() + " | " + c.getTitle());
            if (c.getAssessments().isEmpty()) {
                System.out.println("  No assessments registered.");
            } else {
                for (Assessment a : c.getAssessments()) {
                    System.out.println("  - " + a.getClass().getSimpleName()
                            + " | value=" + a.getValue()
                            + " | weight=" + a.getWeight());
                }
            }
        }
    }

    public void generateAssessmentWeightReport() {
        System.out.println("\n===== ASSESSMENT WEIGHT REPORT =====");
        if (academicSystem.getClasses().isEmpty()) {
            System.out.println("No classes registered.");
            return;
        }
        for (AcademicClass c : academicSystem.getClasses()) {
            double total = c.getAssessments().stream()
                    .mapToDouble(Assessment::getWeight).sum();
            String status = Math.abs(total - 1.0) < 0.001 ? "VALID ✓" : "INVALID ✗";
            System.out.println("Class: " + c.getCode() + " | " + c.getTitle()
                    + " | Total weight: " + total + " | " + status);
        }
    }

    public void generatePersistenceConfigurationReport() {
        System.out.println("\n===== PERSISTENCE CONFIGURATION REPORT =====");
        System.out.println("Current persistence type: "
                + PersistenceConfiguration.getCurrentType());
    }
}