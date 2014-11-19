package edu.gatech;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Assignments {

    private HashSet<Assignment> assignments;
    private GradesDB db;

    public Assignments(GradesDB db) {
        this.db = db;
        this.refresh();

    }

    // Returns a list of all the assignments.
    public HashSet<Assignment> getAssignments() {

        return assignments;
    }

    // Sets current list of assignments
    public void setAssignments(List<Assignment> assignmentList) {
        if (assignmentList != null) {
            this.assignments = new HashSet<Assignment>(assignmentList);
        } else {
            this.assignments = new HashSet<Assignment>();
        }
    }

    // Updates list of assignments to reflect any changes that happened in the
    // database.
    public void refresh() {
        assignments = new HashSet<Assignment>();
        if (db != null) {
            for (String assignmentName : db.getAllAssignmentNames()) {
                String description = db.getAssignmentDescriptionByName(assignmentName);
                int averageGrade = db.getAssignmentAverage(assignmentName);
                Map<String, Integer> studentGrades = new HashMap<String, Integer>();
                for (Student student : db.getStudents()) {
                    studentGrades.put(student.getName(),
                            db.getStudentGradeForAssignment(student.getName(), assignmentName));
                }
                Assignment assignment = new Assignment(assignmentName, description, averageGrade, studentGrades);
                assignments.add(assignment);
            }
        }
    }
}
