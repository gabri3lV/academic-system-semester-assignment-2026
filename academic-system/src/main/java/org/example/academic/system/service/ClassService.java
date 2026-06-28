package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.model.AcademicClass;

public class ClassService {

    private AcademicSystem academicSystem;

    public ClassService(AcademicSystem academicSystem) {
        this.academicSystem = academicSystem;
    }

    public void registerClass(String code, String title) {
        if (code == null || code.isBlank()) {
            throw new AcademicSystemException("Class code cannot be blank.");
        }
        if (title == null || title.isBlank()) {
            throw new AcademicSystemException("Class title cannot be blank.");
        }
        academicSystem.addClass(new AcademicClass(code, title));
    }

    public void listClasses() {
        if (academicSystem.getClasses().isEmpty()) {
            System.out.println("No classes registered.");
            return;
        }
        System.out.println("\n--- Registered Classes ---");
        for (AcademicClass c : academicSystem.getClasses()) {
            System.out.println(c.getCode() + " | " + c.getTitle()
                    + " | Assessments: " + c.getAssessments().size());
        }
    }
}