package org.example.academic.system.controller;

import org.example.academic.system.AcademicSystem;

public class AcademicSystemController {

    private final AcademicSystem system;

    public AcademicSystemController(AcademicSystem system) {
        this.system = system;
    }

    public void registerClass(String code, String title) {
        system.registerClass(code, title);
    }

    public boolean registerAssessment(String classCode, String type, double value, double weight) {
        return system.registerAssessment(classCode, type, value, weight);
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
