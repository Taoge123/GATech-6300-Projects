package edu.gatech;

import java.util.List;

public class Project implements Comparable<Project>{
    private String number;
    private String description;
    private List<String> teamNames;
    private int averageProjectGrade;

    public Project(String number, String description, List<String> teamNames, int averageProjectGrade) {
        this.number = number;
        this.description = description;
        this.teamNames = teamNames;
        this.averageProjectGrade = averageProjectGrade;

    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAverageProjectGrade() {
        return this.averageProjectGrade;
    }

    public void setAverageProjectGrade(int grade) {
        this.averageProjectGrade = grade;
    }

    public List<String> getTeamNames() {
        return this.teamNames;
    }
    
    @Override
    public int compareTo(Project project) {
        
        return this.getNumber().compareTo(((Project) project).getNumber());
    }
}
