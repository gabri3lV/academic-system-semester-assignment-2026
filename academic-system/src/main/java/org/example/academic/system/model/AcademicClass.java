package org.example.academic.system.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
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
}