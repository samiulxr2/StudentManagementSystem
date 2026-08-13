package com.sms.controller;

import com.sms.model.Course;
import com.sms.model.Registration;
import com.sms.model.Student;
import com.sms.service.RegistrationService;
import com.sms.utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistrationController {

    @FXML
    private TextField studentIdField;

    @FXML
    private ComboBox<String> semesterBox;

    @FXML
    private ComboBox<String> batchBox;

    @FXML
    private ComboBox<String> sectionBox;

    @FXML
    private ComboBox<String> courseBox;

    @FXML
    private ComboBox<String> registrationTypeBox;

    @FXML
    private Label labSectionLabel;

    @FXML
    private Label capacityLabel;

    @FXML
    private Label totalRegistrationLabel;

    @FXML
    private Label sectionCountLabel;

    @FXML
    private Label labCountLabel;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Registration> registrationTable;

    @FXML
    private TableColumn<Registration, String> registrationIdColumn;

    @FXML
    private TableColumn<Registration, String> studentIdColumn;

    @FXML
    private TableColumn<Registration, String> studentNameColumn;

    @FXML
    private TableColumn<Registration, String> semesterColumn;

    @FXML
    private TableColumn<Registration, String> batchColumn;

    @FXML
    private TableColumn<Registration, String> sectionColumn;

    @FXML
    private TableColumn<Registration, String> labSectionColumn;

    @FXML
    private TableColumn<Registration, String> courseCodeColumn;

    @FXML
    private TableColumn<Registration, Boolean> retakeColumn;

    private final RegistrationService registrationService =
            new RegistrationService();

    private final ObservableList<Registration> registrations =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        loadSemesters();
        loadBatches();
        loadSections();
        loadCourses();

        registrationTypeBox.setItems(
                FXCollections.observableArrayList(
                        "NORMAL",
                        "RETAKE"
                )
        );

        registrationTypeBox.setValue("NORMAL");

        setupTable();

        semesterBox.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateCapacity()
        );

        batchBox.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateCapacity()
        );

        sectionBox.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateCapacity()
        );

        courseBox.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateCourseInformation()
        );

        refreshTable();
    }

    private void loadSemesters() {

        semesterBox.setItems(
                FXCollections.observableArrayList(
                        "Spring-2026",
                        "Summer-2026",
                        "Fall-2026",
                        "Spring-2027",
                        "Summer-2027",
                        "Fall-2027"
                )
        );
    }

    private void loadBatches() {

        ObservableList<String> batches =
                FXCollections.observableArrayList();

        for (int i = 1; i <= 100; i++) {
            batches.add(String.valueOf(i));
        }

        batchBox.setItems(batches);
    }

    private void loadSections() {

        sectionBox.setItems(
                FXCollections.observableArrayList(
                        "A",
                        "B",
                        "C",
                        "D",
                        "E",
                        "F",
                        "G",
                        "H",
                        "I",
                        "J",
                        "K",
                        "L",
                        "M",
                        "N",
                        "O"
                )
        );
    }

    private void loadCourses() {

        ObservableList<String> courses =
                FXCollections.observableArrayList();

        for (Course course :
                registrationService.getAllCourses()) {

            courses.add(course.getCourseCode());
        }

        courseBox.setItems(courses);
    }

    private void setupTable() {

        registrationIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("registrationId")
        );

        studentIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("studentId")
        );

        semesterColumn.setCellValueFactory(
                new PropertyValueFactory<>("semester")
        );

        batchColumn.setCellValueFactory(
                new PropertyValueFactory<>("batch")
        );

        sectionColumn.setCellValueFactory(
                new PropertyValueFactory<>("section")
        );

        labSectionColumn.setCellValueFactory(
                new PropertyValueFactory<>("labSection")
        );

        courseCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseCode")
        );

        retakeColumn.setCellValueFactory(
                new PropertyValueFactory<>("retake")
        );

        studentNameColumn.setCellValueFactory(
                cellData -> {

                    String studentId =
                            cellData.getValue().getStudentId();

                    String studentName =
                            findStudentName(studentId);

                    return new javafx.beans.property.SimpleStringProperty(
                            studentName
                    );
                }
        );

        registrationTable.setItems(registrations);

        registrationTable.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, registration) -> {

                            if (registration != null) {

                                studentIdField.setText(
                                        registration.getStudentId()
                                );

                                semesterBox.setValue(
                                        registration.getSemester()
                                );

                                batchBox.setValue(
                                        registration.getBatch()
                                );

                                sectionBox.setValue(
                                        registration.getSection()
                                );

                                courseBox.setValue(
                                        registration.getCourseCode()
                                );

                                registrationTypeBox.setValue(
                                        registration.getRetake()
                                                ? "RETAKE"
                                                : "NORMAL"
                                );

                                updateCapacity();
                                updateCourseInformation();
                            }
                        }
                );
    }

    @FXML
    private void registerStudent() {

        if (studentIdField.getText().isBlank()
                || semesterBox.getValue() == null
                || batchBox.getValue() == null
                || sectionBox.getValue() == null
                || courseBox.getValue() == null
                || registrationTypeBox.getValue() == null) {

            AlertUtil.warning(
                    "Validation",
                    "Please fill all registration fields."
            );

            return;
        }

        String studentId =
                studentIdField.getText().trim();

        Student student = findStudent(studentId);

        if (student == null) {

            AlertUtil.error(
                    "Student Not Found",
                    "No student was found with ID: "
                            + studentId
            );

            return;
        }

        boolean retake =
                "RETAKE".equalsIgnoreCase(
                        registrationTypeBox.getValue()
                );

        boolean success =
                registrationService.registerStudent(
                        studentId,
                        semesterBox.getValue(),
                        batchBox.getValue(),
                        sectionBox.getValue(),
                        courseBox.getValue(),
                        retake
                );

        if (success) {

            refreshTable();

            updateCapacity();

            clearFields();

            AlertUtil.information(
                    "Success",
                    "Student registered successfully."
            );

        } else {

            Course course =
                    findCourse(courseBox.getValue());

            if (course != null
                    && "LAB".equalsIgnoreCase(
                    course.getCourseType())) {

                AlertUtil.error(
                        "Registration Failed",
                        "Lab groups are full.\n"
                                + "Maximum 25 students are allowed "
                                + "in each lab group."
                );

            } else {

                AlertUtil.error(
                        "Registration Failed",
                        "Registration failed.\n"
                                + "The student may already be registered "
                                + "or the section may be full."
                );
            }
        }
    }

    @FXML
    private void removeRegistration() {

        Registration selected =
                registrationTable.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            AlertUtil.warning(
                    "Selection Required",
                    "Please select a registration from the table."
            );

            return;
        }

        boolean removed =
                registrationService.removeRegistration(
                        selected.getRegistrationId()
                );

        if (removed) {

            refreshTable();

            updateCapacity();

            clearFields();

            AlertUtil.information(
                    "Success",
                    "Registration removed successfully."
            );

        } else {

            AlertUtil.error(
                    "Error",
                    "Registration could not be removed."
            );
        }
    }

    @FXML
    private void searchRegistration() {

        String keyword =
                searchField.getText()
                        .trim()
                        .toLowerCase();

        if (keyword.isEmpty()) {

            refreshTable();

            return;
        }

        ObservableList<Registration> filtered =
                FXCollections.observableArrayList();

        for (Registration registration :
                registrationService.getAllRegistrations()) {

            String studentName =
                    findStudentName(
                            registration.getStudentId()
                    );

            boolean matchesStudentId =
                    registration.getStudentId() != null
                            && registration.getStudentId()
                            .toLowerCase()
                            .contains(keyword);

            boolean matchesName =
                    studentName.toLowerCase()
                            .contains(keyword);

            boolean matchesCourse =
                    registration.getCourseCode() != null
                            && registration.getCourseCode()
                            .toLowerCase()
                            .contains(keyword);

            boolean matchesBatch =
                    registration.getBatch() != null
                            && registration.getBatch()
                            .toLowerCase()
                            .contains(keyword);

            boolean matchesSection =
                    registration.getSection() != null
                            && registration.getSection()
                            .toLowerCase()
                            .contains(keyword);

            if (matchesStudentId
                    || matchesName
                    || matchesCourse
                    || matchesBatch
                    || matchesSection) {

                filtered.add(registration);
            }
        }

        registrationTable.setItems(filtered);
    }

    @FXML
    private void refreshTable() {

        registrations.setAll(
                registrationService.getAllRegistrations()
        );

        registrationTable.setItems(registrations);

        totalRegistrationLabel.setText(
                String.valueOf(registrations.size())
        );

        updateCapacity();
    }

    private void updateCourseInformation() {

        if (courseBox.getValue() == null) {

            labSectionLabel.setText(
                    "Not Applicable"
            );

            labCountLabel.setText("N/A");

            return;
        }

        Course course =
                findCourse(courseBox.getValue());

        if (course == null) {

            labSectionLabel.setText(
                    "Not Applicable"
            );

            labCountLabel.setText("N/A");

            return;
        }

        if ("LAB".equalsIgnoreCase(
                course.getCourseType())) {

            String labSection =
                    registrationService.findAvailableLabSection(
                            semesterBox.getValue(),
                            batchBox.getValue(),
                            sectionBox.getValue()
                    );

            if (labSection.isEmpty()) {

                labSectionLabel.setText(
                        "FULL"
                );

                labCountLabel.setText(
                        "50 / 50"
                );

            } else {

                labSectionLabel.setText(
                        labSection
                );

                int count =
                        registrationService.countLabStudents(
                                semesterBox.getValue(),
                                batchBox.getValue(),
                                labSection
                        );

                labCountLabel.setText(
                        count + " / 25"
                );
            }

        } else {

            labSectionLabel.setText(
                    "Not Applicable"
            );

            labCountLabel.setText(
                    "N/A"
            );
        }
    }

    private void updateCapacity() {

        if (semesterBox.getValue() == null
                || batchBox.getValue() == null
                || sectionBox.getValue() == null) {

            capacityLabel.setText("0 / 50");
            sectionCountLabel.setText("0 / 50");

            return;
        }

        int count =
                registrationService.countTheoryStudents(
                        semesterBox.getValue(),
                        batchBox.getValue(),
                        sectionBox.getValue()
                );

        capacityLabel.setText(
                count + " / 50"
        );

        sectionCountLabel.setText(
                count + " / 50"
        );

        updateCourseInformation();
    }

    private Student findStudent(
            String studentId) {

        for (Student student :
                registrationService.getAllStudents()) {

            if (student.getStudentId() != null
                    && student.getStudentId()
                    .equalsIgnoreCase(studentId)) {

                return student;
            }
        }

        return null;
    }

    private String findStudentName(
            String studentId) {

        Student student =
                findStudent(studentId);

        if (student == null) {
            return "";
        }

        return student.getFullName() == null
                ? ""
                : student.getFullName();
    }

    private Course findCourse(
            String courseCode) {

        for (Course course :
                registrationService.getAllCourses()) {

            if (course.getCourseCode() != null
                    && course.getCourseCode()
                    .equalsIgnoreCase(courseCode)) {

                return course;
            }
        }

        return null;
    }

    @FXML
    private void clearFields() {

        studentIdField.clear();

        semesterBox.getSelectionModel()
                .clearSelection();

        batchBox.getSelectionModel()
                .clearSelection();

        sectionBox.getSelectionModel()
                .clearSelection();

        courseBox.getSelectionModel()
                .clearSelection();

        registrationTypeBox.setValue(
                "NORMAL"
        );

        searchField.clear();

        labSectionLabel.setText(
                "Not Applicable"
        );

        capacityLabel.setText(
                "0 / 50"
        );

        labCountLabel.setText(
                "N/A"
        );

        registrationTable.getSelectionModel()
                .clearSelection();

        refreshTable();
    }
}