package edu.gatech;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

public class GradesDBTest3 extends TestCase {
    GradesDB db = null;

    @Override
    protected void setUp() throws Exception {
        db = new GradesDB(Constants.GRADES_DB);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSetGetFormula() {
        try {
            db.setFormula("(AS + AT)/2");
            assertTrue(db.getFormula().equals("(AS + AT)/2"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetGradeFromFormula1() {
        try {
            db.setFormula("(AS + AT)/2");
            assertEquals(db.getStudentGrade(Student.getStudentByName("Freddie Catlay")), (93.0 + 90.0) / 2, 0.01);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetGradeFromFormula2() {
        try {
            db.setFormula("(PR1+PR2+PR3)/3");
            double expected = (93.0 + 95.0 + 100.0) / 3.0;
            assertEquals(db.getStudentGrade(Student.getStudentByName("Freddie Catlay")), expected, 0.01);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetGradeFromFormula3() {
        try {
            db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
            double expected = (90.0 * 0.2 + 93 * 0.2 + ((93.0 + 95.0 + 100.0) / 3) * 0.6);
            assertEquals(db.getStudentGrade(Student.getStudentByName("Freddie Catlay")), expected, 0.01);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetGradeFromFormulaNoFormula() {
        try {
            assertEquals(db.getStudentGrade(Student.getStudentByName("Freddie Catlay")), -1, 0.01);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAllGrades1() {
        try {
            db.setFormula("AS");
            double expected = 90.0;
            Map<Student, Double> grades = db.getAllGrades();
            double current_grade = -1.0;
            for (Map.Entry<Student, Double> entry : grades.entrySet()) {
                if (entry.getKey().getName().equals("Genista Parrish")) {
                    current_grade = entry.getValue();
                }
            }
            assertEquals(current_grade, expected, 0.01);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAllGrades2() {
        try {
            db.setFormula("AS");
            double expected = 93.0;
            Map<Student, Double> grades = db.getAllGrades();
            double current_grade = -1.0;
            for (Map.Entry<Student, Double> entry : grades.entrySet()) {
                if (entry.getKey().getName().equals("Wilfrid Eastwood")) {
                    current_grade = entry.getValue();
                }
            }
            assertEquals(current_grade, expected, 0.01);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAllGrades3() {
        try {
            db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
            double expected = (90.0 * 0.2 + 93 * 0.2 + ((93.0 + 95.0 + 100.0) / 3) * 0.6);
            Map<Student, Double> grades = db.getAllGrades();
            double current_grade = -1.0;
            for (Map.Entry<Student, Double> entry : grades.entrySet()) {
                if (entry.getKey().getName().equals("Freddie Catlay")) {
                    current_grade = entry.getValue();
                }
            }
            assertEquals(current_grade, expected, 0.01);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAllGrades4() {
        try {
            db.setFormula("AS");
            Map<Student, Double> grades = db.getAllGrades();
            assertTrue(grades.size() == db.getNumStudents());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAllGradesNoFormual() {
        try {
            Map<Student, Double> grades = db.getAllGrades();
            assertTrue(grades == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAverageGrade1() {
        try {
            db.setFormula("AS");
            double average = db.getAverageGrade();
            double expected = 92.0;
            assertEquals(average, expected, 0.1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAverageGrade2() {
        try {
            db.setFormula("(AS+AT)/2");
            double average = db.getAverageGrade();
            double expected = 91.8;
            assertEquals(average, expected, 0.1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAverageGradeNoFormula() {
        try {
            double average = db.getAverageGrade();
            assertEquals(average, -1, 0.1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetMedianGrade1() {
        try {
            db.setFormula("AS");
            double average = db.getMedianGrade();
            double expected = 92.5;
            assertEquals(average, expected, 0.1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetMedianGrade2() {
        try {
            db.setFormula("(AS+AT)/2");
            double average = db.getMedianGrade();
            double expected = 91.1;
            assertEquals(average, expected, 0.1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetMedianGradeNoFormula() {
        try {
            double average = db.getMedianGrade();
            assertEquals(average, -1, 0.1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

}