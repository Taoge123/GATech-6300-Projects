package edu.gatech;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

public class ProjectsTest extends TestCase {
    GradesDB gradesDb = null;
    Projects projects = null;

    @Override
    protected void setUp() throws Exception {
        gradesDb = new GradesDB(Constants.GRADES_DB);
        projects = new Projects(gradesDb);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        gradesDb = null;
        projects = null;
        super.tearDown();
    }

    @Test
    public void testProjectsSize() {
        // Test for number of projects
        assertEquals(3, projects.getProjects().size());
    }

    public void testNullDbInstance() {
        // Test null database is handled properly
        Projects nullProjects = new Projects(null);
        assertEquals("Projects handle null db", 0, nullProjects.getProjects().size());
    }

    @Test
    public void testProjectsRefresh() {
        // Test for refresh
        int numProjectsBeforeRefresh = projects.getProjects().size();
        projects.setProjects(new ArrayList<Project>());
        projects.refresh();
        int numProjectsAfterRefresh = projects.getProjects().size();
        assertEquals(numProjectsBeforeRefresh, numProjectsAfterRefresh);
    }

    @Test
    public void testProjectByName() {
        // Test retrieving a project by its name
        Project project = projects.getProjectByName("P1");
        assertEquals("P1", project.getNumber());
    }

    @Test
    public void testNullProjectByName() {
        // Test retrieving a project by its name that does not exist
        Project project = projects.getProjectByName("P10");
        assertEquals(null, project);
    }

}
