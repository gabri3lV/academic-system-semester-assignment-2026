package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Exam;
import org.example.academic.system.model.PracticalAssignment;
import org.example.academic.system.repository.PersistenceConfiguration;
import org.example.academic.system.repository.PersistenceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {

    private ReportService reportService;
    private AcademicSystem system;

    @BeforeEach
    void setUp() {
        // Reseta o singleton entre testes
        AcademicSystem.resetInstance();
        system = AcademicSystem.getInstance();
        reportService = new ReportService(system);
    }

    @Test
    void classSummaryReportShouldIncludeClassInfo() {
        AcademicClass poo = new AcademicClass("POO01", "Orientacao a Objetos");
        poo.addAssessment(new Exam(10.0, 0.6));
        system.addClass(poo);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        reportService.generateClassSummaryReport();

        String output = out.toString();
        assertTrue(output.contains("POO01"));
        assertTrue(output.contains("Orientacao a Objetos"));
        assertTrue(output.contains("Exam"));
        assertTrue(output.contains("10.0"));
        assertTrue(output.contains("0.6"));

        System.setOut(System.out);
    }

    @Test
    void weightReportShouldCalculateTotalCorrectly() {
        AcademicClass poo = new AcademicClass("POO01", "Orientacao a Objetos");
        poo.addAssessment(new Exam(10.0, 0.6));
        poo.addAssessment(new PracticalAssignment(10.0, 0.4));
        system.addClass(poo);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        reportService.generateAssessmentWeightReport();

        String output = out.toString();
        assertTrue(output.contains("VALID"));

        System.setOut(System.out);
    }

    @Test
    void weightReportShouldMarkInvalidWhenTotalIsNot1() {
        AcademicClass poo = new AcademicClass("POO01", "Orientacao a Objetos");
        poo.addAssessment(new Exam(10.0, 0.5)); // só 0.5, inválido
        system.addClass(poo);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        reportService.generateAssessmentWeightReport();

        assertTrue(out.toString().contains("INVALID"));

        System.setOut(System.out);
    }

    @Test
    void persistenceConfigReportShouldShowCurrentType() {
        PersistenceConfiguration.setCurrentType(PersistenceType.JSON);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        reportService.generatePersistenceConfigurationReport();

        assertTrue(out.toString().contains("JSON"));

        System.setOut(System.out);
    }
}