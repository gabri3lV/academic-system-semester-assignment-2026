package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Exam;
import org.example.academic.system.repository.PersistenceConfiguration;
import org.example.academic.system.repository.PersistenceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceServiceTest {

    private PersistenceService persistenceService;

    @BeforeEach
    void setUp() {
        AcademicSystem.resetInstance();
        AcademicSystem system = AcademicSystem.getInstance();

        AcademicClass poo = new AcademicClass("POO01", "Orientacao a Objetos");
        poo.addAssessment(new Exam(10.0, 1.0));
        system.addClass(poo);

        persistenceService = new PersistenceService(system);
        PersistenceConfiguration.setCurrentType(PersistenceType.TXT);
    }

    @Test
    void shouldSaveWithDefaultTxtRepository() {
        persistenceService.save();
        assertTrue(new File("academic-data.txt").exists());
    }

    @Test
    void shouldChangePersistenceTypeToXml() {
        persistenceService.setPersistenceType(PersistenceType.XML);
        assertEquals(PersistenceType.XML, persistenceService.getCurrentType());
        persistenceService.save();
        assertTrue(new File("academic-data.xml").exists());
    }

    @Test
    void shouldChangePersistenceTypeToJson() {
        persistenceService.setPersistenceType(PersistenceType.JSON);
        assertEquals(PersistenceType.JSON, persistenceService.getCurrentType());
        persistenceService.save();
        assertTrue(new File("academic-data.json").exists());
    }
}