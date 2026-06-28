from academic_system.security.authentication_service import AuthenticationService
from academic_system.exception.exceptions import AuthenticationException
from academic_system.model.user import User


class AuthenticationController:
    def __init__(self):
        self._auth_service = AuthenticationService()

    def authenticate(self, username: str, password: str) -> bool:
        try:
            self._auth_service.authenticate(username, password)
            return True
        except AuthenticationException:
            return False

    def authenticate_and_return(self, username: str,
                                password: str) -> User:
        return self._auth_service.authenticate(username, password)