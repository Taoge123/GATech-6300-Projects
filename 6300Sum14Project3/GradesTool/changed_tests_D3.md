#Test Change Log

##GradesDBTest2.java

1. testGetStudentAverageContributionForProject() - Changed from == to within 0.005
2. testTeamAverageContributionsForProject() - Changed form == to within 0.001 and to String.equals()

##ProjectTest.java

- In the setUp method, `==` was used to compare two strings. Changed it to use `.equals()`

##AssignmentTest.java

1. testGetGradeForNullStudent() - Changed expected value to -1 from null.

##AssignmentsTest.java

1. testGetAssignments() - Changed expected value to a method call instead of a static value.

##TeamsTest.java

1. testGetTeamByStudent() - Change List to HashSet for consistency.
2. testGetTeamByNullStudent() - Change List to HashSet for consistency.
3. testGetTeamByNonExistentStudent() - Change List to HashSet for consistency.
4. refresh() - changed to initialize the correct length before refresh
 