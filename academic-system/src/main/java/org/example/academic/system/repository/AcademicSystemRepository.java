package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import java.util.List;

public interface AcademicSystemRepository {
    void save(List<AcademicClass> classes);
}