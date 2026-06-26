package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.repository.AcademicSystemRepository;
import org.example.academic.system.repository.TxtAcademicSystemRepository;

import java.util.List;

public class PersistenceService {

    private final AcademicSystemRepository repository;

    public PersistenceService() {
        repository = new TxtAcademicSystemRepository();
    }

    public void save(List<AcademicClass> classes) {
        repository.save(classes);
    }
}
