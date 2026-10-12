package com.latihan.git1224026;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Soal 4 - Unit test untuk StudentGrade.
 * Cyclomatic Complexity calculateGrade() = 7, mencakup 7 independent paths.
 */
class StudentGradeTest {

    private final StudentGrade grade = new StudentGrade();

    // --- calculateGrade ---

    @Test
    void testGradeA() {
        assertEquals("A", grade.calculateGrade(85));
        assertEquals("A", grade.calculateGrade(80)); // boundary
        assertEquals("A", grade.calculateGrade(100)); // boundary atas
    }

    @Test
    void testGradeB() {
        assertEquals("B", grade.calculateGrade(75));
        assertEquals("B", grade.calculateGrade(70)); // boundary
        assertEquals("B", grade.calculateGrade(79));
    }

    @Test
    void testGradeC() {
        assertEquals("C", grade.calculateGrade(65));
        assertEquals("C", grade.calculateGrade(60)); // boundary lulus
    }

    @Test
    void testGradeD() {
        assertEquals("D", grade.calculateGrade(55));
        assertEquals("D", grade.calculateGrade(50)); // boundary
    }

    @Test
    void testGradeE() {
        assertEquals("E", grade.calculateGrade(40));
        assertEquals("E", grade.calculateGrade(0)); // boundary bawah
    }

    @Test
    void testGradeInvalidLow() {
        assertThrows(IllegalArgumentException.class, () -> grade.calculateGrade(-1));
    }

    @Test
    void testGradeInvalidHigh() {
        assertThrows(IllegalArgumentException.class, () -> grade.calculateGrade(101));
    }

    // --- isPassed ---

    @Test
    void testIsPassedTrue() {
        assertTrue(grade.isPassed(60)); // boundary lulus
        assertTrue(grade.isPassed(80));
        assertTrue(grade.isPassed(100));
    }

    @Test
    void testIsPassedFalse() {
        assertFalse(grade.isPassed(59));
        assertFalse(grade.isPassed(0));
        assertFalse(grade.isPassed(50));
    }

    @Test
    void testIsPassedInvalid() {
        assertThrows(IllegalArgumentException.class, () -> grade.isPassed(-5));
        assertThrows(IllegalArgumentException.class, () -> grade.isPassed(150));
    }
}
