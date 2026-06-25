package org.example.academic.system;

import org.example.academic.system.model.AcademicClass;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AcademicSystem system = new AcademicSystem();
        Scanner scanner = new Scanner(System.in);

        // --- Cadastrar turma via teclado ---
        System.out.print("Enter class code: ");
        String code = scanner.nextLine().trim();

        System.out.print("Enter class title: ");
        String title = scanner.nextLine().trim();

        AcademicClass newClass = new AcademicClass(code, title);
        system.addClass(newClass);

        System.out.println("Class registered successfully!");
        system.save();

        // --- AC6: exibir turmas cadastradas ---
        System.out.println("\n--- Registered Classes ---");
        for (AcademicClass c : system.getClasses()) {
            System.out.println(c.getCode() + " | " + c.getTitle());
        }

        scanner.close();
    }
}