package org.example.academic.system.controller;

import org.example.academic.system.AcademicSystem;
import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.repository.PersistenceConfiguration;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.security.Session;
import org.example.academic.system.service.*;

import java.util.Scanner;

public class AcademicSystemController {

    private ClassService classService;
    private AssessmentService assessmentService;
    private PersistenceService persistenceService;
    private ReportService reportService;

    public AcademicSystemController(ClassService classService,
                                    AssessmentService assessmentService,
                                    PersistenceService persistenceService,
                                    ReportService reportService) {
        this.classService = classService;
        this.assessmentService = assessmentService;
        this.persistenceService = persistenceService;
        this.reportService = reportService;
    }

    public AcademicSystemController() {
        AcademicSystem system = AcademicSystem.getInstance();
        this.classService = new ClassService(system);
        this.assessmentService = new AssessmentService(system);
        this.persistenceService = new PersistenceService(system);
        this.reportService = new ReportService(system);
    }

    public void start(Scanner scanner) {
        boolean running = true;
        while (running) {
            try {
                if (Session.isAdmin()) {
                    printAdminMenu();
                    running = handleAdminOption(scanner.nextLine().trim(), scanner);
                } else {
                    printProfessorMenu();
                    running = handleProfessorOption(scanner.nextLine().trim(), scanner);
                }
            } catch (AcademicSystemException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (AuthorizationException e) {
                System.out.println("Access denied: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
            System.out.println();
        }
    }

    // ---- Menus ----

    private void printAdminMenu() {
        System.out.println("\n===== ACADEMIC SYSTEM [ADMIN] =====");
        System.out.println("1 - Register class");
        System.out.println("2 - Register assessment");
        System.out.println("3 - List classes");
        System.out.println("4 - Class assessment summary report");
        System.out.println("5 - Assessment weight report");
        System.out.println("6 - Configure persistence type");
        System.out.println("7 - Save academic data");
        System.out.println("8 - Persistence configuration report");
        System.out.println("9 - Logout");
        System.out.println("0 - Exit");
        System.out.print("Choose: ");
    }

    private void printProfessorMenu() {
        System.out.println("\n===== ACADEMIC SYSTEM [PROFESSOR] =====");
        System.out.println("1 - Register assessment");
        System.out.println("2 - List classes");
        System.out.println("3 - Class assessment summary report");
        System.out.println("4 - Assessment weight report");
        System.out.println("5 - Logout");
        System.out.println("0 - Exit");
        System.out.print("Choose: ");
    }

    // ---- Handlers ----

    private boolean handleAdminOption(String option, Scanner scanner) {
        switch (option) {
            case "1": registerClass(scanner); break;
            case "2": registerAssessment(scanner); break;
            case "3": classService.listClasses(); break;
            case "4": reportService.generateClassSummaryReport(); break;
            case "5": reportService.generateAssessmentWeightReport(); break;
            case "6": configurePersistence(scanner); break;
            case "7": persistenceService.save(); break;
            case "8": reportService.generatePersistenceConfigurationReport(); break;
            case "9":
                Session.logout();
                System.out.println("Logged out successfully.");
                return false;
            case "0":
                System.out.println("Exiting. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option.");
        }
        return true;
    }

    private boolean handleProfessorOption(String option, Scanner scanner) {
        switch (option) {
            case "1": registerAssessment(scanner); break;
            case "2": classService.listClasses(); break;
            case "3": reportService.generateClassSummaryReport(); break;
            case "4": reportService.generateAssessmentWeightReport(); break;
            case "5":
                Session.logout();
                System.out.println("Logged out successfully.");
                return false;
            case "0":
                System.out.println("Exiting. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option.");
        }
        return true;
    }

    // ---- Operations ----

    private void registerClass(Scanner scanner) {
        if (!Session.isAdmin()) {
            throw new AuthorizationException("Only administrators can register classes.");
        }
        System.out.print("Class code: ");
        String code = scanner.nextLine().trim();
        System.out.print("Class title: ");
        String title = scanner.nextLine().trim();
        classService.registerClass(code, title);
        System.out.println("Class registered successfully!");
    }

    private void registerAssessment(Scanner scanner) {
        System.out.print("Class code: ");
        String classCode = scanner.nextLine().trim();
        System.out.print("Assessment type (exam/assignment/seminar/practicalassignment): ");
        String type = scanner.nextLine().trim();
        System.out.print("Value: ");
        double value = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Weight: ");
        double weight = Double.parseDouble(scanner.nextLine().trim());
        assessmentService.registerAssessment(classCode, type, value, weight);
        System.out.println("Assessment registered successfully!");
    }

    private void configurePersistence(Scanner scanner) {
        if (!Session.isAdmin()) {
            throw new AuthorizationException("Only administrators can configure persistence.");
        }
        System.out.println("1 - TXT");
        System.out.println("2 - XML");
        System.out.println("3 - JSON");
        System.out.print("Choose: ");
        switch (scanner.nextLine().trim()) {
            case "1": persistenceService.setPersistenceType(PersistenceType.TXT); break;
            case "2": persistenceService.setPersistenceType(PersistenceType.XML); break;
            case "3": persistenceService.setPersistenceType(PersistenceType.JSON); break;
            default: System.out.println("Invalid persistence type.");
        }
    }

    // Métodos para a camada JavaFX (retornam dados em vez de imprimir)

    public boolean registerClass(String code, String title) {
        try {
            classService.registerClass(code, title);
            return true;
        } catch (AcademicSystemException e) {
            throw e; // repassa para o controller JavaFX tratar
        }
    }

    public boolean registerAssessment(String classCode, String type,
                                      double value, double weight) {
        try {
            assessmentService.registerAssessment(classCode, type, value, weight);
            return true;
        } catch (AcademicSystemException e) {
            throw e;
        }
    }

    public boolean configurePersistence(String type) {
        switch (type.toUpperCase()) {
            case "TXT":  persistenceService.setPersistenceType(PersistenceType.TXT); break;
            case "XML":  persistenceService.setPersistenceType(PersistenceType.XML); break;
            case "JSON": persistenceService.setPersistenceType(PersistenceType.JSON); break;
            default: return false;
        }
        return true;
    }

    public String generateClassAssessmentSummaryReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== CLASS ASSESSMENT SUMMARY REPORT =====\n");
        if (academicSystem().getClasses().isEmpty()) {
            sb.append("No classes registered.");
            return sb.toString();
        }
        for (var c : academicSystem().getClasses()) {
            sb.append("\nClass: ").append(c.getCode()).append(" | ").append(c.getTitle()).append("\n");
            if (c.getAssessments().isEmpty()) {
                sb.append("  No assessments registered.\n");
            } else {
                for (var a : c.getAssessments()) {
                    sb.append("  - ").append(a.getClass().getSimpleName())
                            .append(" | value=").append(a.getValue())
                            .append(" | weight=").append(a.getWeight()).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public String generateAssessmentWeightReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== ASSESSMENT WEIGHT REPORT =====\n");
        for (var c : academicSystem().getClasses()) {
            double total = c.getAssessments().stream()
                    .mapToDouble(a -> a.getWeight()).sum();
            String status = Math.abs(total - 1.0) < 0.001 ? "VALID ✓" : "INVALID ✗";
            sb.append("Class: ").append(c.getCode()).append(" | ")
                    .append(c.getTitle()).append(" | Total weight: ")
                    .append(total).append(" | ").append(status).append("\n");
        }
        return sb.toString();
    }

    public String generatePersistenceConfigurationReport() {
        return "===== PERSISTENCE CONFIGURATION REPORT =====\n"
                + "Current persistence type: "
                + PersistenceConfiguration.getCurrentType();
    }

    private AcademicSystem academicSystem() {
        return AcademicSystem.getInstance();
    }
}