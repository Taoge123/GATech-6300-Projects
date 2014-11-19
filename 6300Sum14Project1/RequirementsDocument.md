#Requirements Document – Team 007

##1 User Requirements
The users will be required to open and navigate the command line interface on their computer.  The users will be given access to a precompiled version of our program: "AverageSentenceLength.jar."  They will be able to identify and navigate to the folder containing “AverageSentenceLength.jar”. and know the path of the raw text file they wish to compute the average sentence length for.   

###1.1 Software Interfaces
The application will work with the command line interface with java 1.6+ installed. 

###1.2 User Interfaces
The user interface will be the command line interface.  Messages will be printed at the command line in response to running the the program.

###1.3 User Characteristics
There will be approximately 350 students per term that will be encouraged to use this software. They will have range from having no/novice computer skills to those that can expert level users who can also program.

##2 System Requirements
* The system must run java 1.6 or a later version of java and have a command line interface. 
* The system will be developed to count the average words of documents in ASCII/plain-text format only.
* Input files must be read accessible to the system.

###2.1 Functional Requirements
The output will be the average words per sentence rounded down to the closest integer.  An average word length of 7.329 would output 7.

####2.1.1 - File location and name
The user will enter `java -jar AverageSentenceLength.jar <file-path>` into the command line while in the same directory as the jar file.  If a valid file-path is not entered a helpful message will be displayed about how to enter the file-path.

**Use cases**

A file named ‘file.txt’ is filled with the following: 
```
A boy sees a blue sky.  The boy likes blue!
```

The ‘file.txt’ and ‘AverageSentenceLength.jar’ are in the same directory.

**Case 2.1.1.1 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt
```

**Output:**
```
Average Sentence Length: 4 words per sentence
```
(Default is to count words greater than or equal to 3 characters)

**Case 2.1.1.2 - User enters:**
```
java -jar AverageSentenceLength.jar
```

**Output:**
```
Warning: File not found.  
Please make sure that you entered the correct file name or directory.
If your file is named: ‘file.txt’ 
and it is located in the directory: ‘/Users/username/Documents’ 
you should enter:
java -jar AverageSentenceLength.jar /Users/username/Documents/file.txt
```

**Case 2.1.1.3 - User enters:**
```
java -jar AverageSentenceLength.jar flie.txt
```

**Output:**
```
Warning: File not found.  
Please make sure that you entered the correct file name or directory.
If your file is named: ‘file.txt’ 
and it is located in the directory: ‘/Users/username/Documents’ 
you should enter:
java -jar AverageSentenceLength.jar /Users/username/Documents/file.txt
```


####2.1.2 - Minimum word length
An optional flag can be used to specify the minimum word length to count with -l:  `java -jar AverageSentenceLength.jar <file-path> -l 5` would only count words greater than or equal to 5 characters.  The default word length is 3.

**Use cases**


A file named ‘file.txt’ is filled with the following: 
```
A boy sees a blue sky.  The boy likes blue!
```



The file ‘file.txt’ and ‘AverageSentenceLength.jar’ are in the same directory.

**Case 2.1.2.1 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -l 1
```

**Output:**
```
Average Sentence Length: 5 words per sentence
```


**Case 2.1.2.2 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -l 2
```

**Output:**
```
Average Sentence Length: 4 words per sentence
```


**Case 2.1.2.3 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -l 3
```

**Output:**
```
Average Sentence Length: 4 words per sentence
```


**Case 2.1.2.4 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -l 4
```

**Output:**
```
Average Sentence Length: 2 words per sentence
```


**Case 2.1.2.5 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -l 5
```

**Output:**
```
Average Sentence Length: 0 words per sentence
```


**Case 2.1.2.6 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -l
```

**Output:**
```
Warning: Please specify a valid minimum word length. 
Example:  java -jar AverageSentenceLength.jar -l 5
```


**Case 2.1.2.7 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -l two
```

