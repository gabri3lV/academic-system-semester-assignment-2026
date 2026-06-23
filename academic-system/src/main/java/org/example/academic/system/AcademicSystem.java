package org.example.academic.system;

import org.example.academic.system.model.*;

import java.util.ArrayList;
import java.util.List;

public class AcademicSystem {

    private List<AcademicClass> classes;

    public AcademicSystem() {
        classes = new ArrayList<>();
    }

    public void addClass(AcademicClass academicClass) {
        classes.add(academicClass);
    }

    public AcademicClass findClassByCode(String code) {

        for (AcademicClass academicClass : classes) {
            if (academicClass.getCode().equals(code)) {
                return academicClass;
            }
        }

        return null;
    }

    public boolean registerAssessment(
            String classCode,
            String type,
            double value,
            double weight) {

        AcademicClass academicClass =
                findClassByCode(classCode);

        if (academicClass == null) {
            return false;
        }

        Assessment assessment;

        switch (type.toLowerCase()) {

            case "exam":
                assessment = new Exam(value, weight);
                break;

            case "assignment":
                assessment = new Assignment(value, weight);
                break;

            case "seminar":
                assessment = new Seminar(value, weight);
                break;

            case "practicalassignment":
                assessment = new PracticalAssignment(value, weight);
                break;

            default:
                return false;
        }

        academicClass.addAssessment(assessment);

        return true;
    }
}