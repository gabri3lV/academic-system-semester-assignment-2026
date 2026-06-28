from __future__ import annotations
from typing import Optional
from academic_system.model.user import User
from academic_system.model.role import Role
import logging

logger = logging.getLogger(__name__)


class Session:
    _current_user: Optional[User] = None

    @classmethod
    def login(cls, user: User):
        cls._current_user = user

    @classmethod
    def logout(cls):
        if cls._current_user:
            logger.info(f"Logout: username={cls._current_user.username}, "
                        f"role={cls._current_user.role}")
        cls._current_user = None

    @classmethod
    def get_current_user(cls) -> Optional[User]:
        return cls._current_user

    @classmethod
    def get_current_role(cls) -> Optional[Role]:
        return cls._current_user.role if cls._current_user else None

    @classmethod
    def is_logged(cls) -> bool:
        return cls._current_user is not None

    @classmethod
    def is_admin(cls) -> bool:
        return (cls._current_user is not None and
                cls._current_user.role == Role.ADMIN)