**Output:**
```
Warning: Please specify a valid minimum word length.   
Example:  java -jar AverageSentenceLength.jar -l 5
```


**Case 2.1.2.8 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -l 2.5
```

**Output:**
```
Warning: Please specify a valid minimum word length.  
Example:  java -jar AverageSentenceLength.jar -l 5
```


####2.1.3 - Delimiters
An optional flag can be used to specify the punctuation that marks the end of a sentence with -d:  `java -jar AverageSentenceLength.jar <file-path> -d .?!:;/|%` would break the document into sentences at periods, commas, question marks, exclamation points, semicolons, and colons.  The default delimiters are “.”, “?”, “!” , “;” , and “:”  
Valid Delimiters:  . , ? ! : ; / | %

**Use cases**

A file named ‘file.txt’ is filled with the following:
```
A boy sees a blue sky.  The boy likes blue!
```

The text file ‘file.txt’ and ‘AverageSentenceLength.jar’ are in the same directory.



**Case 2.1.3.1 - User enters** 
```
java -jar AverageSentenceLength.jar file.txt -d .
```

**Output:**
```
Average Sentence Length: 4 words per sentence
```
(Everything after the last delimter is dropped from the calculations)



**Case 2.1.3.2 - User enters** 
```
java -jar AverageSentenceLength.jar file.txt -d !?
```

**Output:**
```
Average Sentence Length: 8 words per sentence
```


**Case 2.1.3.3 - User Enters:**
```
java -jar AverageSentenceLength.jar file.txt -d /
```

**Output:**
```
Warning: Please specify a valid set of delimiters such as ".?!:;/|%"
Example:  java -jar AverageSentenceLength.jar -d .?!:;
```


**Case 2.1.3.4 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -d
```

**Output:**
```
Warning: Please specify a valid set of delimiters such as ".?!:;/|%"
Example:  java -jar AverageSentenceLength.jar -d .?:;
```


**Case 2.1.3.5 - User enters:**
```
java -jar AverageSentenceLength.jar file.txt -d period
``` 

**Output:**
```
Warning:  Please specify a valid set of delimiters such as ".?!:;/|%"  
Example:  java -jar AverageSentenceLength.jar -d .?:!;
```

####2.1.4 - Combinations

**Use cases**

A file named ‘file.txt’ is filled with the following: 
```
A boy sees a blue sky.  The boy likes blue!
```

The ‘file.txt’ and ‘AverageSentenceLength.jar’ are in the same directory.


**Case 2.1.4.1 - User Enters:**
```
java -jar AverageSentenceLength.jar file.txt -l 1 -d !?
```

**Output:**
```
Average Sentence Length: 10 words per sentence.
```


**Case 2.1.4.2 - User Enters:**
```
java -jar AverageSentenceLength.jar file.txt  -d !? -l 1
```

**Output:**
```
Average Sentence Length: 10 words per sentence.
```


**Case 2.1.4.3 - User Enters:**
```
java -jar AverageSentenceLength.jar file.txt  -d !? -l
```

**Output:**
```
Warning: Please specify a valid minimum word length. 
Example:  java -jar AverageSentenceLength.jar -l 5"
```


**Case 2.1.4.4 - User Enters:**
```
java -jar AverageSentenceLength.jar file.txt  -d -l 1
```

**Output:**
```
Warning: Please specify a valid set of delimiters such as ".?!:;/|%"   
Example:  java -jar AverageSentenceLength.jar -d .?:;
```


**Case 2.1.4.4 - User Enters:**
```
java -jar AverageSentenceLength.jar file.txt  -d -l
```

**Output:**
```
Warning: Please specify a valid set of delimiters such as ".?!:;/|%"
Example:  java -jar AverageSentenceLength.jar -d .?:;
Warning: Please specify a valid minimum word length. 
Example:  java -jar AverageSentenceLength.jar -l 5"
```

###2.2 Non-Functional Requirements


The software should provide meaningful and helpful feedback in the case of misuse. 
