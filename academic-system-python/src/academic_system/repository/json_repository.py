import json
from typing import List
from academic_system.model.academic_class import AcademicClass
from academic_system.repository.academic_system_repository import AcademicSystemRepository


class JsonAcademicSystemRepository(AcademicSystemRepository):
    FILE_NAME = "academic-data.json"

    def save(self, classes: List[AcademicClass]):
        data = {
            "classes": [
                {
                    "code": c.code,
                    "title": c.title,
                    "assessments": [
                        {
                            "type": a.get_type(),
                            "value": a.value,
                            "weight": a.weight
                        }
                        for a in c.assessments
                    ]
                }
                for c in classes
            ]
        }
        with open(self.FILE_NAME, "w", encoding="utf-8") as f:
            json.dump(data, f, indent=2, ensure_ascii=False)
        print(f"Academic data saved to {self.FILE_NAME}")