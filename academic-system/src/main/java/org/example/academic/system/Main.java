package org.example.academic.system;

import org.example.academic.system.model.AcademicClass;

public class Main {

    public static void main(String[] args) {

        AcademicSystem system = new AcademicSystem();

        AcademicClass poo =
                new AcademicClass(
                        "POO01",
                        "Orientacao a Objetos"
                );

        system.addClass(poo);

        boolean registered =
                system.registerAssessment(
                        "POO01",
                        "exam",
                        10,
                        0.4
                );

        System.out.println(registered);

        System.out.println(
                poo.getAssessments().size()
        );
    }
}