import os
import sys

import pytest

PROJECT_ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
if PROJECT_ROOT not in sys.path:
    sys.path.insert(0, PROJECT_ROOT)

from academic_system.academic_system import AcademicSystem
from academic_system.controller.controller_factory import ControllerFactory
from academic_system.exception.exceptions import (
    AuthenticationException,
    AuthorizationException,
    InvalidAssessmentException,
    InvalidClassException,
)
from academic_system.repository.persistence_configuration import (
    PersistenceConfiguration,
)
from academic_system.repository.persistence_type import PersistenceType
from academic_system.security.authentication_service import AuthenticationService
from academic_system.security.session import Session


@pytest.fixture(autouse=True)
def reset_state():
    AcademicSystem.reset_instance()
    ControllerFactory._instance = None
    Session.logout()
    PersistenceConfiguration.set_current_type(PersistenceType.TXT)
    yield
    Session.logout()


def login(username="admin", password="admin123"):
    return AuthenticationService().authenticate(username, password)


def controller():
    return ControllerFactory.get_academic_system_controller()


def test_admin_authentication_starts_session():
    user = login()

    assert user.username == "admin"
    assert Session.is_logged()
    assert Session.is_admin()


def test_invalid_authentication_raises_exception():
    with pytest.raises(AuthenticationException):
        AuthenticationService().authenticate("admin", "wrong-password")

    assert not Session.is_logged()


def test_admin_can_register_class():
    login()
    academic_controller = controller()

    academic_controller.register_class("POO", "Object-Oriented Programming")

    classes = academic_controller.get_classes()
    assert len(classes) == 1
    assert classes[0].code == "POO"
    assert classes[0].title == "Object-Oriented Programming"


def test_professor_cannot_register_class():
    login("professor", "prof123")

    with pytest.raises(AuthorizationException):
        controller().register_class("POO", "Object-Oriented Programming")


def test_blank_class_data_is_invalid():
    login()

    with pytest.raises(InvalidClassException):
        controller().register_class("", "Object-Oriented Programming")

    with pytest.raises(InvalidClassException):
        controller().register_class("POO", "")


def test_register_assessment_for_existing_class():
    login()
    academic_controller = controller()
    academic_controller.register_class("POO", "Object-Oriented Programming")

    academic_controller.register_assessment("POO", "exam", 10.0, 0.6)

    academic_class = academic_controller.get_classes()[0]
    assert len(academic_class.assessments) == 1
    assert academic_class.assessments[0].get_type() == "Exam"
    assert academic_class.assessments[0].value == 10.0
    assert academic_class.assessments[0].weight == 0.6


def test_assessment_requires_existing_class():
    login()

    with pytest.raises(InvalidAssessmentException):
        controller().register_assessment("UNKNOWN", "exam", 10.0, 0.5)


def test_assessment_value_and_weight_are_validated():
    login()
    academic_controller = controller()
    academic_controller.register_class("POO", "Object-Oriented Programming")

    with pytest.raises(ValueError):
        academic_controller.register_assessment("POO", "exam", 0, 0.5)

    with pytest.raises(ValueError):
        academic_controller.register_assessment("POO", "exam", 10, 1.5)


def test_reports_include_classes_assessments_and_weight_status():
    login()
    academic_controller = controller()
    academic_controller.register_class("POO", "Object-Oriented Programming")
    academic_controller.register_assessment("POO", "exam", 10.0, 0.6)
    academic_controller.register_assessment("POO", "seminar", 8.0, 0.4)

    summary = academic_controller.generate_class_summary_report()
    weight_report = academic_controller.generate_assessment_weight_report()

    assert "POO | Object-Oriented Programming" in summary
    assert "Exam" in summary
    assert "Seminar" in summary
    assert "Total weight: 1.0" in weight_report
    assert "VALID" in weight_report


def test_admin_can_configure_persistence_type():
    login()

    controller().configure_persistence("JSON")

    assert PersistenceConfiguration.get_current_type() == PersistenceType.JSON


def test_professor_cannot_configure_persistence_type():
    login("professor", "prof123")

    with pytest.raises(AuthorizationException):
        controller().configure_persistence("JSON")