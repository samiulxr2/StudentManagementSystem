package com.sms.controller;

import com.sms.model.Student;
import com.sms.service.StudentService;
import com.sms.utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentController {

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField departmentField;

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextArea addressField;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> idColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> departmentColumn;

    @FXML
    private TableColumn<Student, String> genderColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private TableColumn<Student, String> phoneColumn;

    @FXML
    private TableColumn<Student, String> addressColumn;

    private final StudentService studentService =
            new StudentService();

    private final ObservableList<Student> students =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // Gender options
        genderBox.setItems(
                FXCollections.observableArrayList(
                        "Male",
                        "Female",
                        "Other"
                )
        );

        // Table columns
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("studentId")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("fullName")
        );

        departmentColumn.setCellValueFactory(
                new PropertyValueFactory<>("department")
        );

        genderColumn.setCellValueFactory(
                new PropertyValueFactory<>("gender")
        );

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );

        addressColumn.setCellValueFactory(
                new PropertyValueFactory<>("address")
        );

        refreshTable();

        // Select student from table
        studentTable.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, student) -> {

                            if (student != null) {

                                studentIdField.setText(
                                        student.getStudentId()
                                );

                                nameField.setText(
                                        student.getFullName()
                                );

                                departmentField.setText(
                                        student.getDepartment()
                                );

                                genderBox.setValue(
                                        student.getGender()
                                );

                                emailField.setText(
                                        student.getEmail()
                                );

                                phoneField.setText(
                                        student.getPhone()
                                );

                                addressField.setText(
                                        student.getAddress()
                                );
                            }
                        }
                );
    }

    @FXML
    private void addStudent() {

        if (studentIdField.getText().isBlank()
                || nameField.getText().isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Student ID and Name are required."
            );

            return;
        }

        // Prevent duplicate Student ID
        if (studentService.searchStudent(
                studentIdField.getText().trim()
        ) != null) {

            AlertUtil.warning(
                    "Duplicate Student",
                    "A student with this ID already exists."
            );

            return;
        }

        Student student = new Student();

        student.setStudentId(
                studentIdField.getText().trim()
        );

        student.setFullName(
                nameField.getText().trim()
        );

        student.setDepartment(
                departmentField.getText().trim()
        );

        student.setGender(
                genderBox.getValue()
        );

        student.setEmail(
                emailField.getText().trim()
        );

        student.setPhone(
                phoneField.getText().trim()
        );

        student.setAddress(
                addressField.getText().trim()
        );

        studentService.addStudent(student);

        refreshTable();

        clearFields();

        AlertUtil.information(
                "Success",
                "Student admitted successfully."
        );
    }

    @FXML
    private void updateStudent() {

        if (studentIdField.getText().isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a student first."
            );

            return;
        }

        Student student = new Student();

        student.setStudentId(
                studentIdField.getText().trim()
        );

        student.setFullName(
                nameField.getText().trim()
        );

        student.setDepartment(
                departmentField.getText().trim()
        );

        student.setGender(
                genderBox.getValue()
        );

        student.setEmail(
                emailField.getText().trim()
        );

        student.setPhone(
                phoneField.getText().trim()
        );

        student.setAddress(
                addressField.getText().trim()
        );

        if (studentService.updateStudent(student)) {

            AlertUtil.information(
                    "Success",
                    "Student information updated successfully."
            );

        } else {

            AlertUtil.error(
                    "Error",
                    "Student not found."
            );
        }

        refreshTable();

        clearFields();
    }

    @FXML
    private void deleteStudent() {

        if (studentIdField.getText().isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a student first."
            );

            return;
        }

        if (studentService.deleteStudent(
                studentIdField.getText().trim()
        )) {

            AlertUtil.information(
                    "Success",
                    "Student deleted successfully."
            );

        } else {

            AlertUtil.error(
                    "Error",
                    "Student not found."
            );
        }

        refreshTable();

        clearFields();
    }

    @FXML
    private void searchStudent() {

        String keyword =
                searchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {

            refreshTable();

            return;
        }

        ObservableList<Student> filtered =
                FXCollections.observableArrayList();

        for (Student student :
                studentService.getAllStudents()) {

            if (student.getStudentId()
                    .toLowerCase()
                    .contains(keyword)

                    || student.getFullName()
                    .toLowerCase()
                    .contains(keyword)

                    || (student.getDepartment() != null
                    && student.getDepartment()
                    .toLowerCase()
                    .contains(keyword))) {

                filtered.add(student);
            }
        }

        studentTable.setItems(filtered);
    }

    @FXML
    private void refreshTable() {

        students.setAll(
                studentService.getAllStudents()
        );

        studentTable.setItems(students);
    }

    @FXML
    private void clearFields() {

        studentIdField.clear();

        nameField.clear();

        departmentField.clear();

        genderBox.getSelectionModel()
                .clearSelection();

        emailField.clear();

        phoneField.clear();

        addressField.clear();

        searchField.clear();

        studentTable.getSelectionModel()
                .clearSelection();

        refreshTable();
    }
}