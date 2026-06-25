package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TxtAcademicSystemRepository implements AcademicSystemRepository {

    private static final String FILE_NAME = "academic-data.txt";

    @Override
    public void save(List<AcademicClass> classes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (AcademicClass academicClass : classes) {
                writer.write("CLASS: " + academicClass.getCode()
                        + " | " + academicClass.getTitle());
                writer.newLine();

                for (Assessment assessment : academicClass.getAssessments()) {
                    writer.write("  ASSESSMENT: "
                            + assessment.getClass().getSimpleName()
                            + " | value=" + assessment.getValue()
                            + " | weight=" + assessment.getWeight());
                    writer.newLine();
                }
            }

            System.out.println("Academic data saved to " + FILE_NAME);

        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}