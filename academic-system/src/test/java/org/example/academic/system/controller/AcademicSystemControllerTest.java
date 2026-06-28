package org.example.academic.system.controller;

import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;
import org.example.academic.system.security.Session;
import org.example.academic.system.service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AcademicSystemControllerTest {

    private ClassService classService;
    private AssessmentService assessmentService;
    private PersistenceService persistenceService;
    private ReportService reportService;
    private AcademicSystemController controller;

    @BeforeEach
    void setUp() {
        classService = mock(ClassService.class);
        assessmentService = mock(AssessmentService.class);
        persistenceService = mock(PersistenceService.class);
        reportService = mock(ReportService.class);

        controller = new AcademicSystemController(
                classService, assessmentService,
                persistenceService, reportService
        );
    }

    @AfterEach
    void tearDown() {
        Session.logout();
    }

    @Test
    void adminShouldBeAbleToRegisterClass() {
        Session.login(new User("admin", "admin123", Role.ADMIN));
        controller.registerClass("POO01", "Orientacao a Objetos");
        verify(classService, times(1)).registerClass("POO01", "Orientacao a Objetos");
    }

    @Test
    void professorShouldNotBeAbleToRegisterClass() {
        Session.login(new User("professor", "prof123", Role.PROFESSOR));
        assertThrows(AuthorizationException.class, () ->
                controller.registerClass("POO01", "Orientacao a Objetos")
        );
        verify(classService, never()).registerClass(any(), any());
    }

    @Test
    void registerAssessmentShouldDelegateToAssessmentService() {
        Session.login(new User("professor", "prof123", Role.PROFESSOR));
        controller.registerAssessment("POO01", "exam", 10.0, 1.0);
        verify(assessmentService, times(1))
                .registerAssessment("POO01", "exam", 10.0, 1.0);
    }
}