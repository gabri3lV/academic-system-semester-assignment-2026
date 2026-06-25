package org.example.academic.system;

import org.example.academic.system.model.AcademicClass;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AcademicSystem system = new AcademicSystem();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            printMenu();

            String input = scanner.nextLine().trim();
            int option;

            // AC6: input não numérico não derruba a aplicação
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {

                case 1: // Cadastrar turma
                    System.out.print("Enter class code: ");
                    String code = scanner.nextLine().trim();

                    System.out.print("Enter class title: ");
                    String title = scanner.nextLine().trim();

                    AcademicClass newClass = new AcademicClass(code, title);
                    system.addClass(newClass);
                    System.out.println("Class registered successfully!");
                    break;

                case 2: // Cadastrar avaliação
                    System.out.print("Enter class code: ");
                    String classCode = scanner.nextLine().trim();

                    System.out.print("Enter assessment type (exam/assignment/seminar/practicalassignment): ");
                    String type = scanner.nextLine().trim();

                    System.out.print("Enter value: ");
                    double value;
                    try {
                        value = Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid value. Operation cancelled.");
                        break;
                    }

                    System.out.print("Enter weight: ");
                    double weight;
                    try {
                        weight = Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid weight. Operation cancelled.");
                        break;
                    }

                    boolean registered = system.registerAssessment(classCode, type, value, weight);
                    if (registered) {
                        System.out.println("Assessment registered successfully!");
                    } else {
                        System.out.println("Failed. Check class code and assessment type.");
                    }
                    break;

                case 3: // Salvar dados
                    system.save();
                    break;

                case 4: // Listar turmas
                    if (system.getClasses().isEmpty()) {
                        System.out.println("No classes registered.");
                    } else {
                        System.out.println("\n--- Registered Classes ---");
                        for (AcademicClass c : system.getClasses()) {
                            System.out.println(c.getCode() + " | " + c.getTitle()
                                    + " | Assessments: " + c.getAssessments().size());
                        }
                    }
                    break;

                case 0: // AC8: sair
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;

                default: // AC5: opção inválida sem crash
                    System.out.println("Invalid option. Try again.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== Academic System ===");
        System.out.println("1. Register class");
        System.out.println("2. Register assessment");
        System.out.println("3. Save data");
        System.out.println("4. List classes");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }
}