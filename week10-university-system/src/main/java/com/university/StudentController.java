package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentController {
    // The @FXML annotation connects these variables to the fx:id in the FXML file
    @FXML private TextField regField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colReg;
    @FXML private TableColumn<Student, String> colFirst;
    @FXML private TableColumn<Student, String> colLast;
    @FXML private Label statusLabel;
    @FXML private Button saveButton;

    // This list holds the data that the TableView displays
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    // The initialize() method is automatically called when the FXML loads
    @FXML
    public void initialize() {
        // 1. Connect the TableColumns to the Student properties (Week 8 skill)
        colReg.setCellValueFactory(cellData -> cellData.getValue().regNumberProperty());
        colFirst.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLast.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // 2. Tell the TableView to use our studentList
        studentTable.setItems(studentList);

        // 3. Disable the Save button if the text fields are empty (Week 8 Binding)
        saveButton.disableProperty().bind(
                regField.textProperty().isEmpty()
                        .or(firstNameField.textProperty().isEmpty())
                        .or(lastNameField.textProperty().isEmpty())
        );

        // 4. Load the initial data from the database
        loadStudents();
    }

    // This method is triggered when the user clicks "Save Student"
    @FXML
    private void handleSaveStudent() {
        String reg = regField.getText();
        String fName = firstNameField.getText();
        String lName = lastNameField.getText();

        // Create a Background Task to save the data (Producer-Consumer Pattern)
        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "INSERT INTO students (registration_number, first_name, last_name, email) VALUES (?, ?, ?, ?)";

                // Connect and execute
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, reg);
                    stmt.setString(2, fName);
                    stmt.setString(3, lName);
                    stmt.setString(4, reg + "@university.edu");
                    stmt.executeUpdate();
                }
                return null;
            }
        };

        // When the background task succeeds, update the UI
        saveTask.setOnSucceeded(e -> {
            statusLabel.setText("Student saved successfully!");
            regField.clear();
            firstNameField.clear();
            lastNameField.clear();
            loadStudents(); // Refresh the table to show the new student
        });

        saveTask.setOnFailed(e -> {
            statusLabel.setText("Error saving student: " + saveTask.getException().getMessage());
            saveTask.getException().printStackTrace();
        });

        // Start the task on a background thread
        new Thread(saveTask).start();
    }

    // Method to load students from the database
    private void loadStudents() {
        LoadStudentsTask task = new LoadStudentsTask();

        // When the task finishes, update the UI
        task.setOnSucceeded(e -> {
            studentList.setAll(task.getValue());
            statusLabel.setText("Loaded " + studentList.size() + " students from database.");
        });

        task.setOnFailed(e -> {
            statusLabel.setText("Database error: " + task.getException().getMessage());
            task.getException().printStackTrace();
        });

        // Start the task on a new background thread
        new Thread(task).start();
    }
}