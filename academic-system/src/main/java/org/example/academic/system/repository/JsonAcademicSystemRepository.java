package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonAcademicSystemRepository implements AcademicSystemRepository {

    private static final String FILE_NAME = "academic-data.json";

    @Override
    public void save(List<AcademicClass> classes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            writer.write("{\n  \"classes\": [\n");

            for (int i = 0; i < classes.size(); i++) {
                AcademicClass academicClass = classes.get(i);
                writer.write("    {\n");
                writer.write("      \"code\": \"" + academicClass.getCode() + "\",\n");
                writer.write("      \"title\": \"" + academicClass.getTitle() + "\",\n");
                writer.write("      \"assessments\": [\n");

                List<Assessment> assessments = academicClass.getAssessments();
                for (int j = 0; j < assessments.size(); j++) {
                    Assessment a = assessments.get(j);
                    writer.write("        {\n");
                    writer.write("          \"type\": \"" + a.getClass().getSimpleName() + "\",\n");
                    writer.write("          \"value\": " + a.getValue() + ",\n");
                    writer.write("          \"weight\": " + a.getWeight() + "\n");
                    writer.write("        }");
                    if (j < assessments.size() - 1) writer.write(",");
                    writer.write("\n");
                }

                writer.write("      ]\n    }");
                if (i < classes.size() - 1) writer.write(",");
                writer.write("\n");
            }

            writer.write("  ]\n}\n");
            System.out.println("Academic data saved to " + FILE_NAME);

        } catch (IOException e) {
            System.err.println("Error saving JSON: " + e.getMessage());
        }
    }
}