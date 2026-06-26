package org.example.academic.system;

import org.example.academic.system.model.*;
import org.example.academic.system.repository.AcademicSystemRepository;
import org.example.academic.system.repository.TxtAcademicSystemRepository;
import org.example.academic.system.service.ClassService;

import java.util.ArrayList;
import java.util.List;

public class AcademicSystem {

    private List<AcademicClass> classes;
    private AcademicSystemRepository repository;
    private ClassService classService;

    public AcademicSystem() {
        classes = new ArrayList<>();
        classService = new ClassService(classes);
        repository = new TxtAcademicSystemRepository(); // padrão: TXT
    }

    public void addClass(AcademicClass academicClass) {
        classService.registerClass(academicClass);
    }

    public List<AcademicClass> getClasses() {
        return classService.getClasses();
    }

    public void save() {
        repository.save(classes);
    }

    public AcademicClass findClassByCode(String code) {
        return classService.findClassByCode(code);
    }

    public boolean registerAssessment(String classCode, String type,
                                      double value, double weight) {
        AcademicClass academicClass = findClassByCode(classCode);
        if (academicClass == null) return false;

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
