package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;

import java.util.List;

public class ClassService {

    private final List<AcademicClass> classes;

    public ClassService(List<AcademicClass> classes) {
        this.classes = classes;
    }

    public void registerClass(AcademicClass academicClass) {
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
