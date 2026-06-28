package org.example.academic.system.service;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceService {

    private static final Logger logger =
            LoggerFactory.getLogger(PersistenceService.class);

    private AcademicSystem academicSystem;

    public PersistenceService(AcademicSystem academicSystem) {
        this.academicSystem = academicSystem;
    }

    public void save() {
        PersistenceType type = PersistenceConfiguration.getCurrentType();
        logger.info("Saving academic data using persistence type: {}", type);
        getRepository().save(academicSystem.getClasses());
        logger.info("Academic data saved successfully using: {}", type);
    }


    public void setPersistenceType(PersistenceType type) {
        PersistenceConfiguration.setCurrentType(type);
        logger.info("Persistence type configured to: {}", type);
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