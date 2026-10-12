package com.latihan.git1224026;

/**
 * Soal 4 - StudentGrade.
 */
public class StudentGrade {

    public String calculateGrade(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        if (score >= 80) {
            return "A";
        } else if (score >= 70) {
            return "B";
        } else if (score >= 60) {
            return "C";
        } else if (score >= 50) {
            return "D";
        } else {
            return "E";
        }
    }

    public boolean isPassed(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        return score >= 60;
    }
}
