from academic_system.academic_system import AcademicSystem
from academic_system.repository.persistence_configuration import PersistenceConfiguration
from academic_system.repository.persistence_type import PersistenceType
from academic_system.repository.txt_repository import TxtAcademicSystemRepository
from academic_system.repository.xml_repository import XmlAcademicSystemRepository
from academic_system.repository.json_repository import JsonAcademicSystemRepository
import logging

logger = logging.getLogger(__name__)


class PersistenceService:
    def __init__(self, system: AcademicSystem):
        self._system = system

    def save(self):
        persistence_type = PersistenceConfiguration.get_current_type()
        logger.info(f"Saving data using: {persistence_type}")
        self._get_repository().save(self._system.get_classes())
        logger.info(f"Data saved successfully using: {persistence_type}")

    def set_persistence_type(self, persistence_type: PersistenceType):
        PersistenceConfiguration.set_current_type(persistence_type)
        logger.info(f"Persistence type configured to: {persistence_type}")
        print(f"Persistence configured as {persistence_type.value}")

    def get_current_type(self) -> PersistenceType:
        return PersistenceConfiguration.get_current_type()

    def _get_repository(self):
        match PersistenceConfiguration.get_current_type():
            case PersistenceType.XML:
                return XmlAcademicSystemRepository()
            case PersistenceType.JSON:
                return JsonAcademicSystemRepository()
            case _:
                return TxtAcademicSystemRepository()