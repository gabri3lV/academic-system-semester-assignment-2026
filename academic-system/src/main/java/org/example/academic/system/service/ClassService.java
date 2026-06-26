package org.example.academic.system.service;

import org.example.academic.system.exception.InvalidAcademicClassException;
import org.example.academic.system.model.AcademicClass;

import java.util.List;

public class ClassService {

    private final List<AcademicClass> classes;

    public ClassService(List<AcademicClass> classes) {
        this.classes = classes;
    }

    public void registerClass(AcademicClass academicClass) {
        if (academicClass == null
                || academicClass.getCode() == null
                || academicClass.getCode().isBlank()
                || academicClass.getTitle() == null
                || academicClass.getTitle().isBlank()) {
            throw new InvalidAcademicClassException("Class code and title must not be blank.");
        }
        classes.add(academicClass);
    }

    public List<AcademicClass> getClasses() {
        return classes;
    }

    public AcademicClass findClassByCode(String code) {
        for (AcademicClass academicClass : classes) {
            if (academicClass.getCode().equals(code)) {
                return academicClass;
            }
        }
        return null;
    }
}
