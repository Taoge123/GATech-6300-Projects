package edu.gatech;

import java.io.File;
import java.util.HashSet;

public interface TeamsInterface {

    public void initialize(File database);

    public HashSet<Team> getTeams();
}
