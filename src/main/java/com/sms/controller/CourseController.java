package com.sms.controller;

import com.sms.model.Course;
import com.sms.service.CourseService;
import com.sms.utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CourseController {

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField creditField;

    @FXML
    private ComboBox<String> courseTypeBox;

    @FXML
    private TextField teacherIdField;

    @FXML
    private TextField semesterField;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, String> codeColumn;

    @FXML
    private TableColumn<Course, String> nameColumn;

    @FXML
    private TableColumn<Course, Double> creditColumn;

    @FXML
    private TableColumn<Course, String> typeColumn;

    @FXML
    private TableColumn<Course, String> teacherColumn;

    @FXML
    private TableColumn<Course, String> semesterColumn;

    private final CourseService courseService =
            new CourseService();

    private final ObservableList<Course> courses =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        courseTypeBox.setItems(
                FXCollections.observableArrayList(
                        "THEORY",
                        "LAB"
                )
        );

        codeColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseCode")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseName")
        );

        creditColumn.setCellValueFactory(
                new PropertyValueFactory<>("credit")
        );

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseType")
        );

        teacherColumn.setCellValueFactory(
                new PropertyValueFactory<>("teacherId")
        );

        semesterColumn.setCellValueFactory(
                new PropertyValueFactory<>("semester")
        );

        refreshTable();

        courseTable.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, course) -> {

                            if (course != null) {

                                courseCodeField.setText(
                                        course.getCourseCode()
                                );

                                courseNameField.setText(
                                        course.getCourseName()
                                );

                                creditField.setText(
                                        String.valueOf(
                                                course.getCredit()
                                        )
                                );

                                courseTypeBox.setValue(
                                        course.getCourseType()
                                );

                                teacherIdField.setText(
                                        course.getTeacherId()
                                );

                                semesterField.setText(
                                        course.getSemester()
                                );
                            }
                        }
                );
    }

    @FXML
    private void addCourse() {

        if (courseCodeField.getText().isBlank()
                || courseNameField.getText().isBlank()
                || creditField.getText().isBlank()
                || courseTypeBox.getValue() == null) {

            AlertUtil.warning(
                    "Validation",
                    "Course Code, Course Name, Credit and Course Type are required."
            );

            return;
        }

        try {

            double credit = Double.parseDouble(
                    creditField.getText().trim()
            );

            if (credit <= 0) {

                AlertUtil.warning(
                        "Invalid Credit",
                        "Credit must be greater than 0."
                );

                return;
            }

            Course course = new Course();

            course.setCourseCode(
                    courseCodeField.getText().trim()
            );

            course.setCourseName(
                    courseNameField.getText().trim()
            );

            course.setCredit(credit);

            course.setCourseType(
                    courseTypeBox.getValue()
            );

            course.setTeacherId(
                    teacherIdField.getText().trim()
            );

            course.setSemester(
                    semesterField.getText().trim()
            );

            courseService.addCourse(course);

            refreshTable();

            clearFields();

            AlertUtil.information(
                    "Success",
                    "Course added successfully."
            );

        } catch (NumberFormatException e) {

            AlertUtil.error(
                    "Invalid Input",
                    "Credit must be a valid number.\nExample: 1.5 or 3.0"
            );
        }
    }

    @FXML
    private void updateCourse() {

        if (courseCodeField.getText().isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a course first."
            );

            return;
        }

        if (courseTypeBox.getValue() == null) {

            AlertUtil.warning(
                    "Validation",
                    "Please select the course type."
            );

            return;
        }

        try {

            double credit = Double.parseDouble(
                    creditField.getText().trim()
            );

            if (credit <= 0) {

                AlertUtil.warning(
                        "Invalid Credit",
                        "Credit must be greater than 0."
                );

                return;
            }

            Course course = new Course();

            course.setCourseCode(
                    courseCodeField.getText().trim()
            );

            course.setCourseName(
                    courseNameField.getText().trim()
            );

            course.setCredit(credit);

            course.setCourseType(
                    courseTypeBox.getValue()
            );

            course.setTeacherId(
                    teacherIdField.getText().trim()
            );

            course.setSemester(
                    semesterField.getText().trim()
            );

            if (courseService.updateCourse(course)) {

                AlertUtil.information(
                        "Success",
                        "Course updated successfully."
                );

            } else {

                AlertUtil.error(
                        "Error",
                        "Course not found."
                );
            }

            refreshTable();

            clearFields();

        } catch (NumberFormatException e) {

            AlertUtil.error(
                    "Invalid Input",
                    "Credit must be a valid number.\nExample: 1.5 or 3.0"
            );
        }
    }

    @FXML
    private void deleteCourse() {

        if (courseCodeField.getText().isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a course first."
            );

            return;
        }

        if (courseService.deleteCourse(
                courseCodeField.getText().trim()
        )) {

            AlertUtil.information(
                    "Success",
                    "Course deleted successfully."
            );

        } else {

            AlertUtil.error(
                    "Error",
                    "Course not found."
            );
        }

        refreshTable();

        clearFields();
    }

    @FXML
    private void searchCourse() {

        String keyword =
                searchField.getText()
                        .trim()
                        .toLowerCase();

        if (keyword.isEmpty()) {

            refreshTable();

            return;
        }

        ObservableList<Course> filtered =
                FXCollections.observableArrayList();

        for (Course course :
                courseService.getAllCourses()) {

            boolean matchesCode =
                    course.getCourseCode() != null
                            && course.getCourseCode()
                            .toLowerCase()
                            .contains(keyword);

            boolean matchesName =
                    course.getCourseName() != null
                            && course.getCourseName()
                            .toLowerCase()
                            .contains(keyword);

            boolean matchesType =
                    course.getCourseType() != null
                            && course.getCourseType()
                            .toLowerCase()
                            .contains(keyword);

            if (matchesCode
                    || matchesName
                    || matchesType) {

                filtered.add(course);
            }
        }

        courseTable.setItems(filtered);
    }

    @FXML
    private void refreshTable() {

        courses.setAll(
                courseService.getAllCourses()
        );

        courseTable.setItems(courses);
    }

    @FXML
    private void clearFields() {

        courseCodeField.clear();

        courseNameField.clear();

        creditField.clear();

        courseTypeBox.getSelectionModel()
                .clearSelection();

        teacherIdField.clear();

        semesterField.clear();

        searchField.clear();

        courseTable.getSelectionModel()
                .clearSelection();

        refreshTable();
    }
}