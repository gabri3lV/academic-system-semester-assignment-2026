package org.example.academic.system;

import org.example.academic.system.model.*;
import org.example.academic.system.repository.AcademicSystemRepository;
import org.example.academic.system.repository.TxtAcademicSystemRepository;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.ClassService;
import org.example.academic.system.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class AcademicSystem {

    private List<AcademicClass> classes;
    private AcademicSystemRepository repository;
    private ClassService classService;
    private AssessmentService assessmentService;
    private ReportService reportService;

    public AcademicSystem() {
        classes = new ArrayList<>();
        classService = new ClassService(classes);
        assessmentService = new AssessmentService(classService);
        reportService = new ReportService(classService);
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
        return assessmentService.registerAssessment(classCode, type, value, weight);
    }

    public String generateClassSummaryReport() {
        return reportService.generateClassSummaryReport();
    }
}
