package edu.gatech;

import java.util.HashSet;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Row;

public class Student {

    private String name;
    private String gtid;
    private String email;
    private int c;
    private int cpp;
    private int java;
    private String csJobExperience;
    private GradesDB db = null;

    public Student(String name, String gtid, String email, int c, int cpp, int java, String csJobExperience) {
        this.name = name;
        this.gtid = gtid;
        this.email = email;
        this.c = c;
        this.cpp = cpp;
        this.java = java;
        this.csJobExperience = csJobExperience;
        db = new GradesDB(Constants.GRADES_DB);
    }

    public Student() {
    }

    public static Student loadStudentFromRow(Row row) {
        String name = row.getCell(0).getStringCellValue();
        String gtid = String.valueOf((int) row.getCell(1).getNumericCellValue());
        String email = row.getCell(2).getStringCellValue();
        int c = (int) row.getCell(3).getNumericCellValue();
        int cpp = (int) row.getCell(4).getNumericCellValue();
        int java = (int) row.getCell(5).getNumericCellValue();
        String experience = row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue();

        return new Student(name, gtid, email, c, cpp, java, experience);

    }

    public HashSet<Assignment> getAssignments() {
        Assignments assignments = new Assignments(db);
        return assignments.getAssignments();
    }
    
    public HashSet<Team> getTeams() {
    	Teams teams = new Teams(db);
    	return (HashSet<Team>) teams.getTeamsByStudentName(this.name);
    }
    
    public HashSet<Project> getProjects() {
    	Projects projects = new Projects(db);
    	return projects.getProjects();
    }

    public int getAttendance() {
        return db.getAttendanceForStudent(name);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGtid() {
        return gtid;
    }

    public int getC() {
        return c;
    }

    public int getCpp() {
        return cpp;
    }

    public int getJava() {
        return java;
    }

    public String getCSJobExperience() {
        return csJobExperience;
    }

    public String getTextOutput() {

        StringBuilder outputString = new StringBuilder();
        outputString.append("Student Information" + "\n");
        outputString.append("      Name: " + getName() + "\n");
        outputString.append("      Email: " + getEmail() + "\n");
        outputString.append("      GT ID: " + getGtid() + "\n\n");
        outputString.append("Programming Experience" + "\n");
        outputString.append("      C: " + getC() + " year(s)" + "\n");
        outputString.append("      C++: " + getCpp() + " year(s)" + "\n");
        outputString.append("      Java: " + getJava() + " year(s)" + "\n");
        outputString.append("      CS Job Experience: " + getCSJobExperience() + "\n\n");
        outputString.append("Class Information" + "\n");
        outputString.append("      Attendance: " + getAttendance() + "\n\n");
        outputString.append("Assignment Information: \n");
        HashSet<Assignment> assignments = getAssignments();
        TreeSet<Assignment> sortedAssignments = new TreeSet<Assignment>(assignments);
        for (Assignment assignment : sortedAssignments) {
            outputString.append("      " +assignment.getAssignmentName() + "\n" + "      Student Grade: "
                    + assignment.getGradeForStudent(getName()) + "% (avg: " + assignment.getAverageGrade() + "%)" + "\n"
                    + "      Description: " + assignment.getDescription() + "\n\n");
        }
        outputString.append("Project Information:" + "\n");
        HashSet<Team> teams = getTeams();
        System.out.println(teams);
        TreeSet<Team> sortedTeams = new TreeSet<Team>(teams);
        for (Team team : sortedTeams) {
        	if(team.getStudentContributionByName(getName()) != -1) {
        	outputString.append("      Project:" + team.getProjectNumber()  + "\n" + "      "
        			+ team.getTeamNumber() + "\n" + "      Team Grade: " + team.getTeamGrade() + "%\n"
        			+ "      Student Contribution: " + team.getStudentContributionByName(getName()) + "\n\n");
        	}		
        }

        return outputString.toString();
    }

    public static Student getStudentByName(String studentName) {
        GradesDB db = new GradesDB(Constants.GRADES_DB);
        return db.getStudentByName(studentName);
    }

    @Override
    public String toString() {
        return name;
    }

}
