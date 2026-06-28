from abc import ABC
from dataclasses import dataclass


@dataclass
class Assessment(ABC):
    value: float
    weight: float

    def __post_init__(self):
        if self.value <= 0:
            raise ValueError("Assessment value must be positive.")
        if self.weight <= 0 or self.weight > 1.0:
            raise ValueError("Weight must be between 0 and 1.0.")

    def get_type(self) -> str:
        return self.__class__.__name__

    def __str__(self):
        return (
            f"{self.get_type()} | "
            f"value={self.value} | "
            f"weight={self.weight}"
        )