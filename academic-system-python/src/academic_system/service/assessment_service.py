from academic_system.academic_system import AcademicSystem
from academic_system.model.exam import Exam
from academic_system.model.assignment import Assignment
from academic_system.model.seminar import Seminar
from academic_system.model.practical_assignment import PracticalAssignment
from academic_system.exception.exceptions import InvalidAssessmentException
import logging

logger = logging.getLogger(__name__)


class AssessmentService:
    def __init__(self, system: AcademicSystem):
        self._system = system

    def register_assessment(self, class_code: str, assessment_type: str,
                            value: float, weight: float):
        academic_class = self._system.find_class_by_code(class_code)
        if academic_class is None:
            raise InvalidAssessmentException(
                f"Class not found: {class_code}")

        match assessment_type.lower():
            case "exam":
                assessment = Exam(value=value, weight=weight)
            case "assignment":
                assessment = Assignment(value=value, weight=weight)
            case "seminar":
                assessment = Seminar(value=value, weight=weight)
            case "practicalassignment":
                assessment = PracticalAssignment(value=value, weight=weight)
            case _:
                raise InvalidAssessmentException(
                    f"Invalid assessment type: {assessment_type}")

        academic_class.add_assessment(assessment)
        logger.info(f"Assessment registered: class={class_code}, "
                    f"type={assessment_type}")