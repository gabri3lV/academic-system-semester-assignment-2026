from __future__ import annotations

import argparse
import os
import sys

PROJECT_DIR = os.path.dirname(os.path.abspath(__file__))
SRC_DIR = os.path.join(PROJECT_DIR, "src")
if SRC_DIR not in sys.path:
    sys.path.insert(0, SRC_DIR)

from academic_system.controller.authentication_controller import AuthenticationController
from academic_system.controller.controller_factory import ControllerFactory
from academic_system.exception.exceptions import (
    AcademicSystemException,
    AuthenticationException,
    SecurityException,
)
from academic_system.logging_config.logging_setup import setup_logging
from academic_system.security.session import Session


def read_float(label: str) -> float:
    while True:
        try:
            return float(input(label).strip())
        except ValueError:
            print("Invalid number. Try again.")


def register_class(controller):
    code = input("Class code: ").strip()
    title = input("Class title: ").strip()
    controller.register_class(code, title)
    print("Class registered successfully!")


def register_assessment(controller):
    class_code = input("Class code: ").strip()
    assessment_type = input(
        "Assessment type (exam/assignment/seminar/practicalassignment): "
    ).strip()
    value = read_float("Value: ")
    weight = read_float("Weight: ")
    controller.register_assessment(class_code, assessment_type, value, weight)
    print("Assessment registered successfully!")


def configure_persistence(controller):
    print("1 - TXT")
    print("2 - XML")
    print("3 - JSON")
    option = input("Choose: ").strip()
    options = {"1": "TXT", "2": "XML", "3": "JSON"}
    persistence_type = options.get(option)

    if persistence_type is None:
        print("Invalid persistence type.")
        return

    controller.configure_persistence(persistence_type)


def print_admin_menu():
    print("\n===== ACADEMIC SYSTEM [ADMIN] =====")
    print("1 - Register class")
    print("2 - Register assessment")
    print("3 - List classes")
    print("4 - Class assessment summary report")
    print("5 - Assessment weight report")
    print("6 - Configure persistence type")
    print("7 - Save academic data")
    print("8 - Persistence configuration report")
    print("9 - Logout")
    print("0 - Exit")


def print_professor_menu():
    print("\n===== ACADEMIC SYSTEM [PROFESSOR] =====")
    print("1 - Register assessment")
    print("2 - List classes")
    print("3 - Class assessment summary report")
    print("4 - Assessment weight report")
    print("5 - Logout")
    print("0 - Exit")


def handle_admin_option(option: str, controller) -> bool:
    match option:
        case "1":
            register_class(controller)
        case "2":
            register_assessment(controller)
        case "3":
            controller.list_classes()
        case "4":
            print(controller.generate_class_summary_report())
        case "5":
            print(controller.generate_assessment_weight_report())
        case "6":
            configure_persistence(controller)
        case "7":
            controller.save()
        case "8":
            print(controller.generate_persistence_configuration_report())
        case "9":
            Session.logout()
            print("Logged out successfully.")
            return False
        case "0":
            print("Exiting. Goodbye!")
            raise SystemExit(0)
        case _:
            print("Invalid option.")
    return True


def handle_professor_option(option: str, controller) -> bool:
    match option:
        case "1":
            register_assessment(controller)
        case "2":
            controller.list_classes()
        case "3":
            print(controller.generate_class_summary_report())
        case "4":
            print(controller.generate_assessment_weight_report())
        case "5":
            Session.logout()
            print("Logged out successfully.")
            return False
        case "0":
            print("Exiting. Goodbye!")
            raise SystemExit(0)
        case _:
            print("Invalid option.")
    return True


def run_session(controller):
    running = True
    while running:
        try:
            if Session.is_admin():
                print_admin_menu()
                running = handle_admin_option(input("Choose: ").strip(), controller)
            else:
                print_professor_menu()
                running = handle_professor_option(input("Choose: ").strip(), controller)
        except (AcademicSystemException, SecurityException, ValueError) as exc:
            print(f"Error: {exc}")
        print()


def run_cli():
    auth_controller = AuthenticationController()
    controller = ControllerFactory.get_academic_system_controller()

    while True:
        print("\n===== ACADEMIC SYSTEM LOGIN =====")
        username = input("Username: ").strip()
        password = input("Password: ").strip()

        try:
            user = auth_controller.authenticate_and_return(username, password)
        except AuthenticationException as exc:
            print(f"Login failed: {exc}")
            continue

        print(f"Welcome, {user.username}!")
        run_session(controller)


def main():
    setup_logging()

    parser = argparse.ArgumentParser(description="Academic System")
    parser.add_argument("--gui", action="store_true", help="Open tkinter interface.")
    args = parser.parse_args()

    if args.gui:
        from academic_system.view.tkinter_app import launch_app
        launch_app()
    else:
        run_cli()


if __name__ == "__main__":
    main()