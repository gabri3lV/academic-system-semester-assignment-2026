from academic_system.academic_system import AcademicSystem
from academic_system.model.academic_class import AcademicClass
from academic_system.exception.exceptions import InvalidClassException
import logging

logger = logging.getLogger(__name__)


class ClassService:
    def __init__(self, system: AcademicSystem):
        self._system = system

    def register_class(self, code: str, title: str):
        if not code or not code.strip():
            raise InvalidClassException("Class code cannot be blank.")
        if not title or not title.strip():
            raise InvalidClassException("Class title cannot be blank.")
        self._system.add_class(AcademicClass(code.strip(), title.strip()))
        logger.info(f"Class registered: code={code}, title={title}")

    def list_classes(self):
        classes = self._system.get_classes()
        if not classes:
            print("No classes registered.")
            return
        print("\n--- Registered Classes ---")
        for c in classes:
            print(f"{c.code} | {c.title} | "
                  f"Assessments: {len(c.assessments)}")