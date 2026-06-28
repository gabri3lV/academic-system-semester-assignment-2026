package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.exception.AcademicSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClassServiceTest {

    private ClassService classService;
    private AcademicSystem system;

    @BeforeEach
    void setUp() {
        AcademicSystem.resetInstance();
        system = AcademicSystem.getInstance();
        classService = new ClassService(system);
    }

    @Test
    void shouldRegisterValidClass() {
        classService.registerClass("POO01", "Orientacao a Objetos");
        assertNotNull(system.findClassByCode("POO01"));
    }

    @Test
    void registeredClassShouldBeStoredInAcademicSystem() {
        classService.registerClass("POO01", "Orientacao a Objetos");
        assertEquals(1, system.getClasses().size());
    }

    @Test
    void blankCodeShouldThrowAcademicSystemException() {
        assertThrows(AcademicSystemException.class, () ->
                classService.registerClass("", "Orientacao a Objetos")
        );
    }

    @Test
    void blankTitleShouldThrowAcademicSystemException() {
        assertThrows(AcademicSystemException.class, () ->
                classService.registerClass("POO01", "")
        );
    }
}