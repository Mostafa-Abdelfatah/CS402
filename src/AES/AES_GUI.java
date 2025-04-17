package AES;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;

public class AES_GUI extends Application {

    private File selectedFile;
    private TextField filePathField;
    private TextField keyField;
    private Label messageLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DES");

        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 10;");
        root.setStyle("-fx-background-color: #003366;");
        root.setAlignment(Pos.CENTER);

        HBox fileBox = new HBox(10);
        Button chooseFileButton = new Button("Choose File");
        chooseFileButton.setStyle("-fx-font-weight: bold; -fx-background-color: #3498db; -fx-text-fill: white;");
        fileBox.setAlignment(Pos.CENTER);
        filePathField = new TextField();
        filePathField.setEditable(false);
        fileBox.getChildren().addAll(chooseFileButton, filePathField);

        HBox keyBox = new HBox(10);
        Label keyLabel = new Label("Enter key:");
        keyLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #cccccc;");
        keyBox.setAlignment(Pos.CENTER);
        keyField = new TextField();
        keyBox.getChildren().addAll(keyLabel, keyField);


        HBox buttonBox = new HBox(10);
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");
        buttonBox.setAlignment(Pos.CENTER);
        encryptButton.setStyle("-fx-font-weight: bold; -fx-background-color: green; -fx-text-fill: white;");
        decryptButton.setStyle("-fx-font-weight: bold; -fx-background-color: red; -fx-text-fill: white;");
        buttonBox.getChildren().addAll(encryptButton, decryptButton);

        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
        root.getChildren().addAll(fileBox, keyBox, buttonBox, messageLabel);
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        chooseFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        encryptButton.setOnAction(e -> processFile("Encrypt"));
        decryptButton.setOnAction(e -> processFile("Decrypt"));
    }

    private void processFile(String operation) {
        if (selectedFile == null) {
            messageLabel.setText("Please choose a file!");
            return;
        }
        if(keyField.getText().isEmpty() ) {
            messageLabel.setText("please enter key");
            return;
        }
        String key = keyField.getText();
        key = key.toUpperCase();
        boolean goodkey = true;
        //keys
        Key k = new Key(key);
        AES as = new AES();
        String result = null;
        switch (operation) {
            case "Encrypt":
                String s = readFile(selectedFile);
                result = as.realEncrypt(s.substring(0,s.length() - 1),k.getKeys());
                break;
            case "Decrypt":
                String ss = readFile(selectedFile);
                result = as.realDecrypt(ss.substring(0, ss.length() - 1),k.getKeys());
                System.out.println("the result " + result);
                break;

        }
        if (result != null) {
            writeFile(result);
            messageLabel.setText(operation + " successful!");
        } else {
            messageLabel.setText("Error in performing " + operation);
        }
    }

    private String readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            System.out.println(sb);
            return sb.toString();
        } catch (IOException ex) {
            messageLabel.setText("Error reading file: " + ex.getMessage());
            return null;
        }
    }

    private void writeFile(String content) {
        try (FileWriter writer = new FileWriter("C:\\Users\\Mostafa\\IdeaProjects\\CS402\\src\\ceaser\\dyc.txt")) {
            writer.write(content);
        } catch (IOException ex) {
            messageLabel.setText("Error writing to file: " + ex.getMessage());
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}



