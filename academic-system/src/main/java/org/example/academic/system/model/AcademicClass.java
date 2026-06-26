package org.example.academic.system.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AcademicClass that)) {
            return false;
        }
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
