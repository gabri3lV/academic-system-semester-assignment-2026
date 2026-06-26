package org.example.academic.system;

import org.example.academic.system.model.*;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.ClassService;
import org.example.academic.system.service.PersistenceService;
import org.example.academic.system.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class AcademicSystem {

    private List<AcademicClass> classes;
    private ClassService classService;
    private AssessmentService assessmentService;
    private PersistenceService persistenceService;
    private ReportService reportService;

    public AcademicSystem() {
        classes = new ArrayList<>();
        classService = new ClassService(classes);
        assessmentService = new AssessmentService(classService);
        persistenceService = new PersistenceService();
        reportService = new ReportService(classService);
    }

    public void addClass(AcademicClass academicClass) {
        classService.registerClass(academicClass);
    }

    public void registerClass(String code, String title) {
        addClass(new AcademicClass(code, title));
    }

    public List<AcademicClass> getClasses() {
        return classService.getClasses();
    }

    public void save() {
        persistenceService.save(classes);
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
