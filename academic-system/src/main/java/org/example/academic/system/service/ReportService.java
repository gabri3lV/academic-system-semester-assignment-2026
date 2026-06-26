package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;

import java.util.List;

public class ReportService {

    private final ClassService classService;

    public ReportService(ClassService classService) {
        this.classService = classService;
    }

    public String generateClassSummaryReport() {
        List<AcademicClass> classes = classService.getClasses();
        if (classes.isEmpty()) {
            return "No classes registered.";
        }

        StringBuilder report = new StringBuilder("\n--- Registered Classes ---");
        for (AcademicClass academicClass : classes) {
            report.append(System.lineSeparator())
                    .append(academicClass.getCode())
                    .append(" | ")
                    .append(academicClass.getTitle())
                    .append(" | Assessments: ")
                    .append(academicClass.getAssessments().size());
        }
        return report.toString();
    }
}
