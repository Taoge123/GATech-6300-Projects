package gatech.cs6300.project1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AverageSentenceLengthTest {

    private AverageSentenceLength asl;
    private String fileDir;

    // Byte output stream to capture error messages.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream consoleOut = System.out;

    
    @Before
    public void setUp() throws Exception {
        System.setOut(consoleOut);
        asl = new AverageSentenceLength();
        fileDir = new String("test" + File.separator + "inputfiles" + File.separator);
    }

    @After
    public void tearDown() throws Exception {
        asl = null;
        fileDir = null;
    }
    
   

    @Test
    public void testComputeAverageSentenceLength1() {
        asl.setFile(new File(fileDir + "multi.txt"));
        assertEquals(6, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength2() {
        asl.setFile(new File(fileDir + "file.txt"));
        asl.setSentenceDelimiters("|%");
        assertEquals(1, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength3() {
        asl.setFile(new File(fileDir + "essay.txt"));
        assertEquals(10, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength4() {
        asl.setFile(new File(fileDir + "essay.txt"));
        asl.setMinWordLength(5);
        assertEquals(4, asl.computeAverageSentenceLength(), 0);
    }
    
    @Test
    public void testComputeAverageSentenceLength5() {
        asl.setFile(new File(fileDir + "numbers.txt"));
        asl.setSentenceDelimiters("/|");
        asl.setMinWordLength(1);
        assertEquals(5, asl.computeAverageSentenceLength(), 0);
    }

    /** ADDITIONAL TESTS */

    /** ----- POSITIVE SCENARIOS ---- */

    // Test to verify if the different delimiters specified.
    @Test
    public void verifyQuestionmarkDelimiters() {
        asl.setFile(new File(fileDir + "questionMarkDelimited.txt"));
        asl.setSentenceDelimiters("?");
        asl.setMinWordLength(4);
        assertEquals(2, asl.computeAverageSentenceLength(), 0);

    }

    @Test
    public void verifyColonDelimiters() {
        asl.setFile(new File(fileDir + "colonDelimited.txt"));
        asl.setSentenceDelimiters(":");
        asl.setMinWordLength(1);
        assertEquals(9, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void verifyExclamationDelimiters() {
        asl.setFile(new File(fileDir + "exclamationDelimited.txt"));
        asl.setSentenceDelimiters("!");
        asl.setMinWordLength(2);
        assertEquals(15, asl.computeAverageSentenceLength(), 0);

    }

    @Test
    public void verifySemicolonDelimiters() {
        asl.setFile(new File(fileDir + "semicolonDelimited.txt"));
        asl.setSentenceDelimiters(";");
        assertEquals(11, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void verifyPeriodDelimiters() {
        asl.setFile(new File(fileDir + "periodDelimited.txt"));
        asl.setSentenceDelimiters(".");
        asl.setMinWordLength(5);
        assertEquals(5, asl.computeAverageSentenceLength(), 0);

    }

    @Test
    public void verifyCombinationDelimiters() {

        asl.setFile(new File(fileDir + "combination.txt"));
        asl.setSentenceDelimiters(".?:;");
        asl.setMinWordLength(5);
        assertEquals( 3,asl.computeAverageSentenceLength(), 0);

    }

    /** ----- NEGATIVE SCENARIOS ---- */

    @Test
    public void verifyNoDelimitersPresent() {

        // Test when no input delimiter does not exist.
        asl.setFile(new File(fileDir + "noDelimiter.txt"));
        asl.setSentenceDelimiters(".");
        asl.setMinWordLength(50);
        assertEquals(0, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void verifyEmptyFile() {
        asl.setFile(new File(fileDir + "emptyFile.txt"));
        asl.setSentenceDelimiters(".");
        asl.setMinWordLength(50);
        assertEquals(0, asl.computeAverageSentenceLength(), 0);
    }

    // This is a test to verify rounding logic.
    @Test
    public void verifyRoundingMath() {
        // 10 lines, 19 words. 1.9 ~ 1

        asl.setFile(new File(fileDir + "roundingLogic.txt"));
        asl.setSentenceDelimiters(":");
        asl.setMinWordLength(1);
        assertEquals(1, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void verifyZeroWordCount() {
        // no words of length 900. expected output 0

        asl.setFile(new File(fileDir + "roundingLogic.txt"));
        asl.setSentenceDelimiters(":");
        asl.setMinWordLength(900);
        assertEquals(0, asl.computeAverageSentenceLength());
    }

    @Test
    public void verifyNegativeWordLength() {
        // Should throw an error.

        System.setOut(new PrintStream(outContent));
        asl.setFile(new File(fileDir + "roundingLogic.txt"));
        asl.setSentenceDelimiters(":");
        try{
        asl.setMinWordLength(-1);
        
        }catch (RuntimeException e) {
            assertEquals("Warning: Please specify a valid minimum word length.\n"
                    + "Example:  java -jar AverageSentenceLength.jar -l 5", outContent.toString().trim());
        }
    }

    /** ----- SYSTEM MESSAGE TESTS ---- */

    @Test
    public void verifyMissingDelimitersMessage() {

        System.setOut(new PrintStream(outContent));

        Main.main(new String[] { fileDir + "numbers.txt", "-d" });

        assertEquals("Warning: Please specify a valid set of delimiters such as \".?!:;/|%\"\n"
                + "Example:  java -jar AverageSentenceLength.jar -d \".?!:;\"", outContent.toString().trim());

    }

    @Test
    public void verifyInvalidDelimitersMessage() {

        System.setOut(new PrintStream(outContent));

        Main.main(new String[] { "-d", " ? ", fileDir + "numbers.txt" });

        assertEquals("Warning: Please specify a valid set of delimiters such as \".?!:;/|%\"\n"
            + "Example:  java -jar AverageSentenceLength.jar -d \".?!:;\""
                , outContent.toString().trim());

    }

    @Test
    public void verifyWordLengthMessage() {

        System.setOut(new PrintStream(outContent));

        Main.main(new String[] { "-l" });

        assertEquals("Warning: Please specify a valid minimum word length.\n"
                + "Example:  java -jar AverageSentenceLength.jar -l 5", outContent.toString().trim());

        
    }

    @Test
    public void verifyDecimalLengthMessage() {

        System.setOut(new PrintStream(outContent));

        Main.main(new String[] { "-l", "2.5" });

        assertEquals("Warning: Please specify a valid minimum word length.\n"
                + "Example:  java -jar AverageSentenceLength.jar -l 5", outContent.toString().trim());

        
    }

    @Test
    public void verifyNoInput() {

        System.setOut(new PrintStream(outContent));
        
        Main.main(new String[] { "" });

        assertEquals("Warning: File not found.\n"
                + "Please make sure that you entered the correct file name or directory.\n"
                + "If your file is named: 'file.txt'\n"
                + "and it is located in the directory: '/Users/username/Documents'\n" + "you should enter:\n"
                + "java -jar AverageSentenceLength.jar '/Users/username/Documents/file.txt'", outContent.toString().trim());

    }

    /** -- REQUIREMENT DOCUMENT SCENARIONS **/

    @Test
    public void requirementDocumentExample1() {

        asl.setFile(new File(fileDir + "requirementDocumentExample.txt"));
        assertEquals(4, asl.computeAverageSentenceLength(), 0);

    }

    @Test
    public void requirementDocumentExample2() {

        asl.setFile(new File(fileDir + "requirementDocumentExample.txt"));
        asl.setMinWordLength(1);
        assertEquals(5, asl.computeAverageSentenceLength(), 0);

        asl.setMinWordLength(2);
        assertEquals(4, asl.computeAverageSentenceLength(), 0);

        asl.setMinWordLength(3);
        assertEquals(4, asl.computeAverageSentenceLength(), 0);

        asl.setMinWordLength(4);
        assertEquals(2, asl.computeAverageSentenceLength(), 0);

        asl.setMinWordLength(5);
        assertEquals(0, asl.computeAverageSentenceLength(), 0);

    }

    @Test
    public void requirementDocumentExample3() {

        asl.setFile(new File(fileDir + "requirementDocumentExample.txt"));
        asl.setSentenceDelimiters(".");
        assertEquals(4, asl.computeAverageSentenceLength(), 0);

        asl.setSentenceDelimiters("!?");
        assertEquals(8, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void requirementDocumentExample4() {

        asl.setFile(new File(fileDir + "requirementDocumentExample.txt"));
        asl.setSentenceDelimiters("!?");
        asl.setMinWordLength(1);
        assertEquals(10, asl.computeAverageSentenceLength(), 0);

    }

}
