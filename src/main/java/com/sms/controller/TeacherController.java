package com.sms.controller;

import com.sms.model.Teacher;
import com.sms.service.TeacherService;
import com.sms.utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeacherController {

    @FXML
    private TextField teacherIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField designationField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Teacher> teacherTable;

    @FXML
    private TableColumn<Teacher, String> idColumn;

    @FXML
    private TableColumn<Teacher, String> nameColumn;

    @FXML
    private TableColumn<Teacher, String> departmentColumn;

    @FXML
    private TableColumn<Teacher, String> designationColumn;

    @FXML
    private TableColumn<Teacher, String> emailColumn;

    @FXML
    private TableColumn<Teacher, String> phoneColumn;

    private final TeacherService teacherService = new TeacherService();

    private final ObservableList<Teacher> teachers =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        refreshTable();

        teacherTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, teacher) -> {

                    if (teacher != null) {

                        teacherIdField.setText(teacher.getTeacherId());
                        nameField.setText(teacher.getFullName());
                        departmentField.setText(teacher.getDepartment());
                        designationField.setText(teacher.getDesignation());
                        emailField.setText(teacher.getEmail());
                        phoneField.setText(teacher.getPhone());

                    }

                });

    }

    @FXML
    private void addTeacher() {

        if (teacherIdField.getText().isBlank()
                || nameField.getText().isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Teacher ID and Name are required."
            );
            return;
        }

        Teacher teacher = new Teacher();

        teacher.setTeacherId(teacherIdField.getText().trim());
        teacher.setFullName(nameField.getText().trim());
        teacher.setDepartment(departmentField.getText().trim());
        teacher.setDesignation(designationField.getText().trim());
        teacher.setEmail(emailField.getText().trim());
        teacher.setPhone(phoneField.getText().trim());

        teacherService.addTeacher(teacher);

        refreshTable();

        clearFields();

        AlertUtil.information(
                "Success",
                "Teacher added successfully."
        );

    }

    @FXML
    private void updateTeacher() {

        Teacher teacher = new Teacher();

        teacher.setTeacherId(teacherIdField.getText().trim());
        teacher.setFullName(nameField.getText().trim());
        teacher.setDepartment(departmentField.getText().trim());
        teacher.setDesignation(designationField.getText().trim());
        teacher.setEmail(emailField.getText().trim());
        teacher.setPhone(phoneField.getText().trim());

        if (teacherService.updateTeacher(teacher)) {

            AlertUtil.information(
                    "Success",
                    "Teacher updated successfully."
            );

        } else {

            AlertUtil.error(
                    "Error",
                    "Teacher not found."
            );

        }

        refreshTable();

        clearFields();

    }

    @FXML
    private void deleteTeacher() {

        if (teacherService.deleteTeacher(teacherIdField.getText().trim())) {

            AlertUtil.information(
                    "Success",
                    "Teacher deleted successfully."
            );

        } else {

            AlertUtil.error(
                    "Error",
                    "Teacher not found."
            );

        }

        refreshTable();

        clearFields();

    }

    @FXML
    private void searchTeacher() {

        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {

            refreshTable();
            return;

        }

        ObservableList<Teacher> filtered =
                FXCollections.observableArrayList();

        for (Teacher teacher : teacherService.getAllTeachers()) {

            if (teacher.getTeacherId().toLowerCase().contains(keyword.toLowerCase())
                    || teacher.getFullName().toLowerCase().contains(keyword.toLowerCase())) {

                filtered.add(teacher);

            }

        }

        teacherTable.setItems(filtered);

    }

    @FXML
    private void refreshTable() {

        teachers.setAll(teacherService.getAllTeachers());

        teacherTable.setItems(teachers);

    }

    @FXML
    private void clearFields() {

        teacherIdField.clear();
        nameField.clear();
        departmentField.clear();
        designationField.clear();
        emailField.clear();
        phoneField.clear();
        searchField.clear();

        teacherTable.getSelectionModel().clearSelection();

        refreshTable();

    }

}