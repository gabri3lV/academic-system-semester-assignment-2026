from typing import List
from academic_system.model.academic_class import AcademicClass
from academic_system.repository.academic_system_repository import AcademicSystemRepository


class TxtAcademicSystemRepository(AcademicSystemRepository):
    FILE_NAME = "academic-data.txt"

    def save(self, classes: List[AcademicClass]):
        with open(self.FILE_NAME, "w", encoding="utf-8") as f:
            for c in classes:
                f.write(f"CLASS: {c.code} | {c.title}\n")
                for a in c.assessments:
                    f.write(f"  ASSESSMENT: {a.get_type()} | "
                            f"value={a.value} | weight={a.weight}\n")
        print(f"Academic data saved to {self.FILE_NAME}")