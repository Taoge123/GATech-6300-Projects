package gatech.cs6300.project1;

import gatech.cs6300.project1.AverageSentenceLength;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        int minWordLength = -1;
        String delimiter = "";
        String filename = "";
        boolean show_length_error = false;
        boolean show_delimiter_error = false;

        int i = 0;
        while (i < args.length) {
            String token = args[i];

            if (token.startsWith("-")) {
                String nextToken = i + 1 < args.length ? args[i + 1] : null;
                if (token.equals("-l")) {

                    // Process minimum word count flag
                    if (nextToken == null || nextToken.startsWith("-")) {
                        show_length_error = true;
                    } else {
                        try {
                            minWordLength = Integer.parseInt(nextToken);
                            i++;
                        } catch (NumberFormatException e) {
                            show_length_error = true;
                        }
                    }

                } else if (token.equals("-d")) {

                    // Process delimiter flag
                    if (nextToken == null || nextToken.startsWith("-")) {
                        show_delimiter_error = true;
                    } else {
                        delimiter = nextToken;
                        i++;
                    }
                }
            } else {
                // Should be the filename
                filename = token;
            }
            i++;
        }

        if (show_delimiter_error)
            System.out.println(AverageSentenceLength.DELIMITER_ERROR);
        if (show_length_error)
            System.out.println(AverageSentenceLength.WORD_LENGTH_ERROR);
        if (show_delimiter_error || show_length_error)
            return;

        AverageSentenceLength asl = new AverageSentenceLength();

        try {
            asl.setFile(new File(filename));

            // Set delimiter and minimum word length if specified
            if (!"".equals(delimiter)) {
                asl.setSentenceDelimiters(delimiter);
            }

            if (minWordLength != -1) {
                asl.setMinWordLength(minWordLength);
            }
            int wordPerSentence = asl.computeAverageSentenceLength();
            System.out.println(String.format("Average Sentence Length: %d words per sentence", wordPerSentence));

        } catch (RuntimeException e) {
            return;
        }

    }
}
