package edu.gatech;

import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

public class TeamsTest extends TestCase {
    GradesDB gradesDb = null;
    Teams teams = null;

    @Override
    protected void setUp() throws Exception {
        gradesDb = new GradesDB(Constants.GRADES_DB);
        teams = new Teams(gradesDb);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        gradesDb = null;
        teams = null;
        super.tearDown();
    }

    /** Test for constructor **/

    public void testGetNumStudents() {
        try {
            int numTeams = teams.getTeams().size();

            assertEquals("Correct number of teams are loaded", 9, numTeams);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify if the Teams class handles null value in constructor
     * gracefully
     */
    public void testNullDbInstance() {
        try {
            teams = new Teams(null);

            boolean emptyList = teams.getTeams().isEmpty();

            assertEquals("Teams constructor gracefully handles null", true, emptyList);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /** Test for get teams by student name **/

    public void testGetTeamByStudent() {
        try {

            HashSet<Team> teamsForStudent = teams.getTeamsByStudentName("Laraine Smith");

            boolean found = false;

            for (Team team : teamsForStudent) {

                if (team.getTeamNumber().equals("Team 1")) {
                    found = true;
                    break;
                }
            }

            assertEquals("Correct number of teams are loaded", true, found);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /** Test to verify if getTeamsByStudentName when input is null */
    public void testGetTeamByNullStudent() {
        try {

            HashSet<Team> teamsForStudent = teams.getTeamsByStudentName(null);

            assertEquals("get team by student gracefully handles null", true,
                    teamsForStudent.isEmpty());

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /** Test to verify if getTeamsByStudentName when an invalid student is input */
    public void testGetTeamByNonExistentStudent() {
        try {

            HashSet<Team> teamsForStudent = teams.getTeamsByStudentName("Daniel Smith");

            assertEquals("team not found for student", true, teamsForStudent.isEmpty());

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /** Test for get teams by student name and project number **/

    public void testGetTeamByStudentNameAndProjectNumber() {
        try {

            Team team = teams.getTeamByStudentNameAndProjectNumber("Laraine Smith", "P1");

            assertEquals("Correct team is loaded", "Team 2", team.getTeamNumber());
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify getTeamByStudentNameAndProjectNumber when student name is
     * null
     */
    public void testGetTeamByNullStudentAndProjectNumber() {
        try {

            Team team = teams.getTeamByStudentNameAndProjectNumber(null, "P1");

            assertEquals("get team by student and project number handles null", null, team);

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify getTeamByStudentNameAndProjectNumber when project number
     * is null
     */
    public void testGetTeamByStudentNameAndNullProject() {
        try {

            Team team = teams.getTeamByStudentNameAndProjectNumber("Laraine Smith", null);

            assertEquals("get team by student and project number handles null", null, team);

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify getTeamByStudentNameAndProjectNumber when an invalid
     * student is input
     */
    public void testGetTeamByNonExistentStudentAndProject() {
        try {

            Team team = teams.getTeamByStudentNameAndProjectNumber("Daniel Smith", "P1");

            assertEquals("team not found for student and project number", null, team);

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify getTeamByStudentNameAndProjectNumber when an invalid
     * project is input
     */
    public void testGetTeamByStudentAndNonExistentProject() {
        try {

            Team team = teams.getTeamByStudentNameAndProjectNumber("Laraine Smith", "P 4");

            assertEquals("team not found for student and project number", null, team);

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify getTeamByTeamNameAndProjectNumber when valid team and
     * project are provided
     **/

    public void testGetTeamByTeamNameAndProjectNumber() {
        try {

            Team team = teams.getTeamByTeamNameAndProjectNumber("Team 2", "P1");

            List<String> stringOfStudents = team.getStudentNames();
            boolean found = stringOfStudents.contains("Christine Schaeffer");

            assertEquals("Correct number of teams are loaded", true, found);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /** Test to verify getTeamByTeamNameAndProjectNumber when null team is input */
    public void testGetTeamByNullTeamAndProjectNumber() {
        try {

            Team team = teams.getTeamByTeamNameAndProjectNumber(null, "P1");

            assertEquals("team not found for student", null, team);

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify getTeamByTeamNameAndProjectNumber when null student is
     * input
     */
    public void testGetTeamByTeamNameAndNullProject() {
        try {

            Team team = teams.getTeamByTeamNameAndProjectNumber("Laraine Smith", null);

            assertEquals("team not found for student", null, team);

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify getTeamByTeamNameAndProjectNumber when an invalid team is
     * input
     */
    public void testGetTeamByNonExistentTeamAndProject() {
        try {

            Team team = teams.getTeamByTeamNameAndProjectNumber("Daniel Smith", "P1");

            assertEquals("team not found for student", null, null);

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /**
     * Test to verify getTeamByTeamNameAndProjectNumber when an invalid project
     * is input
     */
    public void testGetTeamByTeamAndNonExistentProject() {
        try {

            Team team = teams.getTeamByTeamNameAndProjectNumber("Laraine Smith", "P 4");

            assertEquals("team not found for student", null, null);

        } catch (Exception e) {
            fail("Exception");
        }
    }

    /** Test for refresh **/
    public void testRefresh() {
        try {
            // set the teams to null
            teams.setTeams(null);

            // team count is zero
            int numTeamsBeforeRefresh = 0;

            // refresh the teams from the datasource gradesDB
            teams.refresh();

            // get number of teams
            int numTeamsAfterRefresh = teams.getTeams().size();

            assertEquals("Number of teams changed after refresh", true,
                    (numTeamsAfterRefresh != numTeamsBeforeRefresh));
        } catch (Exception e) {
            fail("Exception");
        }
    }

}
