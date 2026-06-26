package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.model.Assignment;
import org.example.academic.system.model.Exam;
import org.example.academic.system.model.PracticalAssignment;
import org.example.academic.system.model.Seminar;

public class AssessmentService {

    private final ClassService classService;

    public AssessmentService(ClassService classService) {
        this.classService = classService;
    }

    public boolean registerAssessment(String classCode, String type, double value, double weight) {
        AcademicClass academicClass = classService.findClassByCode(classCode);
        if (academicClass == null) {
            return false;
        }

        Assessment assessment = createAssessment(type, value, weight);
        if (assessment == null) {
            return false;
        }

        academicClass.addAssessment(assessment);
        return true;
    }

    private Assessment createAssessment(String type, double value, double weight) {
        if (type == null) {
            return null;
        }

        switch (type.toLowerCase()) {
            case "exam":
                return new Exam(value, weight);
            case "assignment":
                return new Assignment(value, weight);
            case "seminar":
                return new Seminar(value, weight);
            case "practicalassignment":
                return new PracticalAssignment(value, weight);
            default:
                return null;
        }
    }
}
