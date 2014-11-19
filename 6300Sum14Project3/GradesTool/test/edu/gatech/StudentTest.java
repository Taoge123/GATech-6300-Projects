package edu.gatech;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentTest extends TestCase {
    Student student = null;

    @Override
    @Before
    public void setUp() throws Exception {
        String name = "Rastus Kight";
        String gtid = "901234512";
        String email = "rk@gatech.edu";
        int cExperience = 2;
        int cPlusPlusExperience = 1;
        int javaExperience = 1;
        String csWorkExperience = "";
        student = new Student(name, gtid, email, cExperience, cPlusPlusExperience, javaExperience, csWorkExperience);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        student = null;
    }

    // Tests that the list of assignments is retrieved.
    @Test
    public void testGetAssignments() {
        try {
            assertEquals(3, student.getAssignments().size());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the method returns the expected attendance for the student.
    @Test
    public void testGetAttendance() {
        try {
            assertEquals(96, student.getAttendance(), 0);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the method returns the expected name for the student.
    @Test
    public void testGetName() {
        try {
            assertTrue(student.getName().equals("Rastus Kight"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the method returns the expected email for the student.
    @Test
    public void testGetEmail() {
        try {
            assertTrue(student.getEmail().equals("rk@gatech.edu"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the method returns the expected GTid for the student.
    @Test
    public void testGetGtid() {
        try {
            assertTrue(student.getGtid().equals("901234512"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the method returns the expected C experience for the student.
    @Test
    public void testGetC() {
        try {
            assertEquals(2, student.getC());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the method returns the expected C++ experience for the student.
    @Test
    public void testGetCpp() {
        try {
            assertEquals(1, student.getCpp());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the method returns the expected Java experience for the student.
    @Test
    public void testGetJava() {
        try {
            assertEquals(1, student.getJava());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the method returns the expected CS job experience for the student.
    @Test
    public void testGetCSJobExperience() {
        try {
            assertTrue(student.getCSJobExperience().equals(""));
        } catch (Exception e) {
            fail("Exception");
        }
    }

}
