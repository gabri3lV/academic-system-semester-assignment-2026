package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Exam;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    private List<AcademicClass> buildTestData() {
        AcademicClass poo = new AcademicClass("POO01", "Orientacao a Objetos");
        poo.addAssessment(new Exam(10.0, 1.0));
        return List.of(poo);
    }

    @Test
    void txtRepositoryShouldGenerateFile() {
        TxtAcademicSystemRepository repo = new TxtAcademicSystemRepository();
        repo.save(buildTestData());
        assertTrue(new File("academic-data.txt").exists());
    }

    @Test
    void xmlRepositoryShouldGenerateFile() {
        XmlAcademicSystemRepository repo = new XmlAcademicSystemRepository();
        repo.save(buildTestData());
        assertTrue(new File("academic-data.xml").exists());
    }

    @Test
    void jsonRepositoryShouldGenerateFile() {
        JsonAcademicSystemRepository repo = new JsonAcademicSystemRepository();
        repo.save(buildTestData());
        assertTrue(new File("academic-data.json").exists());
    }
}