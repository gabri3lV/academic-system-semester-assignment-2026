package org.example.academic.system;

import org.example.academic.system.model.*;

import java.util.ArrayList;
import java.util.List;

public class AcademicSystem {

    private static AcademicSystem instance;
    private List<AcademicClass> classes;

    private AcademicSystem() {
        classes = new ArrayList<>();
    }

    public static AcademicSystem getInstance() {
        if (instance == null) {
            instance = new AcademicSystem();
        }
        return instance;
    }

    public void addClass(AcademicClass academicClass) {
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