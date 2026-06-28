package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.model.*;

public class AssessmentService {

    private AcademicSystem academicSystem;

    public AssessmentService(AcademicSystem academicSystem) {
        this.academicSystem = academicSystem;
    }

    public void registerAssessment(String classCode, String type,
                                   double value, double weight) {
        AcademicClass academicClass = academicSystem.findClassByCode(classCode);
        if (academicClass == null) {
            throw new AcademicSystemException("Class not found: " + classCode);
        }

        Assessment assessment;
        switch (type.toLowerCase()) {
            case "exam":               assessment = new Exam(value, weight); break;
            case "assignment":         assessment = new Assignment(value, weight); break;
            case "seminar":            assessment = new Seminar(value, weight); break;
            case "practicalassignment": assessment = new PracticalAssignment(value, weight); break;
            default: throw new AcademicSystemException("Invalid assessment type: " + type);
        }

        academicClass.addAssessment(assessment);
    }
}