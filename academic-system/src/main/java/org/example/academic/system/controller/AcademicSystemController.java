package org.example.academic.system.controller;

import org.example.academic.system.AcademicSystem;

public class AcademicSystemController {

    private final AcademicSystem system;

    public AcademicSystemController(AcademicSystem system) {
        this.system = system;
    }

    public String registerClass(String code, String title) {
        system.registerClass(code, title);
        return "Class registered successfully!";
    }

    public String registerAssessment(String classCode, String type, double value, double weight) {
        boolean registered = system.registerAssessment(classCode, type, value, weight);
        if (registered) {
            return "Assessment registered successfully!";
        }
        return "Failed. Check class code and assessment type.";
    }

    public void saveData() {
        system.save();
    }

    public boolean classExists(String code) {
        return system.findClassByCode(code) != null;
    }

    public String generateClassSummaryReport() {
        return system.generateClassSummaryReport();
    }
}
