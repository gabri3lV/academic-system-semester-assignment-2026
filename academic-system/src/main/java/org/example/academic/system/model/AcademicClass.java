package org.example.academic.system.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class AcademicClass {

    @NotBlank(message = "Class code cannot be blank.")
    private String code;

    @NotBlank(message = "Class title cannot be blank.")
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AcademicClass that = (AcademicClass) obj;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}