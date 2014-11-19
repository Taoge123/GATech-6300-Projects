package edu.gatech;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class GradesDB implements OverallGradeCalculatorInterface {

    private Workbook workbook;
    private Sheet details;
    private Sheet data;
    private Sheet attendance;
    private Sheet grades;
    private Sheet p1teams;
    private Sheet p2teams;
    private Sheet p3teams;
    private String formula = null;

    // Accepts a file location for the workbook and initializes the various class variables used throughout the class.
    public GradesDB(String location) {
        InputStream file;

        try {
            file = new FileInputStream(location);
            try {
                this.workbook = WorkbookFactory.create(file);
                this.details = workbook.getSheet("Details");
                this.data = workbook.getSheet("Data");
                this.attendance = workbook.getSheet("Attendance");
                this.grades = workbook.getSheet("Grades");
                this.p1teams = workbook.getSheet("P1 Teams");
                this.p2teams = workbook.getSheet("P2 Teams");
                this.p3teams = workbook.getSheet("P3 Teams");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Returns the number of students.
    public int getNumStudents() {
        int result = details.getLastRowNum();
        return result;
    }

    // Returns the attendance percentage of a student.
    public int getAttendanceForStudent(String name) {
        int results = 0;
        int nameColumn = 0;
        for (Row r : attendance) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c != null) {
                    if (c.getStringCellValue().equals(name)) {
                        Cell c1 = r.getCell(1);
                        results = (int) Math.floor(c1.getNumericCellValue() * 100);
                    }
                }
            }
        }
        return results;
    }

    // Returns the number of assignments in the class.
    public int getNumAssignments() {
        int results = 0;
        int assignmentColumn = 3;
        for (Row r : data) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(assignmentColumn);
                if (c != null && c.getStringCellValue().trim().length() > 0) {
                    results++;
                }
            }
        }
        return results;
    }

    // Returns the number of projects in the class.
    public int getNumProjects() {
        int results = 0;
        int projectColumn = 0;
        for (Row r : data) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(projectColumn);
                if (c != null && c.getStringCellValue().trim().length() > 0) {
                    results++;
                }
            }
        }
        return results;
    }

    // Returns a hash of students.
    public HashSet<Student> getStudents() {
        HashSet<Student> students = new HashSet<Student>();
        for (Row r : details) {
            if (r.getRowNum() > 0) {
                students.add(Student.loadStudentFromRow(r));
            }
        }
        return students;
    }

    // Returns a student based off the name argument.
    public Student getStudentByName(String name) {
        Student s = new Student();
        for (Row r : details) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(0);
                if (c != null) {
                    if (c.getStringCellValue().equals(name)) {
                        return Student.loadStudentFromRow(r);
                    }
                }
            }
        }
        return s;
    }

    // Returns a student based off Gtid.
    public Student getStudentByID(String gtid) {
        Student s = new Student();
        for (Row r : details) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(1);
                if (c != null) {
                    int value = (int) c.getNumericCellValue();
                    String sValue = String.valueOf(value);
                    if (sValue.equals(gtid)) {
                        return Student.loadStudentFromRow(r);
                    }
                }
            }
        }
        return s;
    }

    public String[] getAllProjectNames() {
        int nameColumn = 0;
        ArrayList<String> projectNames = new ArrayList<String>();
        for (Row r : data) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c != null && !c.getStringCellValue().trim().equals("")) {
                    projectNames.add(c.getStringCellValue());
                }
            }
        }
        if (projectNames.isEmpty())
            return null;

        String[] projects = new String[projectNames.size()];
        for (String projectName : projectNames) {
            projects[projectNames.indexOf(projectName)] = projectName;
        }

        return projects;
    }

    public String getProjectDescriptionByName(String projectName) {
        int nameColumn = 0;
        for (Row r : data) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c != null && c.getStringCellValue().trim().equals(projectName)) {
                    return r.getCell(nameColumn + 1).getStringCellValue();
                }
            }
        }

        return null;

    }

    public String[] getAllAssignmentNames() {
        int nameColumn = 3;
        ArrayList<String> assignmentNames = new ArrayList<String>();
        for (Row r : data) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c != null && !c.getStringCellValue().trim().equals("")) {
                    assignmentNames.add(c.getStringCellValue());
                }
            }
        }
        if (assignmentNames.isEmpty())
            return null;

        String[] assignments = new String[assignmentNames.size()];
        for (String assignmentName : assignmentNames) {
            assignments[assignmentNames.indexOf(assignmentName)] = assignmentName;
        }

        return assignments;
    }

    public String getAssignmentDescriptionByName(String assignmentName) {
        int nameColumn = 3;
        for (Row r : data) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c != null && c.getStringCellValue().trim().equals(assignmentName)) {
                    return r.getCell(nameColumn + 1).getStringCellValue();
                }
            }
        }

        return null;
    }

    public int getStudentGradeForAssignment(String studentName, String assignmentName) {
        int nameColumn = 0;
        int columnNumber = 0;
        for (Row r : grades) {
            if (r.getRowNum() == 0) {
                Cell c = r.getCell(columnNumber);
                while (c != null) {
                    if (c.getStringCellValue().equals(assignmentName))
                        break;
                    columnNumber += 1;
                    c = r.getCell(columnNumber);
                }
                if (c == null)
                    return -1;
            } else {
                Cell c = r.getCell(nameColumn);
                if (c != null && c.getStringCellValue().trim().equals(studentName)) {
                    int grade = (int) r.getCell(columnNumber).getNumericCellValue();
                    return grade;
                }
            }
        }

        return -1;
    }

    public int getStudentAvgGradeForAllAssignments(String studentName) {
        int nameColumn = 0;
        int columnNumber = 1;
        for (Row r : grades) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c != null && c.getStringCellValue().trim().equals(studentName)) {
                    int grade = (int) Math.round(r.getCell(columnNumber).getNumericCellValue());
                    return grade;
                }
            }
        }

        return -1;
    }

    public int getAssignmentAverage(String assignmentName) {
        int columnNumber = 0;
        ArrayList<Integer> assignmentGrades = new ArrayList<Integer>();
        for (Row r : grades) {
            if (r.getRowNum() == 0) {
                Cell c = r.getCell(columnNumber);
                while (c != null) {
                    if (c.getStringCellValue().equals(assignmentName))
                        break;
                    columnNumber += 1;
                    c = r.getCell(columnNumber);
                }
                if (c == null)
                    return -1;
            } else {
                Cell c = r.getCell(columnNumber);
                if (c != null)
                    assignmentGrades.add((int) c.getNumericCellValue());
            }
        }

        if (assignmentGrades.isEmpty())
            return -1;

        double average = 0;

        for (int grade : assignmentGrades) {
            average += grade;
        }

        int avg = (int) Math.round(average / assignmentGrades.size());
        return avg;
    }

    public int[] getStudentGradesForAllAssignments(String studentName) {
        int nameColumn = 0;
        int columnNumber = 2;
        ArrayList<Integer> assignmentGrades = new ArrayList<Integer>();
        for (Row r : grades) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c != null && c.getStringCellValue().trim().equals(studentName)) {
                    Cell gc = r.getCell(columnNumber);
                    while (gc != null) {
                        assignmentGrades.add((int) gc.getNumericCellValue());
                        columnNumber += 1;
                        gc = r.getCell(columnNumber);
                    }
                }
            }
        }

        if (assignmentGrades.isEmpty())
            return null;

        int[] assignments = new int[assignmentGrades.size()];
        for (int grade : assignmentGrades) {
            assignments[assignmentGrades.indexOf(grade)] = grade;
        }

        return assignments;
    }

    public String[] getTeamNamesForProject(String projectName) {
        Sheet teamProject = workbook.getSheet(projectName + " Teams");

        if (teamProject == null)
            return null;

        int nameColumn = 0;
        ArrayList<String> teams = new ArrayList<String>();
        for (Row r : teamProject) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c != null && !c.getStringCellValue().trim().equals("")) {
                    teams.add(c.getStringCellValue());
                }
            }
        }

        String[] names = new String[teams.size()];
        for (String team : teams) {
            names[teams.indexOf(team)] = team;
        }
        return names;
    }

    public String getTeamNameForProject(String projectName, String studentName) {
        Sheet teamProject = workbook.getSheet(projectName + " Teams");
        if (teamProject == null)
            return null;

        int col = 1;
        int row = 1;
        Cell c = teamProject.getRow(row).getCell(col);
        while (c != null) {
            if (c.getStringCellValue().equals(studentName)) {
                return teamProject.getRow(row).getCell(0).getStringCellValue();
            }
            if (c == null || c.getStringCellValue().trim().equals(""))
                return null;

            while (c != null && !c.getStringCellValue().trim().equals("")) {
                if (c.getStringCellValue().equals(studentName)) {
                    return teamProject.getRow(row).getCell(0).getStringCellValue();
                }
                col += 1;
                c = teamProject.getRow(row).getCell(col);
            }

            row += 1;
            col = 1;
            c = teamProject.getRow(row).getCell(col);
        }
        return null;
    }

    public String[] getTeamMemberNamesForProject(String projectName, String teamName) {
        Sheet teamProject = workbook.getSheet(projectName + " Teams");

        if (teamProject == null)
            return null;

        int nameColumn = 0;
        int col = 1;

        ArrayList<String> members = new ArrayList<String>();
        for (Row r : teamProject) {
            if (r.getRowNum() > 0) {
                Cell c = r.getCell(nameColumn);
                if (c.getStringCellValue().equals(teamName)) {
                    Cell mc = r.getCell(col);
                    while (mc != null && !mc.getStringCellValue().trim().equals("")) {
                        members.add(mc.getStringCellValue());
                        col += 1;
                        mc = r.getCell(col);
                    }
                }
            }
        }

        if (members.isEmpty())
            return null;

        String[] team = new String[members.size()];
        for (String member : members) {
            team[members.indexOf(member)] = member;
        }
        return team;

    }

    public int getTeamGradeForProject(String projectName, String teamName) {
        Sheet teamGrade = workbook.getSheet(projectName + " Grades");

        if (teamGrade == null)
            return -1;

        int nameColumn = 0;
        int columnNumber = 0;
        for (Row r : teamGrade) {
            if (r.getRowNum() == 0) {
                Cell c = r.getCell(columnNumber);
                while (c != null) {
                    if (c.getStringCellValue().equals(teamName))
                        break;
                    columnNumber += 1;
                    c = r.getCell(columnNumber);
                }
            } else {

                Cell c = r.getCell(nameColumn);
                if (c.getStringCellValue().equals("TOTAL:"))
                    return (int) Math.round(r.getCell(columnNumber).getNumericCellValue());
            }
        }

        return -1;
    }

    public int getMaxPointsForProject(String projectName) {
        Sheet teamGrade = workbook.getSheet(projectName + " Grades");

        if (teamGrade == null)
            return -1;

        int nameColumn = 0;
        int columnNumber = 1;
        for (Row r : teamGrade) {
            Cell c = r.getCell(nameColumn);
            if (c.getStringCellValue().equals("TOTAL:"))
                return (int) Math.round(r.getCell(columnNumber).getNumericCellValue());
        }

        return -1;
    }

    public int getAverageGradeForProject(String projectName) {
        Sheet teamGrade = workbook.getSheet(projectName + " Grades");

        if (teamGrade == null)
            return -1;

        ArrayList<Integer> grades = new ArrayList<Integer>();
        int nameColumn = 0;
        int columnNumber = 2;
        for (Row r : teamGrade) {
            Cell c = r.getCell(nameColumn);
            if (c.getStringCellValue().equals("TOTAL:")) {
                Cell gc = r.getCell(columnNumber);

                while (gc != null && gc.getNumericCellValue() != 0) {
                    grades.add((int) gc.getNumericCellValue());
                    columnNumber += 1;
                    gc = r.getCell(columnNumber);
                }

            }

        }

        if (grades.isEmpty())
            return -1;

        double average = 0;
        int avg = 0;

        for (int grade : grades) {
            average += grade;
        }

        avg = (int) Math.round(average / grades.size());
        return avg;
    }

    public double getStudentAverageContributionForProject(String projectName, String StudentName) {
        Sheet contri = workbook.getSheet(projectName + " Contri");

        if (contri == null)
            return -1.0;

        int nameColumn = 1;
        for (Row r : contri) {
            Cell c = r.getCell(nameColumn);
            if (c.getStringCellValue().equals(StudentName)) {
                double contribution = r.getCell(nameColumn + 1).getNumericCellValue();
                return contribution;
            }
        }

        return -1.0;
    }

    public HashMap<String, Double> getTeamAverageContributionForProject(String projectName, String teamName) {
        Sheet contri = workbook.getSheet(projectName + " Contri");

        if (contri == null)
            return null;

        int teamColumn = 0;
        int nameColumn = 1;
        int avgColumn = 2;

        boolean foundTeam = false;
        HashMap<String, Double> studentContributions = new HashMap<String, Double>();

        for (Row r : contri) {
            Cell c = r.getCell(teamColumn);
            Cell sc = r.getCell(nameColumn);
            Cell cc = r.getCell(avgColumn);
            if (foundTeam) {
                if (sc != null && !sc.getStringCellValue().trim().equals("")) {
                    double avg = cc.getNumericCellValue();
                    avg = avg * 100;
                    avg = Math.round(avg);
                    avg = avg / 100;
                    studentContributions.put(sc.getStringCellValue(), avg);
                } else {
                    foundTeam = false;
                }
            }

            if (c.getStringCellValue().equals(teamName))
                foundTeam = true;
        }

        if (studentContributions.isEmpty())
            return null;

        return studentContributions;
    }

    @Override
    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public String getFormula() {
        return this.formula;
    }

    @Override
    public double getStudentGrade(Student student) {
        if (this.formula == null)
            return -1.0;

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String currentFormula = this.formula;
        String name = student.getName();
        if (name == null)
            return -1.0;

        double AS = getStudentAvgGradeForAllAssignments(name);
        currentFormula = currentFormula.replace("AS", String.valueOf(AS));

        double AT = getAttendanceForStudent(name);
        currentFormula = currentFormula.replace("AT", String.valueOf(AT));

        for (int i = 1; i <= getNumProjects(); i++) {
            String team = getTeamNameForProject("P" + i, name);
            double P = getTeamGradeForProject("P" + i, team);
            currentFormula = currentFormula.replace("PR" + i, String.valueOf(P));
            double I = getStudentAverageContributionForProject("P" + i, name);
            currentFormula = currentFormula.replace("IC" + i, String.valueOf(I));
        }

        try {
            double value = (Double) engine.eval(currentFormula);
            return value;
        } catch (ScriptException e) {
            return -1.0;
        }
    }

    @Override
    public Map<Student, Double> getAllGrades() {
        if (this.formula == null)
            return null;

        HashSet<Student> students = getStudents();
        Map<Student, Double> studentGrades = new HashMap<Student, Double>();
        for (Student student : students) {
            double grade = getStudentGrade(student);
            studentGrades.put(student, grade);
        }
        if (studentGrades.size() <= 0)
            return null;

        return studentGrades;
    }

    @Override
    public double getAverageGrade() {
        if (this.formula == null)
            return -1.0;

        Map<Student, Double> grades = getAllGrades();
        if (grades.size() <= 0)
            return -1.0;

        double average = 0.0;

        for (Entry<Student, Double> entry : grades.entrySet()) {
            average += entry.getValue();
        }
        average = average / grades.size();

        return average;
    }

    @Override
    public double getMedianGrade() {
        if (this.formula == null)
            return -1.0;

        Map<Student, Double> grades = getAllGrades();
        Object[] gradesArray = grades.values().toArray();
        Arrays.sort(gradesArray);

        int n = gradesArray.length;
        if (n <= 0) {
            return -1.0;
        }
        double median = 0.0;

        if (n % 2 == 0) {
            median = (((Double) (gradesArray[(n - 1) / 2])) + ((Double) (gradesArray[n / 2]))) / 2.0;
        } else {
            median = ((Double) gradesArray[n / 2]);
        }
        return median;
    }

}
