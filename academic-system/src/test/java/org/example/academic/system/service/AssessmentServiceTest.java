package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.model.AcademicClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssessmentServiceTest {

    private AssessmentService assessmentService;
    private AcademicSystem system;

    @BeforeEach
    void setUp() {
        AcademicSystem.resetInstance();
        system = AcademicSystem.getInstance();
        assessmentService = new AssessmentService(system);

        system.addClass(new AcademicClass("POO01", "Orientacao a Objetos"));
    }

    @Test
    void shouldRegisterAssessmentInExistingClass() {
        assessmentService.registerAssessment("POO01", "exam", 10.0, 1.0);
        assertEquals(1, system.findClassByCode("POO01").getAssessments().size());
    }

    @Test
    void invalidTypeShouldThrowAcademicSystemException() {
        assertThrows(AcademicSystemException.class, () ->
                assessmentService.registerAssessment("POO01", "tipoerrado", 10.0, 1.0)
        );
    }

    @Test
    void nonexistentClassCodeShouldThrowAcademicSystemException() {
        assertThrows(AcademicSystemException.class, () ->
                assessmentService.registerAssessment("NAOEXI", "exam", 10.0, 1.0)
        );
    }
}