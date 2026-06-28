from __future__ import annotations
from typing import Optional
from academic_system.academic_system import AcademicSystem
from academic_system.service.class_service import ClassService
from academic_system.service.assessment_service import AssessmentService
from academic_system.service.persistence_service import PersistenceService
from academic_system.service.report_service import ReportService
from academic_system.controller.academic_system_controller import AcademicSystemController


class ControllerFactory:
    _instance: Optional[AcademicSystemController] = None

    @classmethod
    def get_academic_system_controller(cls) -> AcademicSystemController:
        if cls._instance is None:
            system = AcademicSystem.get_instance()
            cls._instance = AcademicSystemController(
                ClassService(system),
                AssessmentService(system),
                PersistenceService(system),
                ReportService(system)
            )
        return cls._instance