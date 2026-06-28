from abc import ABC, abstractmethod
from typing import List
from academic_system.model.academic_class import AcademicClass


class AcademicSystemRepository(ABC):
    @abstractmethod
    def save(self, classes: List[AcademicClass]):
        pass