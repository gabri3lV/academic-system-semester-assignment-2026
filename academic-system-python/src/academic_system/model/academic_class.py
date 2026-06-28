from __future__ import annotations
from typing import List
from academic_system.model.assessment import Assessment


class AcademicClass:
    def __init__(self, code: str, title: str):
        self.code = code
        self.title = title
        self.assessments: List[Assessment] = []

    def add_assessment(self, assessment: Assessment):
        self.assessments.append(assessment)

    def __eq__(self, other):
        if not isinstance(other, AcademicClass):
            return False
        return self.code == other.code

    def __hash__(self):
        return hash(self.code)

    def __str__(self):
        return f"AcademicClass(code={self.code}, title={self.title})"