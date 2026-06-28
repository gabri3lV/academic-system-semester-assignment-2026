package org.example.academic.system.controller;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.service.*;

public class ControllerFactory {

    private static AcademicSystemController instance;

    public static AcademicSystemController getAcademicSystemController() {
        if (instance == null) {
            AcademicSystem system = AcademicSystem.getInstance();
            instance = new AcademicSystemController(
                    new ClassService(system),
                    new AssessmentService(system),
                    new PersistenceService(system),
                    new ReportService(system)
            );
        }
        return instance;
    }
}