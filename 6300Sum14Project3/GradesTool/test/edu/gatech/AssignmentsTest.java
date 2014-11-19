package edu.gatech;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AssignmentsTest extends TestCase {
    GradesDB gradesDb = null;
    Assignments assignments = null;

    @Before
    public void setUp() throws Exception {
        gradesDb = new GradesDB(Constants.GRADES_DB);
        assignments = new Assignments(gradesDb);
    }

    @After
    public void tearDown() throws Exception {
        gradesDb = null;
        assignments = null;
    }

    // Tests that when the database does not change that the Assignment size is the same after a refresh.
    @Test
    public void testRefreshNoChange() {
        try {
            int beforeRefreshSize = assignments.getAssignments().size();
            assignments.refresh();
            assertEquals(beforeRefreshSize, assignments.getAssignments().size());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the current assignments in the list is 3.
    @Test
    public void testGetAssignments() {
        try {
            assertEquals(gradesDb.getNumAssignments(), assignments.getAssignments().size());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that an empty list is returned if the assignment list is empty.
    @Test
    public void testGetNoAssignments() {
        try {
            assignments.setAssignments(null);
            assertTrue(assignments.getAssignments().size() == 0);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Tests that the Assignment size is equal after refresh when the assignments have been updated.
    @Test
    public void testRefreshWithChanges() {
        try {
            int beforeRefreshSize = assignments.getAssignments().size();
            assignments.setAssignments(null);
            assignments.refresh();
            assertEquals(beforeRefreshSize, assignments.getAssignments().size());
        } catch (Exception e) {
            fail("Exception");
        }
    }

}
