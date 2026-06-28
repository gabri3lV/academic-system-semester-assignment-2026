package org.example.academic.system.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AcademicClassEqualityTest {

    @Test
    void twoClassesWithSameCodeShouldBeEqual() {
        AcademicClass c1 = new AcademicClass("POO01", "Orientacao a Objetos");
        AcademicClass c2 = new AcademicClass("POO01", "Titulo Diferente");
        assertEquals(c1, c2);
    }

    @Test
    void twoClassesWithSameCodeShouldHaveSameHashCode() {
        AcademicClass c1 = new AcademicClass("POO01", "Orientacao a Objetos");
        AcademicClass c2 = new AcademicClass("POO01", "Titulo Diferente");
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void twoClassesWithDifferentCodesShouldNotBeEqual() {
        AcademicClass c1 = new AcademicClass("POO01", "Orientacao a Objetos");
        AcademicClass c2 = new AcademicClass("POO02", "Orientacao a Objetos");
        assertNotEquals(c1, c2);
    }
}