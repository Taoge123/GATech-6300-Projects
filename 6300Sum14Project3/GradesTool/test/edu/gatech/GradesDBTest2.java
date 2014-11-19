package edu.gatech;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

public class GradesDBTest2 extends TestCase {
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

    public void testGetAllProjectNames() {
        try {
            String[] projectNames = db.getAllProjectNames();
            boolean match = true;
            for (String projectName : projectNames) {
                if (!projectName.equals("P1") && !projectName.equals("P2") && !projectName.equals("P3")) {
                    match = false;
                }
            }
            assertTrue(match);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetProjectDescriptionFromProjectName() {
        try {
            String description = db.getProjectDescriptionByName("P1");
            assertTrue(description.equals("WordCount in Java"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetProjectDescriptionFromProjectNameNull() {
        try {
            String description = db.getProjectDescriptionByName("P4");
            assertTrue(description == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAssignmentNames() {
        try {
            String[] assignmentNames = db.getAllAssignmentNames();
            boolean match = true;
            for (String assignmentName : assignmentNames) {
                if (!assignmentName.equals("Assignment 1") && !assignmentName.equals("Assignment 2")
                        && !assignmentName.equals("Assignment 3")) {
                    match = false;
                }
            }
            assertTrue(match);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAssignementDescriptionByName() {
        try {
            String description = db.getAssignmentDescriptionByName("Assignment 3");
            assertTrue(description.equals("junit and coverage"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAssignementDescriptionByNameNull() {
        try {
            String description = db.getAssignmentDescriptionByName("Assignment 30");
            assertTrue(description == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentGradeForAssignment() {
        try {
            int grade = db.getStudentGradeForAssignment("Kym Hiles", "Assignment 2");
            assertTrue(grade == 90);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentGradeForAssignmentNull1() {
        try {
            int grade = db.getStudentGradeForAssignment("Kyn Hiles", "Assignment 2");
            assertTrue(grade == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentGradeForAssignmentNull2() {
        try {
            int grade = db.getStudentGradeForAssignment("Kym Hiles", "Assignment 20");
            assertTrue(grade == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentAvgGradeForAllAssignments() {
        try {
            int grade = db.getStudentAvgGradeForAllAssignments("Kym Hiles");
            assertTrue(grade == 92);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentAvgGradeForAllAssignmentsNull() {
        try {
            int grade = db.getStudentAvgGradeForAllAssignments("Kym Hil");
            assertTrue(grade == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAllStudentGradesForAllAssignments() {
        try {
            int[] grades = db.getStudentGradesForAllAssignments("Kym Hiles");
            int[] correct = { 100, 90, 85 };
            assertTrue(Arrays.equals(grades, correct));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAllStudentGradesForAllAssignmentsNull() {
        try {
            int[] grades = db.getStudentGradesForAllAssignments("Kyn Hiles");
            assertTrue(grades == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAssignmentAverage() {
        try {
            int average = db.getAssignmentAverage("Assignment 3");
            // Assumes Math.round applied to average
            assertTrue(average == 77);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAssignmentAverageNull() {
        try {
            int average = db.getAssignmentAverage("Assignment 30");
            // Assumes Math.round applied to average
            assertTrue(average == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetTeamNamesForProject() {
        try {
            String[] teamNames = db.getTeamNamesForProject("P2");
            String[] correct = { "Team 1", "Team 2", "Team 3" };
            assertTrue(Arrays.equals(teamNames, correct));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetTeamNamesForProjectNull() {
        try {
            String[] teamNames = db.getTeamNamesForProject("P20");
            assertTrue(teamNames == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentTeamNameForProject() {
        try {
            String teamName = db.getTeamNameForProject("P3", "Sheree Gadow");
            assertTrue(teamName.equals("Team 3"));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentTeamNameForProjectNull1() {
        try {
            String teamName = db.getTeamNameForProject("P30", "Sheree Gadow");
            assertTrue(teamName == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentTeamNameForProjectNull2() {
        try {
            String teamName = db.getTeamNameForProject("P3", "Sheree Gadowasdf");
            assertTrue(teamName == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetTeamMemberNamesForProject() {
        try {
            String[] teamMemberNames = db.getTeamMemberNamesForProject("P3", "Team 2");
            String[] correct = { "Josepha Jube", "Shevon Wise", "Christine Schaeffer", "Rastus Kight", "Kym Hiles" };
            assertTrue(Arrays.equals(teamMemberNames, correct));
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetTeamMemberNamesForProjectNull1() {
        try {
            String[] teamMemberNames = db.getTeamMemberNamesForProject("P30", "Team 2");
            assertTrue(teamMemberNames == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetTeamMemberNamesForProjectNull2() {
        try {
            String[] teamMemberNames = db.getTeamMemberNamesForProject("P3", "Team 20");
            assertTrue(teamMemberNames == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetTeamGradeForProject() {
        try {
            int grade = db.getTeamGradeForProject("P3", "Team 2");
            assertTrue(grade == 96);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetTeamGradeForProjectNull1() {
        try {
            int grade = db.getTeamGradeForProject("Project 30", "Team 2");
            assertTrue(grade == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetTeamGradeForProjectNull2() {
        try {
            int grade = db.getTeamGradeForProject("Project 3", "Team 20");
            assertTrue(grade == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetMaxPointsForProject() {
        try {
            int grade = db.getMaxPointsForProject("P2");
            assertTrue(grade == 100);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetMaxPointsForProjectNull() {
        try {
            int grade = db.getMaxPointsForProject("P20");
            assertTrue(grade == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAverageGradeForProject() {
        try {
            int grade = db.getAverageGradeForProject("P1");
            assertTrue(grade == 93);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetAverageGradeForProjectNull() {
        try {
            int grade = db.getAverageGradeForProject("P10");
            assertTrue(grade == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // Double to double
    // Make sure the difference is less than tolerance
    public void testGetStudentAverageContributionForProject() {
        try {
            double contribution = db.getStudentAverageContributionForProject("P2", "Laraine Smith");
            // assertTrue(contribution == 9.33);
            assertTrue(Math.abs(contribution - 9.33) < 0.005);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentAverageContributionForProjectNull1() {
        try {
            Double contribution = db.getStudentAverageContributionForProject("P20", "Laraine Smith");
            assertTrue(contribution == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetStudentAverageContributionForProjectNull2() {
        try {
            Double contribution = db.getStudentAverageContributionForProject("P2", "Laraine Smithasdf");
            assertTrue(contribution == -1);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    // changed to doubles and check for within tolerance
    // added string.equals instead of ==
    public void testTeamAverageContributionsForProject() {
        try {
            HashMap<String, Double> contributions = db.getTeamAverageContributionForProject("P2", "Team 1");
            boolean match = true;

            Iterator it = contributions.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                if (!(((String) pairs.getKey()).equals("Freddie Catlay") && Math.abs(((Double) pairs.getValue()) - 9.0) < 1e-2)
                        && !(((String) pairs.getKey()).equals("Laraine Smith") && Math
                                .abs(((Double) pairs.getValue()) - 9.33) < 1e-2)
                        && !(((String) pairs.getKey()).equals("Sheree Gadow") && Math
                                .abs(((Double) pairs.getValue()) - 8.67) < 1e-2)
                        && !(((String) pairs.getKey()).equals("Rastus Kight") && Math
                                .abs(((Double) pairs.getValue()) - 9.33) < 1e-2)) {
                    match = false;
                    System.out.println("Map: " + ((String) pairs.getKey()).equals("Freddie Catlay") + " "
                            + (((Double) (pairs.getValue()) - 9.00) < 1e-2));
                }
                it.remove();
            }

            assertTrue(match);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testTeamAverageContributionsForProjectNull1() {
        try {
            HashMap<String, Double> contributions = db.getTeamAverageContributionForProject("P20", "Team 1");
            assertTrue(contributions == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testTeamAverageContributionsForProjectNull2() {
        try {
            HashMap<String, Double> contributions = db.getTeamAverageContributionForProject("P2", "Team 10");
            assertTrue(contributions == null);
        } catch (Exception e) {
            fail("Exception");
        }
    }

}
