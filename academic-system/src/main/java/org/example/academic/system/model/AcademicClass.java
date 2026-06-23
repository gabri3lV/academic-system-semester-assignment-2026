package org.example.academic.system.model;

import java.util.ArrayList;
import java.util.List;

public class AcademicClass {

    private String code;
    private String title;
    private List<Assessment> assessments;

    public AcademicClass(String code, String title) {
        this.code = code;
        this.title = title;
        this.assessments = new ArrayList<>();
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }
}
