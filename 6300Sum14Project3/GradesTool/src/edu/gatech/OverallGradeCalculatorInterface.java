package edu.gatech;

import java.util.Map;

public interface OverallGradeCalculatorInterface {

    public void setFormula(String formula);

    public String getFormula();

    public double getStudentGrade(Student student);

    public Map<Student, Double> getAllGrades();

    public double getAverageGrade();

    public double getMedianGrade();
}
