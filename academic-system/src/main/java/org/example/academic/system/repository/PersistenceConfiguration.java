package org.example.academic.system.repository;

public class PersistenceConfiguration {

    private static PersistenceType currentType = PersistenceType.TXT;

    public static PersistenceType getCurrentType() {
        return currentType;
    }

    public static void setCurrentType(PersistenceType type) {
        currentType = type;
    }
}