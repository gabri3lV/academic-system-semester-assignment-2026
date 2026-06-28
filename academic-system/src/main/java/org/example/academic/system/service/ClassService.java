package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.validation.DomainValidator;

public class ClassService {

    private AcademicSystem academicSystem;

    public ClassService(AcademicSystem academicSystem) {
        this.academicSystem = academicSystem;
    }

    public void registerClass(String code, String title) {
        AcademicClass academicClass = new AcademicClass(code, title);
        DomainValidator.validate(academicClass);
        academicSystem.addClass(academicClass);
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