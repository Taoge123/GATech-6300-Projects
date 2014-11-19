package edu.gatech;

import java.util.Map;

import junit.framework.TestCase;

public class TeamTest extends TestCase {
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

    /** Test for get Student Contribution by Student name **/

    public void testStudentContributionByStudent() {
        try {
            Team team1 = null;
            for (Team team : teams.getTeams()) {

                if (team.getTeamNumber().equals("Team 1") && team.getProjectNumber().equals("P1")) {
                    team1 = team;
                    break;
                }
            }

            assertEquals("Average Project grade calculated.", 9.25,
                    team1.getStudentContributionByName("Kym Hiles"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /** Test for get Student Contribution by Null Student name **/

    public void testStudentContributionByNullStudent() {
        try {

            Team team1 = null;
            for (Team team : teams.getTeams()) {

                if (team.getTeamNumber().equals("Team 1")) {
                    team1 = team;
                    break;
                }
            }

            assertEquals("Average Project grade calculated.", true,
                    team1.getStudentContributionByName(null) < 0);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    /** Test for get student contributions **/

    public void testStudentContributions() {
        try {
            teams = new Teams(gradesDb);

            Team team1 = null;
            for (Team team : teams.getTeams()) {

                if (team.getTeamNumber().equals("Team 1") && team.getProjectNumber().equals("P1")) {
                    team1 = team;
                    break;
                }
            }

            Map<String, Double> contributions = team1.getStudentContributions();

            assertEquals("Student contribution loaded.", 9.25, contributions.get("Freddie Catlay"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

}
