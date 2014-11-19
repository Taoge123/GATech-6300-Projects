package edu.gatech;

import java.util.List;
import java.util.Map;

/**
 * Class to hold information about a particular team, including team number,
 * list of student names, team grade, and list of average contribution ratings.
 * 
 * @author vijay
 * 
 */
public class Team implements Comparable<Team>{

    private String teamNumber;
    private String projectNumber;
    private int teamGrade;
    private Map<String, Double> studentcontributions;
    private List<String> studentNames;

    /** Returns the Team number **/
    public String getTeamNumber() {
        return this.teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    /** Returns the Project number associated with the team **/
    public String getProjectNumber() {
        return this.projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    /** Returns the team grade associated with the team **/
    public int getTeamGrade() {
        return this.teamGrade;
    }

    public void setTeamGrade(int teamGrade) {
        this.teamGrade = teamGrade;
    }

    /**
     * Returns a map of student names and the contributions of students within
     * the team
     **/
    public Map<String, Double> getStudentContributions() {
        return studentcontributions;
    }

    public void setStudentcontributions(Map<String, Double> studentcontributions) {
        this.studentcontributions = studentcontributions;
    }

    /**
     * Returns a list of students within the team
     **/
    public List<String> getStudentNames() {
        return this.studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }

    /**
     * Returns the students contribution for a student within the team. Returns
     * negative If student is not found
     **/
    public Double getStudentContributionByName(String studentName) {

        Double studentContribution = -1.0;

        if (studentName != null && studentcontributions.containsKey(studentName)) {
            studentContribution = studentcontributions.get(studentName);
        }

        return studentContribution;
    }

    public Team(String teamNumber, String projectNumber, int teamGrade,
            Map<String, Double> studentcontributions, List<String> studentNames) {
        super();
        this.teamNumber = teamNumber;
        this.projectNumber = projectNumber;
        this.teamGrade = teamGrade;
        this.studentcontributions = studentcontributions;
        this.studentNames = studentNames;
    }

    @Override
    public int compareTo(Team team) {
        return this.getProjectNumber().compareTo(((Team) team).getProjectNumber());
    }
}
