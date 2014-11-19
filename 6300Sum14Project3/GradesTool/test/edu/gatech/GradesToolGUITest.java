package edu.gatech;

import java.nio.file.Files;
import java.nio.file.Paths;

import junit.framework.TestCase;

public class GradesToolGUITest extends TestCase {

    private GradesToolGUI gui;

    @Override
    protected void setUp() throws Exception {
        gui = new GradesToolGUI(Constants.GRADES_DB);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSaveToText() {
        try {
            Student testStudent = Student.getStudentByName("Josepha Jube");
            gui.saveToText(testStudent.getName(), testStudent.getTextOutput(), testStudent.getName() + ".txt");
            System.out.println(testStudent.getName().replace(" ", ""));
            String resultString = new String(Files.readAllBytes(Paths.get(testStudent.getName().replace(" ", "")
                    + ".txt")));
            String expectedString = new String(Files.readAllBytes(Paths.get(Constants.CORRECT_STUDENT_FILE)));
            System.out.println(resultString);
            System.out.println(expectedString);
            StringBuffer resultbuffer = new StringBuffer(resultString);
            assertTrue(expectedString.contentEquals(resultbuffer));
        } catch (Exception e) {
            fail("Exception");
        }
    }
}
