from dataclasses import dataclass
from academic_system.model.role import Role


@dataclass
class User:
    username: str
    password: str
    role: Role

    def is_admin(self) -> bool:
        return self.role == Role.ADMIN

    def __eq__(self, other):
        if not isinstance(other, User):
            return False
        return self.username == other.username

    def __hash__(self):
        return hash(self.username)

    def __str__(self):
        return f"User(username={self.username}, role={self.role})"