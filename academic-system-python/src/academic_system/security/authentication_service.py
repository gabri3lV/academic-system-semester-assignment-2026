from academic_system.model.user import User
from academic_system.model.role import Role
from academic_system.security.session import Session
from academic_system.exception.exceptions import AuthenticationException
import logging

logger = logging.getLogger(__name__)


class AuthenticationService:
    def __init__(self):
        self._users = [
            User("admin", "admin123", Role.ADMIN),
            User("professor", "prof123", Role.PROFESSOR),
        ]

    def authenticate(self, username: str, password: str) -> User:
        for user in self._users:
            if user.username == username and user.password == password:
                Session.login(user)
                logger.info(f"Successful login: username={username}, "
                            f"role={user.role}")
                return user
        logger.warning(f"Failed login attempt: username={username}")
        raise AuthenticationException("Invalid username or password.")