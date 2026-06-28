package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XmlAcademicSystemRepository implements AcademicSystemRepository {

    private static final String FILE_NAME = "academic-data.xml";

    @Override
    public void save(List<AcademicClass> classes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            writer.write("<academicData>\n");

            for (AcademicClass academicClass : classes) {
                writer.write("  <class>\n");
                writer.write("    <code>" + academicClass.getCode() + "</code>\n");
                writer.write("    <title>" + academicClass.getTitle() + "</title>\n");
                writer.write("    <assessments>\n");

                for (Assessment assessment : academicClass.getAssessments()) {
                    writer.write("      <assessment>\n");
                    writer.write("        <type>" + assessment.getClass().getSimpleName() + "</type>\n");
                    writer.write("        <value>" + assessment.getValue() + "</value>\n");
                    writer.write("        <weight>" + assessment.getWeight() + "</weight>\n");
                    writer.write("      </assessment>\n");
                }

                writer.write("    </assessments>\n");
                writer.write("  </class>\n");
            }

            writer.write("</academicData>\n");
            System.out.println("Academic data saved to " + FILE_NAME);

        } catch (IOException e) {
            System.err.println("Error saving XML: " + e.getMessage());
        }
    }
}