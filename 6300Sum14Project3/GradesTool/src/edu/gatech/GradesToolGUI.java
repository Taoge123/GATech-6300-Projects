package edu.gatech;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GradesToolGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private JComboBox<Student> studentListComboBox;
    private JTextArea studentInformationTextArea;
    private JButton saveButton;
    private GradesDB db;

    public static void main(String[] args) {
        GradesToolGUI gtg = new GradesToolGUI(Constants.GRADES_DB);
        gtg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gtg.setVisible(true);
    }

    public GradesToolGUI(String location) {
        db = new GradesDB(location);
        init();
    }

    private void init() {
        setTitle("GradesTool");
        setSize(500, 500);

        mainPanel = new JPanel(new GridLayout(1, 2));

        studentInformationTextArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(studentInformationTextArea);

        // scrollPane.setPreferredSize(getPreferredSize());
        studentListComboBox = new JComboBox<Student>();
        studentListComboBox.setMaximumSize(new Dimension(200, 40));
        HashSet<Student> students = db.getStudents();
        for (Student student : students) {
            studentListComboBox.addItem(student);
        }

        studentListComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student selectedStudent = (Student) studentListComboBox.getSelectedItem();
                studentInformationTextArea.setText(selectedStudent.getTextOutput());
                studentInformationTextArea.setCaretPosition(0);
            }
        });

        // get the student data for the first student by default.
        if (studentListComboBox.getItemCount() > 0) {
            studentListComboBox.setSelectedIndex(0);
        }

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 20));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Student selectedStudent = (Student) studentListComboBox.getSelectedItem();

                FileDialog fileDialog = new FileDialog(new Frame(), "Save", FileDialog.SAVE);
                fileDialog.setFilenameFilter(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".txt");
                    }
                });
                fileDialog.setFile(selectedStudent.getName() + ".txt");
                fileDialog.setVisible(true);

                if (fileDialog.getFile() != null) {
                    String fileName = fileDialog.getDirectory() + "//" + fileDialog.getFile();

                    System.out.print(fileName);
                    saveToText(selectedStudent.getName(), selectedStudent.getTextOutput(), fileName);
                }

            }
        });

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(studentListComboBox);
        leftPanel.add(saveButton);
        mainPanel.add(leftPanel);

        mainPanel.add(scrollPane);

        setContentPane(mainPanel);

    }

    public void saveToText(String studentName, String content, String filename) {
        // String filename = studentName.replace(" ", "") + ".txt";

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(content);

        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}
