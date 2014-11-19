package gatech.cs6300.project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AverageSentenceLength {

    private File file;
    private int minWordLength = 3;
    private String delimiter = ".?!;:";

    protected static final String WORD_LENGTH_ERROR = "Warning: Please specify a valid minimum word length.\n"
            + "Example:  java -jar AverageSentenceLength.jar -l 5";

    protected static final String DELIMITER_ERROR = "Warning: Please specify a valid set of delimiters such as \".?!:;/|%\"\n"
            + "Example:  java -jar AverageSentenceLength.jar -d \".?!:;\"";

    protected static final String VALID_DELIMITERS = ".?!:;/|%";

    public void setFile(File file) {
        this.file = file;
    }

    public int computeAverageSentenceLength() {
        String content = null;
        try {
            content = readFile(file);
        } catch (IOException e) {
            System.out.println("Warning: File not found.\n"
                    + "Please make sure that you entered the correct file name or directory.\n"
                    + "If your file is named: 'file.txt'\n"
                    + "and it is located in the directory: '/Users/username/Documents'\n" + "you should enter:\n"
                    + "java -jar AverageSentenceLength.jar '/Users/username/Documents/file.txt'");
            throw new RuntimeException();
        }

        int numSentences = 0;
        int numTotalWords = 0;

        // Match any delimiter
        Pattern pattern = Pattern.compile("(.*?)[" + delimiter + "]");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String sentence = content.substring(matcher.start(), matcher.end() - 1).trim();
            if (!"".equals(sentence)) {
                // Split into words at every space or comma character
                String[] words = sentence.split("\\s*(,|\\s)\\s*");

                int countWords = 0;
                for (String word : words) {
                    // Remove quotes from the word
                    word = word.replaceAll("['\"]", "");
                    if (word.length() >= minWordLength) {
                        countWords++;
                    }
                }

                numTotalWords += countWords;
                numSentences++;
            }
        }

        if (numSentences == 0)
            return 0;

        return numTotalWords / numSentences;
    }

    public void setSentenceDelimiters(String delimiter) {
        for (char ch : delimiter.toCharArray()) {
            if (VALID_DELIMITERS.indexOf(ch) < 0) {
                System.out.println(DELIMITER_ERROR);
                throw new RuntimeException();
            }
        }
        this.delimiter = delimiter;
    }

    public void setMinWordLength(int wordLength) {
        if (wordLength < 0) {
            System.out.println(WORD_LENGTH_ERROR);
            throw new RuntimeException();
        }
        this.minWordLength = wordLength;
    }

    private String readFile(File file) throws IOException {
        String content = "";

        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            String line = reader.readLine();
            while (line != null) {
                content += line + " ";
                line = reader.readLine();
            }
        } finally {
            reader.close();
        }

        return content;
    }
}
