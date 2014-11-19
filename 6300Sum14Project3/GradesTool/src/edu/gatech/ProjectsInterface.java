package edu.gatech;

import java.io.File;
import java.util.HashSet;

public interface ProjectsInterface {

    public void initialize(File database);

    public HashSet<Project> getProjects();
}
