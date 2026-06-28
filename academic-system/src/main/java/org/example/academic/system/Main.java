package org.example.academic.system;

import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.security.AuthenticationService;
import org.example.academic.system.security.Session;
import org.example.academic.system.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AcademicSystem system = AcademicSystem.getInstance();
        AuthenticationService authService = new AuthenticationService();
        Scanner scanner = new Scanner(System.in);

        ClassService classService = new ClassService(system);
        AssessmentService assessmentService = new AssessmentService(system);
        PersistenceService persistenceService = new PersistenceService(system);
        ReportService reportService = new ReportService(system);

        AcademicSystemController controller = new AcademicSystemController(
                classService, assessmentService, persistenceService, reportService
        );

        while (true) {
            System.out.println("\n===== ACADEMIC SYSTEM LOGIN =====");
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            try {
                authService.authenticate(username, password);
                System.out.println("Welcome, " + Session.getCurrentUser().getUsername() + "!");
                controller.start(scanner);
                // se chegar aqui, o usuário fez logout — volta ao login
            } catch (AuthenticationException e) {
                System.out.println("Login failed: " + e.getMessage());
            }
        }
    }
}