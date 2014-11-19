package edu.gatech;

import java.util.Map;

public class Assignment implements Comparable<Assignment>{
    private String assignmentName;
    private String description;
    private int averageGrade;
    private Map<String, Integer> studentGrades;

    public Assignment(String assignmentName, String description, int averageGrade, Map<String, Integer> studentGrades) {
        this.assignmentName = assignmentName;
        this.description = description;
        this.averageGrade = averageGrade;
        this.studentGrades = studentGrades;
    }

    // Returns grade for the current assignment for a given student.
    public int getGradeForStudent(String studentName) {
        if (this.studentGrades.containsKey(studentName)) {
            return this.studentGrades.get(studentName);
        } else {
            return -1;
        }
    }

    // Returns the average grade on the current assignment.
    public int getAverageGrade() {
        return this.averageGrade;
    }

    public void setAverageGrade(int grade) {
        this.averageGrade = grade;
    }

    // Returns the Assignment name.
    public String getAssignmentName() {
        return this.assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    // Returns Assignment description.
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Assignment assignment) {
        
        return this.getAssignmentName().compareTo(((Assignment) assignment).getAssignmentName());
    }

}
