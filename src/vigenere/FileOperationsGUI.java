package vigenere;

import ceaser.Ceaser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class FileOperationsGUI extends JFrame implements ActionListener {
    private JFileChooser fileChooser;
    private JTextField filePathField;
    private JTextField keyField;
    private JLabel messageLabel, keyLabel;
    private JButton chooseFileButton, encryptButton, decryptButton;

    private JPanel jPanelFile, jPanelKey, jPanel3Buttons, jPanelMessage;

    public FileOperationsGUI() {
        super("File Operations GUI");
        setLayout(new GridLayout(4, 3, 20, 10)); // Use GridLayout
        setSize(300, 350);

        jPanelFile = new JPanel();

        // File Chooser Button
        chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(this);
        jPanelFile.add(chooseFileButton);

        // File Path Field
        filePathField = new JTextField(30);
        filePathField.setEditable(false);
        jPanelFile.add(filePathField);


        jPanelKey = new JPanel();

        //key label
        keyLabel = new JLabel("enter key:");
        jPanelKey.add(keyLabel);
        // Number Field
        keyField = new JTextField(10);
        jPanelKey.add(keyField);


        jPanelMessage = new JPanel();
        // Message Label
        messageLabel = new JLabel("");
        jPanelMessage.add(messageLabel);


        jPanel3Buttons = new JPanel();

        // Encrypt Button
        encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(this);
        jPanel3Buttons.add(encryptButton);

        // Decrypt Button
        decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(this);
        jPanel3Buttons.add(decryptButton);

        // File Chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);


        add(jPanelFile);
        add(jPanelKey);
        add(jPanel3Buttons);
        add(jPanelMessage);

        // Pack and Center Window
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == chooseFileButton) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());
                // Enable other buttons only if a file is selected
                encryptButton.setEnabled(true);
                decryptButton.setEnabled(true);
            }
        } else if (source == encryptButton || source == decryptButton) {
            String filePath = filePathField.getText();
            String key = keyField.getText();

            // Validate file selection
            if (filePath.isEmpty()) {
                messageLabel.setText("Please choose a file!");
                return;
            }

            // Validate key input

            if(key.isEmpty()) {
                messageLabel.setText("please enter a valid key");
                return;
            } else {
                for(int i = 0; i < key.length(); i++) {
                    if(!Character.isAlphabetic(key.charAt(i))){
                        messageLabel.setText("please enter a valid key");
                        return;
                    }
                }
            }

            // Perform the algorithm based on the button clicked
            String operation = ((JButton) source).getText();
            String result = null;

            switch (operation) {
                case "Encrypt":
                    result = Vigi.encrypt(readFile(filePath), key);
                    if (result != null) {
                        writeFile(filePath, result); // Write encrypted content
                    }
                    break;
                case "Decrypt":
                    result = Vigi.decrypt(readFile(filePath), key);
                    if (result != null) {
                        writeFile(filePath, result); // Write decrypted content
                    }
                    break;
                default:
                    messageLabel.setText("Invalid operation!");
            }

            if (result != null) {
                messageLabel.setText(operation + " successful!");
            } else {
                messageLabel.setText("Error in performing " + operation);
            }
        }

    }

    private String readFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (IOException ex) {
            messageLabel.setText("Error reading file: " + ex.getMessage());
            return null;
        }
    }

    private void writeFile(String filePath, String content) {
        try {
            FileWriter writer = new FileWriter("C:\\Users\\Mostafa\\IdeaProjects\\CS402\\src\\ceaser\\dyc.txt");
            writer.write(content);
            writer.close();
        } catch (IOException ex) {
            messageLabel.setText("Error writing to file: " + ex.getMessage());
        }
    }

    // Main method (unchanged)
    public static void main(String[] args) {
        new FileOperationsGUI();
    }
}
