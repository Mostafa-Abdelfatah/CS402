package oneTimePad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class FileOperationsGUI extends JFrame implements ActionListener {

    private JFileChooser fileChooser;
    private JTextField filePathField;
    private JLabel messageLabel, keyLabel;
    private JButton chooseFileButton, encryptButton, decryptButton;
    private JPanel jPanelFile, jPanel3Buttons, jPanelMessage;
    private final One o1 = new One();

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

            // Validate file selection
            if (filePath.isEmpty()) {
                messageLabel.setText("Please choose a file!");
                return;
            }

            // Perform the algorithm based on the button clicked
            String operation = ((JButton) source).getText();
            String result = null;


            switch (operation) {
                case "Encrypt":
                    result = o1.encrypt(readFile(filePath));
                    if (result != null) {
                        writeFile(filePath, result); // Write encrypted content
                    }
                    break;
                case "Decrypt":
                    String s = readFile(filePath);
                    assert s != null;
                    String a = "";
                    // to remove '\0' char
                    for(int i = 0; i < s.length(); i++) {
                        if(Character.isAlphabetic(s.charAt(i))) {
                            a += s.charAt(i);
                        }
                    }
                    if(a.length() != o1.getSize()){
                        messageLabel.setText("the text has a length not equal the key");
                        return;
                    }
                    result = o1.decrypt(a);
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

    public static void main(String[] args) {
        new FileOperationsGUI();
    }
}
