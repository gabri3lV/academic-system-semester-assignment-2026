package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.repository.*;

public class PersistenceService {

    private AcademicSystem academicSystem;

    public PersistenceService(AcademicSystem academicSystem) {
        this.academicSystem = academicSystem;
    }

    public void save() {
        getRepository().save(academicSystem.getClasses());
    }

    public void setPersistenceType(PersistenceType type) {
        PersistenceConfiguration.setCurrentType(type);
        System.out.println("Persistence configured as " + type);
    }

    public PersistenceType getCurrentType() {
        return PersistenceConfiguration.getCurrentType();
    }

    private AcademicSystemRepository getRepository() {
        switch (PersistenceConfiguration.getCurrentType()) {
            case XML:  return new XmlAcademicSystemRepository();
            case JSON: return new JsonAcademicSystemRepository();
            default:   return new TxtAcademicSystemRepository();
        }
    }
}