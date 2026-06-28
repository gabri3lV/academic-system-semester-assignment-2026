from academic_system.service.class_service import ClassService
from academic_system.service.assessment_service import AssessmentService
from academic_system.service.persistence_service import PersistenceService
from academic_system.service.report_service import ReportService
from academic_system.security.session import Session
from academic_system.repository.persistence_type import PersistenceType
from academic_system.exception.exceptions import AuthorizationException
import logging

logger = logging.getLogger(__name__)


class AcademicSystemController:
    def __init__(self, class_service: ClassService,
                 assessment_service: AssessmentService,
                 persistence_service: PersistenceService,
                 report_service: ReportService):
        self._class_service = class_service
        self._assessment_service = assessment_service
        self._persistence_service = persistence_service
        self._report_service = report_service

    def register_class(self, code: str, title: str):
        if not Session.is_admin():
            logger.warning(f"Authorization failure: "
                           f"user={Session.get_current_user()}, "
                           f"operation=register_class")
            raise AuthorizationException(
                "Only administrators can register classes.")
        self._class_service.register_class(code, title)

    def register_assessment(self, class_code: str, assessment_type: str,
                            value: float, weight: float):
        self._assessment_service.register_assessment(
            class_code, assessment_type, value, weight)

    def list_classes(self):
        self._class_service.list_classes()

    def get_classes(self):
        return self._class_service._system.get_classes()

    def configure_persistence(self, type_str: str):
        if not Session.is_admin():
            raise AuthorizationException(
                "Only administrators can configure persistence.")
        match type_str.upper():
            case "TXT":
                self._persistence_service.set_persistence_type(
                    PersistenceType.TXT)
            case "XML":
                self._persistence_service.set_persistence_type(
                    PersistenceType.XML)
            case "JSON":
                self._persistence_service.set_persistence_type(
                    PersistenceType.JSON)
            case _:
                print("Invalid persistence type.")

    def save(self):
        self._persistence_service.save()

    def generate_class_summary_report(self) -> str:
        return self._report_service.generate_class_summary_report()

    def generate_assessment_weight_report(self) -> str:
        return self._report_service.generate_assessment_weight_report()

    def generate_persistence_configuration_report(self) -> str:
        return self._report_service.generate_persistence_configuration_report()