class AcademicSystemException(RuntimeError):
    """Superclasse para exceções de domínio acadêmico."""
    def __init__(self, message: str):
        super().__init__(message)


class InvalidClassException(AcademicSystemException):
    """Lançada quando os dados de uma turma são inválidos."""
    pass


class InvalidAssessmentException(AcademicSystemException):
    """Lançada quando os dados de uma avaliação são inválidos."""
    pass


class KeyboardInputException(RuntimeError):
    """Superclasse para exceções de entrada de teclado."""
    def __init__(self, message: str):
        super().__init__(message)


class InvalidNumericInputException(KeyboardInputException):
    """Lançada quando a entrada não pode ser convertida para número."""
    pass


class UnsupportedMenuOptionException(KeyboardInputException):
    """Lançada quando o usuário seleciona opção de menu inválida."""
    pass


class SecurityException(RuntimeError):
    """Superclasse para exceções de segurança."""
    def __init__(self, message: str):
        super().__init__(message)


class AuthenticationException(SecurityException):
    """Lançada quando as credenciais são inválidas."""
    pass


class AuthorizationException(SecurityException):
    """Lançada quando o usuário não tem permissão."""
    pass