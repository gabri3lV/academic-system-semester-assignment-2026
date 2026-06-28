from typing import List
import xml.etree.ElementTree as ET
from academic_system.model.academic_class import AcademicClass
from academic_system.repository.academic_system_repository import AcademicSystemRepository


class XmlAcademicSystemRepository(AcademicSystemRepository):
    FILE_NAME = "academic-data.xml"

    def save(self, classes: List[AcademicClass]):
        root = ET.Element("academicData")
        for c in classes:
            class_elem = ET.SubElement(root, "class")
            ET.SubElement(class_elem, "code").text = c.code
            ET.SubElement(class_elem, "title").text = c.title
            assessments_elem = ET.SubElement(class_elem, "assessments")
            for a in c.assessments:
                assessment_elem = ET.SubElement(assessments_elem, "assessment")
                ET.SubElement(assessment_elem, "type").text = a.get_type()
                ET.SubElement(assessment_elem, "value").text = str(a.value)
                ET.SubElement(assessment_elem, "weight").text = str(a.weight)

        tree = ET.ElementTree(root)
        ET.indent(tree, space="  ")
        tree.write(self.FILE_NAME, encoding="unicode", xml_declaration=True)
        print(f"Academic data saved to {self.FILE_NAME}")