from __future__ import annotations
from typing import List, Optional
from academic_system.model.academic_class import AcademicClass


class AcademicSystem:
    _instance: Optional[AcademicSystem] = None

    def __init__(self):
        self._classes: List[AcademicClass] = []

    @classmethod
    def get_instance(cls) -> AcademicSystem:
        if cls._instance is None:
            cls._instance = AcademicSystem()
        return cls._instance

    @classmethod
    def reset_instance(cls):
        """Usado apenas nos testes."""
        cls._instance = None

    def add_class(self, academic_class: AcademicClass):
        self._classes.append(academic_class)

    def get_classes(self) -> List[AcademicClass]:
        return self._classes

    def find_class_by_code(self, code: str) -> Optional[AcademicClass]:
        for c in self._classes:
            if c.code == code:
                return c
        return None