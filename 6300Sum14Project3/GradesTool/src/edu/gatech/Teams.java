package edu.gatech;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Class to hold information about all teams in a class. The class contains a
 * constructor that takes a reference to a GradesDB instance as input and
 * constructs (and stores) the Team objects based on the information in that
 * instance. The class also provides a method that refreshes the list of teams.
 * 
 * @author vijay
 * 
 */
public class Teams implements TeamsInterface {

    private HashSet<Team> teams;
    private GradesDB db;

    /**
     * Constructor that takes a reference to a GradesDB instance as input and
     * constructs (and stores) the Team objects based on the information in that
     * instance.
     * 
     * @param gradesDb
     *            GradesDB instance datasource to be used
     */
    public Teams(GradesDB gradesDb) {
        this.db = gradesDb;
        this.refresh();
    }

    /**
     * Setter method to set the teams within a Teams object
     * 
     * @param teams
     *            Teams to be set
     */
    public void setTeams(HashSet<Team> teams) {

        this.teams = teams;
    }

    /**
     * Returns a list of teams present in the Grades DB instance.
     * 
     * @param teams
     *            Teams present in datasource.
     */
    public HashSet<Team> getTeams() {
        return this.teams;
    }

    /**
     * Returns a Team associated with a team name and a project number.
     * 
     * @param teamName
     * @param projectNumber
     * @return
     */
    public Team getTeamByTeamNameAndProjectNumber(String teamName, String projectNumber) {
        for (Team team : teams) {

            if (team.getTeamNumber().equals(teamName)
                    && team.getProjectNumber().equals(projectNumber)) {
                return team;
            }
        }
        return null;
    }

    /**
     * Returns a Team by the Student name and the project number
     * 
     * @param studentName
     *            Student name
     * @param projectNumber
     *            Project number
     * @return team having student and the project combination. null if not
     *         found
     */
    public Team getTeamByStudentNameAndProjectNumber(String studentName, String projectNumber) {
        for (Team team : teams) {

            if (team.getProjectNumber().equals(projectNumber)
                    && team.getStudentNames().contains(studentName)) {
                return team;
            }
        }
        return null;
    }

    /**
     * Returns a Team by the Student name and the project number
     * 
     * 
     * @param studentName
     *            Student name
     * @return list of teams having student. List of size zero if not found
     *         found
     */
    public HashSet<Team> getTeamsByStudentName(String studentName) {
        HashSet<Team> studentTeams = new HashSet<Team>();
        for (Team team : teams) {

            if (team.getStudentNames().contains(studentName)) {
                studentTeams.add(team);
            }
        }
        return studentTeams;
    }

    /**
     * Refreshes the teams list from datasource
     */
    public void refresh() {
        this.initWithGradesDB(this.db);
    }

    @Override
    public void initialize(File database) {
        GradesDB db_instance = new GradesDB(database.getAbsolutePath());
        this.initWithGradesDB(db_instance);
    }

    /**
     * Initialize the team object with data from the data sheet.
     * 
     * @param db
     */
    private void initWithGradesDB(GradesDB db) {
        this.teams = new HashSet<Team>();

        if (db != null) {
            if (db != null) {
                for (String projectName : db.getAllProjectNames()) {
                    List<String> teamNames = Arrays.asList(db.getTeamNamesForProject(projectName));

                    for (String teamName : teamNames) {

                        int teamGrade = db.getTeamGradeForProject(projectName, teamName);
                        List<String> studentNames = Arrays.asList(db.getTeamMemberNamesForProject(
                                projectName, teamName));

                        Map<String, Double> studentcontributions = db
                                .getTeamAverageContributionForProject(projectName, teamName);

                        Team team = new Team(teamName, projectName, teamGrade,
                                studentcontributions, studentNames);

                        teams.add(team);

                    }

                }
            }

        }

    }
}
