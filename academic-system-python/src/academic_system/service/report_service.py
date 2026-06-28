from academic_system.academic_system import AcademicSystem
from academic_system.repository.persistence_configuration import PersistenceConfiguration
from academic_system.security.session import Session
import logging

logger = logging.getLogger(__name__)


class ReportService:
    def __init__(self, system: AcademicSystem):
        self._system = system

    def generate_class_summary_report(self) -> str:
        logger.info(f"Generating class summary report: "
                    f"role={Session.get_current_role()}")
        lines = ["===== CLASS ASSESSMENT SUMMARY REPORT ====="]
        classes = self._system.get_classes()
        if not classes:
            lines.append("No classes registered.")
            return "\n".join(lines)
        for c in classes:
            lines.append(f"\nClass: {c.code} | {c.title}")
            if not c.assessments:
                lines.append("  No assessments registered.")
            else:
                for a in c.assessments:
                    lines.append(f"  - {a.get_type()} | "
                                 f"value={a.value} | weight={a.weight}")
        return "\n".join(lines)

    def generate_assessment_weight_report(self) -> str:
        logger.info(f"Generating weight report: "
                    f"role={Session.get_current_role()}")
        lines = ["===== ASSESSMENT WEIGHT REPORT ====="]
        for c in self._system.get_classes():
            total = sum(a.weight for a in c.assessments)
            status = "VALID ✓" if abs(total - 1.0) < 0.001 else "INVALID ✗"
            lines.append(f"Class: {c.code} | {c.title} | "
                         f"Total weight: {total} | {status}")
        return "\n".join(lines)

    def generate_persistence_configuration_report(self) -> str:
        logger.info(f"Generating persistence config report: "
                    f"role={Session.get_current_role()}")
        return (f"===== PERSISTENCE CONFIGURATION REPORT =====\n"
                f"Current persistence type: "
                f"{PersistenceConfiguration.get_current_type().value}")