package edu.gatech;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Projects implements ProjectsInterface {

    private HashSet<Project> projects;
    private GradesDB db;

    public Projects(GradesDB db) {
        this.db = db;
        this.refresh();
    }

    public void refresh() {
        this.initWithGradesDB(this.db);
    }

    @Override
    public HashSet<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = new HashSet<Project>(projects);
    }

    public Project getProjectByName(String name) {

        for (Project project : projects) {
            System.out.println(project.getNumber());
            if (project.getNumber().equals(name)) {
                return project;
            }
        }
        return null;
    }

    @Override
    public void initialize(File database) {
        GradesDB db_instance = new GradesDB(database.getAbsolutePath());
        this.initWithGradesDB(db_instance);
    }

    private void initWithGradesDB(GradesDB db) {
        projects = new HashSet<Project>();
        if (db != null) {
            for (String projectName : db.getAllProjectNames()) {
                String description = db.getProjectDescriptionByName(projectName);
                List<String> teamNames = Arrays.asList(db.getTeamNamesForProject(projectName));
                int averageProjectGrade = db.getAverageGradeForProject(projectName);

                Project project = new Project(projectName, description, teamNames, averageProjectGrade);
                projects.add(project);
            }
        }

    }

}
