from academic_system.repository.persistence_type import PersistenceType


class PersistenceConfiguration:
    _current_type: PersistenceType = PersistenceType.TXT

    @classmethod
    def get_current_type(cls) -> PersistenceType:
        return cls._current_type

    @classmethod
    def set_current_type(cls, persistence_type: PersistenceType):
        cls._current_type = persistence_type