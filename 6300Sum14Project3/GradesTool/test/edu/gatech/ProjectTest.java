package edu.gatech;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

public class ProjectTest extends TestCase {
    Project project = null;

    @Override
    protected void setUp() throws Exception {
        GradesDB gradesDb = new GradesDB(Constants.GRADES_DB);
        Projects projects = new Projects(gradesDb);
        for (Project item : projects.getProjects()) {
            if (item.getNumber().equals("P1")) {
                project = item;
                break;
            }
        }
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        project = null;
        super.tearDown();
    }

    @Test
    public void testProjectNumber() {
        // Test for project number
        assertEquals("P1", project.getNumber());
    }

    @Test
    public void testProjectDescription() {
        // Test for project description
        assertEquals("WordCount in Java", project.getDescription());
    }

    @Test
    public void testProjectAverageGrade() {
        // Test for checking average grade
        assertEquals(93, project.getAverageProjectGrade());
    }

    @Test
    public void testProjectTeamNames() {
        // Test for getting team names in the project
        List<String> teamNames = project.getTeamNames();
        assertEquals(3, teamNames.size());
        assertTrue(teamNames.contains("Team 1"));
    }

}
