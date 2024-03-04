package hillCipher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileOperationsGUI2 extends JFrame implements ActionListener {

    private JFileChooser fileChooser;
    private JTextField filePathField;
    private JTextField keyField, plainField, cipherField;
    private JLabel messageLabel, keyLabel, plainLabel, cipherLabel, messageLabel2;
    private JButton chooseFileButton, encryptButton, decryptButton, attackButton;

    public FileOperationsGUI2() {
        super("File Operations GUI");
        setLayout(new GridLayout(6, 6, 20, 10)); // Use GridLayout
        setSize(500, 350);

        // File Chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        JPanel jPanelFile = new JPanel();
        // File Chooser Button
        chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(this);
        jPanelFile.add(chooseFileButton);

        // File Path Field
        filePathField = new JTextField(30);
        filePathField.setEditable(false);
        jPanelFile.add(filePathField);
        add(jPanelFile);

        JPanel jPanelKey = new JPanel();
        // Key Label
        keyLabel = new JLabel("Enter Key:");
        jPanelKey.add(keyLabel);
        // Key Field
        keyField = new JTextField(10);
        jPanelKey.add(keyField);
        add(jPanelKey);

        JPanel jPanel3Buttons = new JPanel();
        // Encrypt Button
        encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(this);
        jPanel3Buttons.add(encryptButton);
        // Decrypt Button
        decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(this);
        jPanel3Buttons.add(decryptButton);
        // Attack Button
        attackButton = new JButton("Attack");
        attackButton.addActionListener(this);
        jPanel3Buttons.add(attackButton);
        add(jPanel3Buttons);

        JPanel jPanelMessage = new JPanel();
        // Message Label
        messageLabel = new JLabel("");
        jPanelMessage.add(messageLabel);
        add(jPanelMessage);

        JPanel jPanelAttack = new JPanel();
        // Plain Text Label and Field
        plainLabel = new JLabel("Plaintext:");
        plainField = new JTextField(15);
        jPanelAttack.add(plainLabel);
        jPanelAttack.add(plainField);
        // Cipher Text Label and Field
        cipherLabel = new JLabel("Ciphertext:");
        cipherField = new JTextField(15);
        jPanelAttack.add(cipherLabel);
        jPanelAttack.add(cipherField);
        // Message Label for Attack
        messageLabel2 = new JLabel();
        jPanelAttack.add(messageLabel2);
        add(jPanelAttack);

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
            }
        } else if (source == encryptButton || source == decryptButton) {
            // Implementation for Encrypt and Decrypt buttons
            // ...
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
                if(key.length() != 9) {
                    messageLabel.setText("please enter a valid key have length of 9 not less or more");
                    return;
                }
            }

            // Perform the algorithm based on the button clicked
            String operation = ((JButton) source).getText();
            String result = null;

            switch (operation) {
                case "Encrypt":
                    Hill h = new Hill();
                    h.setMat(key);
                    result = h.encrypt(readFile(filePath), h.getMat());
                    if (result != null) {
                        writeFile(filePath, result); // Write encrypted content
                    }
                    break;
                case "Decrypt":
                    Hill h1 = new Hill();
                    h1.setMat(key);
                    long x = Hill.determinant(h1.getMat());
                    if(x == 0){
                        messageLabel.setText("the key matrix doesn't have invearse because det=0");
                        return;
                    }

                    h1.setInvMat();
                    long d = h1.getMultInv(x);
                    if(d == -1) {
                        messageLabel.setText("the key matrix det doesn't have mult.inveasre");
                        return;
                    }

                    result = h1.encrypt(readFile(filePath),h1.getInvMat());
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


        } else if (source == attackButton) {
            // Attack button logic
            String plain = plainField.getText();
            String cipher = cipherField.getText();

            // Validate input fields
            if (plain.isEmpty() || cipher.isEmpty()) {
                messageLabel.setText("Please enter the plaintext and the ciphertext");
                return;
            }
            if (plain.length() != 9 || cipher.length() != 9) {
                messageLabel.setText("The length of plaintext and ciphertext should be 9");
                return;
            }
            for (int i = 0; i < plain.length(); i++) {
                if (!Character.isAlphabetic(plain.charAt(i))) {
                    messageLabel.setText("The plaintext must contain letters only");
                    return;
                }
            }
            for (int i = 0; i < cipher.length(); i++) {
                if (!Character.isAlphabetic(cipher.charAt(i))) {
                    messageLabel.setText("The ciphertext must contain letters only");
                    return;
                }
            }

            // Perform attack
            Hill h = new Hill();
            h.setMatAttackP_setMatAttackC(plain, cipher);
            long d = Hill.determinant(h.matAttackP);
            if (d == 0) {
                messageLabel.setText("The plaintext matrix doesn't have inverse as det = 0");
                return;
            }
            if (h.getMultInv(d) == -1) {
                messageLabel.setText("The determinant of plain matrix doesn't have multiplicative inverse.");
                return;
            }

            String filePath = filePathField.getText();
            String result = h.attack();
            if (result != null) {
                writeFile(filePath, result); // Write decrypted content
                messageLabel.setText("Attack successful!");
            } else {
                messageLabel.setText("Error in performing attack");
            }
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

    public static void main(String[] args) {
        new FileOperationsGUI2();
    }
}
