package edu.gatech;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AssignmentTest extends TestCase {
    Assignment assignment = null;

    @Override
    @Before
    public void setUp() throws Exception {
        String assignmentName = "Assignment 1";
        String description = "swiki page";
        int averageGrade = 99;
        Map<String, Integer> studentMap = new HashMap<String, Integer>();
        studentMap.put("Ratus Kight", 100);
        assignment = new Assignment(assignmentName, description, averageGrade, studentMap);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        assignment = null;
    }

    // Tests that the expected grade is returned.
    @Test
    public void testGetGradeForStudent() {
        try {
            assertEquals(100, assignment.getGradeForStudent("Ratus Kight"), 0);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that -1 is returned when a non-existing student is provided.
    @Test
    public void testGetGradeForNullStudent() {
        try {
            assertEquals(-1, assignment.getGradeForStudent("bogus name"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the correct average is returned from the current assignment.
    @Test
    public void testGetAverageGrade() {
        try {
            assertEquals("Average should be ", 99, assignment.getAverageGrade(), 0);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the correct description is received
    @Test
    public void testGetAssignmentName() {
        try {
            assertTrue(assignment.getAssignmentName().equals("Assignment 1"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the correct description is received
    @Test
    public void testGetDescription() {
        try {
            assertTrue(assignment.getDescription().equals("swiki page"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

}
