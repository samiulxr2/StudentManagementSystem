package com.sms.controller;

import com.sms.model.Attendance;
import com.sms.model.Course;
import com.sms.model.Registration;
import com.sms.repository.CourseRepository;
import com.sms.service.AttendanceService;
import com.sms.service.RegistrationService;
import com.sms.utils.AlertUtil;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AttendanceController {

    // =========================================================
    // CLASS SELECTION
    // =========================================================

    @FXML
    private javafx.scene.control.ComboBox<String> semesterBox;

    @FXML
    private javafx.scene.control.ComboBox<String> courseBox;

    @FXML
    private javafx.scene.control.ComboBox<String> batchBox;

    @FXML
    private javafx.scene.control.ComboBox<String> sectionBox;

    @FXML
    private DatePicker datePicker;


    // =========================================================
    // SUMMARY
    // =========================================================

    @FXML
    private Label totalStudentLabel;

    @FXML
    private Label presentLabel;

    @FXML
    private Label absentLabel;


    // =========================================================
    // TABLE
    // =========================================================

    @FXML
    private TableView<Attendance> attendanceTable;

    @FXML
    private TableColumn<Attendance, String> studentIdColumn;

    @FXML
    private TableColumn<Attendance, String> courseCodeColumn;

    @FXML
    private TableColumn<Attendance, String> semesterColumn;

    @FXML
    private TableColumn<Attendance, String> batchColumn;

    @FXML
    private TableColumn<Attendance, String> sectionColumn;

    @FXML
    private TableColumn<Attendance, String> dateColumn;

    @FXML
    private TableColumn<Attendance, Boolean> statusColumn;


    // =========================================================
    // SERVICES
    // =========================================================

    private final AttendanceService attendanceService =
            new AttendanceService();

    private final RegistrationService registrationService =
            new RegistrationService();

    private final CourseRepository courseRepository =
            new CourseRepository();


    // =========================================================
    // TABLE DATA
    // =========================================================

    private final ObservableList<Attendance> attendanceList =
            FXCollections.observableArrayList();


    // =========================================================
    // INITIALIZE
    // =========================================================

    @FXML
    public void initialize() {

        setupTable();

        attendanceTable.setItems(attendanceList);

        loadSemesterOptions();

        setupSelectionListeners();

        updateSummary();
    }


    // =========================================================
    // TABLE SETUP
    // =========================================================

    private void setupTable() {

        studentIdColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        safe(data.getValue().getStudentId())
                )
        );


        courseCodeColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        safe(data.getValue().getCourseCode())
                )
        );


        semesterColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        safe(data.getValue().getSemester())
                )
        );


        batchColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        safe(data.getValue().getBatch())
                )
        );


        sectionColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        safe(data.getValue().getSection())
                )
        );


        dateColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        safe(data.getValue().getDate())
                )
        );


        // =====================================================
        // ATTENDANCE CHECKBOX
        // =====================================================

        statusColumn.setCellValueFactory(
                data -> {

                    boolean present =
                            "Present".equalsIgnoreCase(
                                    data.getValue().getStatus()
                            );

                    return new javafx.beans.property.SimpleBooleanProperty(
                            present
                    ).asObject();
                }
        );


        statusColumn.setCellFactory(
                new Callback<TableColumn<Attendance, Boolean>,
                        TableCell<Attendance, Boolean>>() {

                    @Override
                    public TableCell<Attendance, Boolean> call(
                            TableColumn<Attendance, Boolean> column) {

                        return new TableCell<>() {

                            private final CheckBox checkBox =
                                    new CheckBox();


                            {
                                checkBox.setSelected(true);

                                checkBox.setOnAction(event -> {

                                    int index = getIndex();

                                    if (index < 0
                                            || index >= getTableView()
                                            .getItems()
                                            .size()) {

                                        return;
                                    }


                                    Attendance attendance =
                                            getTableView()
                                                    .getItems()
                                                    .get(index);


                                    if (attendance == null) {
                                        return;
                                    }


                                    if (checkBox.isSelected()) {

                                        attendance.setStatus(
                                                "Present"
                                        );

                                    } else {

                                        attendance.setStatus(
                                                "Absent"
                                        );
                                    }


                                    updateSummary();
                                });
                            }


                            @Override
                            protected void updateItem(
                                    Boolean present,
                                    boolean empty) {

                                super.updateItem(
                                        present,
                                        empty
                                );


                                if (empty) {

                                    setGraphic(null);

                                } else {

                                    boolean isPresent =
                                            present != null
                                                    && present;


                                    checkBox.setSelected(
                                            isPresent
                                    );


                                    setGraphic(
                                            checkBox
                                    );
                                }
                            }
                        };
                    }
                }
        );


        // =====================================================
        // CENTER CHECKBOX
        // =====================================================

        statusColumn.setStyle(
                "-fx-alignment: CENTER;"
        );
    }


    // =========================================================
    // SELECTION LISTENERS
    // =========================================================

    private void setupSelectionListeners() {

        semesterBox.setOnAction(event -> {

            clearCourseBatchSection();

            String semester =
                    semesterBox.getValue();


            if (semester == null
                    || semester.isBlank()) {

                return;
            }


            loadCourseOptions(
                    semester
            );


            loadBatchOptions(
                    semester
            );
        });


        batchBox.setOnAction(event -> {

            sectionBox.getItems().clear();

            sectionBox.getSelectionModel()
                    .clearSelection();


            String semester =
                    semesterBox.getValue();

            String batch =
                    batchBox.getValue();


            if (semester == null
                    || batch == null
                    || semester.isBlank()
                    || batch.isBlank()) {

                return;
            }


            loadSectionOptions(
                    semester,
                    batch
            );
        });


        courseBox.setOnAction(event -> {

            attendanceList.clear();

            attendanceTable.refresh();

            updateSummary();
        });
    }


    // =========================================================
    // CLEAR COURSE / BATCH / SECTION
    // =========================================================

    private void clearCourseBatchSection() {

        courseBox.getItems().clear();

        batchBox.getItems().clear();

        sectionBox.getItems().clear();


        courseBox.getSelectionModel()
                .clearSelection();

        batchBox.getSelectionModel()
                .clearSelection();

        sectionBox.getSelectionModel()
                .clearSelection();


        attendanceList.clear();

        attendanceTable.refresh();

        updateSummary();
    }


    // =========================================================
    // LOAD SEMESTERS
    // =========================================================

    private void loadSemesterOptions() {

        semesterBox.getItems().clear();


        Set<String> semesters =
                new LinkedHashSet<>();


        for (Registration registration :
                registrationService.getAllRegistrations()) {


            String semester =
                    registration.getSemester();


            if (semester != null
                    && !semester.isBlank()) {

                semesters.add(
                        semester.trim()
                );
            }
        }


        semesterBox.getItems()
                .addAll(semesters);
    }


    // =========================================================
    // LOAD COURSES
    // =========================================================

    private void loadCourseOptions(
            String semester) {

        courseBox.getItems().clear();


        Set<String> courses =
                new LinkedHashSet<>();


        for (Registration registration :
                registrationService.getAllRegistrations()) {


            if (!matches(
                    registration.getSemester(),
                    semester)) {

                continue;
            }


            String courseCode =
                    registration.getCourseCode();


            if (courseCode != null
                    && !courseCode.isBlank()) {

                courses.add(
                        courseCode.trim()
                );
            }
        }


        courseBox.getItems()
                .addAll(courses);
    }


    // =========================================================
    // LOAD BATCHES
    // =========================================================

    private void loadBatchOptions(
            String semester) {

        batchBox.getItems().clear();


        Set<String> batches =
                new LinkedHashSet<>();


        for (Registration registration :
                registrationService.getAllRegistrations()) {


            if (!matches(
                    registration.getSemester(),
                    semester)) {

                continue;
            }


            String batch =
                    registration.getBatch();


            if (batch != null
                    && !batch.isBlank()) {

                batches.add(
                        batch.trim()
                );
            }
        }


        batchBox.getItems()
                .addAll(batches);
    }


    // =========================================================
    // LOAD SECTIONS
    // =========================================================

    private void loadSectionOptions(
            String semester,
            String batch) {

        sectionBox.getItems().clear();


        Set<String> sections =
                new LinkedHashSet<>();


        for (Registration registration :
                registrationService.getAllRegistrations()) {


            if (!matches(
                    registration.getSemester(),
                    semester)) {

                continue;
            }


            if (!matches(
                    registration.getBatch(),
                    batch)) {

                continue;
            }


            String section =
                    registration.getSection();


            if (section != null
                    && !section.isBlank()) {

                sections.add(
                        section.trim()
                );
            }
        }


        sectionBox.getItems()
                .addAll(sections);
    }


    // =========================================================
    // LOAD REGISTERED STUDENTS
    // =========================================================

    @FXML
    private void loadStudents() {

        String semester =
                semesterBox.getValue();

        String courseCode =
                courseBox.getValue();

        String batch =
                batchBox.getValue();

        String section =
                sectionBox.getValue();

        LocalDate selectedDate =
                datePicker.getValue();


        // =====================================================
        // VALIDATION
        // =====================================================

        if (semester == null
                || semester.isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a semester."
            );

            return;
        }


        if (courseCode == null
                || courseCode.isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a course."
            );

            return;
        }


        if (batch == null
                || batch.isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a batch."
            );

            return;
        }


        if (section == null
                || section.isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a section."
            );

            return;
        }


        if (selectedDate == null) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a date."
            );

            return;
        }


        String date =
                selectedDate.toString();


        // =====================================================
        // CLEAR PREVIOUS
        // =====================================================

        attendanceList.clear();


        // =====================================================
        // GET REGISTRATIONS
        // =====================================================

        List<Registration> registrations =
                registrationService
                        .getAllRegistrations();


        List<String> studentIds =
                new ArrayList<>();


        // =====================================================
        // FIND REGISTERED STUDENTS
        // =====================================================

        for (Registration registration :
                registrations) {


            if (!matches(
                    registration.getSemester(),
                    semester)) {

                continue;
            }


            if (!matches(
                    registration.getCourseCode(),
                    courseCode)) {

                continue;
            }


            if (!matches(
                    registration.getBatch(),
                    batch)) {

                continue;
            }


            if (!matches(
                    registration.getSection(),
                    section)) {

                continue;
            }


            String studentId =
                    registration.getStudentId();


            if (studentId != null
                    && !studentId.isBlank()
                    && !studentIds.contains(
                    studentId)) {

                studentIds.add(
                        studentId
                );
            }
        }


        // =====================================================
        // NO STUDENT
        // =====================================================

        if (studentIds.isEmpty()) {

            attendanceTable.setItems(
                    attendanceList
            );

            updateSummary();


            AlertUtil.warning(
                    "No Students",
                    "No registered students were found."
            );

            return;
        }


        // =====================================================
        // FIND TEACHER
        // =====================================================

        String teacherId = "";


        Course selectedCourse =
                findCourse(courseCode);


        if (selectedCourse != null
                && selectedCourse.getTeacherId() != null) {

            teacherId =
                    selectedCourse.getTeacherId();
        }


        // =====================================================
        // GENERATE IDs
        // =====================================================

        int nextId =
                getNextAttendanceNumber();


        // =====================================================
        // CREATE ATTENDANCE ROWS
        // =====================================================

        for (String studentId :
                studentIds) {


            Attendance existing =
                    findExistingAttendance(
                            studentId,
                            courseCode,
                            semester,
                            batch,
                            section,
                            date
                    );


            // =================================================
            // ALREADY SAVED
            // =================================================

            if (existing != null) {

                attendanceList.add(
                        existing
                );

                continue;
            }


            // =================================================
            // NEW RECORD
            // =================================================

            Attendance attendance =
                    new Attendance();


            attendance.setAttendanceId(
                    "ATT-"
                            + String.format(
                            "%04d",
                            nextId
                    )
            );


            nextId++;


            attendance.setStudentId(
                    studentId
            );


            attendance.setCourseCode(
                    courseCode
            );


            attendance.setTeacherId(
                    teacherId
            );


            attendance.setSemester(
                    semester
            );


            attendance.setBatch(
                    batch
            );


            attendance.setSection(
                    section
            );


            attendance.setLabSection(
                    findLabSection(
                            studentId,
                            courseCode,
                            semester,
                            batch,
                            section
                    )
            );


            attendance.setDate(
                    date
            );


            // =================================================
            // EVERYONE PRESENT BY DEFAULT
            // =================================================

            attendance.setStatus(
                    "Present"
            );


            attendanceList.add(
                    attendance
            );
        }


        // =====================================================
        // UPDATE TABLE
        // =====================================================

        attendanceTable.setItems(
                attendanceList
        );


        attendanceTable.refresh();


        updateSummary();


        // =====================================================
        // ALERT
        // =====================================================

        Platform.runLater(() -> {

            AlertUtil.information(
                    "Students Loaded",
                    studentIds.size()
                            + " registered student(s) loaded."
            );
        });
    }


    // =========================================================
    // SAVE ATTENDANCE
    // =========================================================

    @FXML
    private void saveAttendance() {

        if (attendanceList.isEmpty()) {

            AlertUtil.warning(
                    "No Data",
                    "Please load students first."
            );

            return;
        }


        int newRecords = 0;

        int updatedRecords = 0;


        for (Attendance attendance :
                attendanceList) {


            // =================================================
            // CHECKBOX LOGIC
            // =================================================

            if (attendance.getStatus() == null
                    || attendance.getStatus().isBlank()) {

                attendance.setStatus(
                        "Present"
                );
            }


            Attendance existing =
                    findExistingAttendance(
                            attendance.getStudentId(),
                            attendance.getCourseCode(),
                            attendance.getSemester(),
                            attendance.getBatch(),
                            attendance.getSection(),
                            attendance.getDate()
                    );


            // =================================================
            // UPDATE
            // =================================================

            if (existing != null) {

                attendance.setAttendanceId(
                        existing.getAttendanceId()
                );


                attendanceService
                        .updateAttendance(
                                attendance
                        );


                updatedRecords++;


            } else {

                // =============================================
                // ADD
                // =============================================

                if (attendance.getAttendanceId() == null
                        || attendance.getAttendanceId()
                        .isBlank()) {

                    attendance.setAttendanceId(
                            generateAttendanceId()
                    );
                }


                attendanceService
                        .addAttendance(
                                attendance
                        );


                newRecords++;
            }
        }


        updateSummary();


        attendanceTable.refresh();


        AlertUtil.information(
                "Attendance Saved",
                "Attendance saved successfully.\n\n"
                        + "New Records: "
                        + newRecords
                        + "\nUpdated Records: "
                        + updatedRecords
        );
    }


    // =========================================================
    // FIND EXISTING ATTENDANCE
    // =========================================================

    private Attendance findExistingAttendance(
            String studentId,
            String courseCode,
            String semester,
            String batch,
            String section,
            String date) {


        for (Attendance attendance :
                attendanceService
                        .getAllAttendance()) {


            if (!matches(
                    attendance.getStudentId(),
                    studentId)) {

                continue;
            }


            if (!matches(
                    attendance.getCourseCode(),
                    courseCode)) {

                continue;
            }


            if (!matches(
                    attendance.getSemester(),
                    semester)) {

                continue;
            }


            if (!matches(
                    attendance.getBatch(),
                    batch)) {

                continue;
            }


            if (!matches(
                    attendance.getSection(),
                    section)) {

                continue;
            }


            if (!matches(
                    attendance.getDate(),
                    date)) {

                continue;
            }


            return attendance;
        }


        return null;
    }


    // =========================================================
    // FIND COURSE
    // =========================================================

    private Course findCourse(
            String courseCode) {


        for (Course course :
                courseRepository
                        .loadCourses()) {


            if (matches(
                    course.getCourseCode(),
                    courseCode)) {

                return course;
            }
        }


        return null;
    }


    // =========================================================
    // FIND LAB SECTION
    // =========================================================

    private String findLabSection(
            String studentId,
            String courseCode,
            String semester,
            String batch,
            String section) {


        for (Registration registration :
                registrationService
                        .getAllRegistrations()) {


            if (!matches(
                    registration.getStudentId(),
                    studentId)) {

                continue;
            }


            if (!matches(
                    registration.getCourseCode(),
                    courseCode)) {

                continue;
            }


            if (!matches(
                    registration.getSemester(),
                    semester)) {

                continue;
            }


            if (!matches(
                    registration.getBatch(),
                    batch)) {

                continue;
            }


            if (!matches(
                    registration.getSection(),
                    section)) {

                continue;
            }


            if (registration.getLabSection() != null) {

                return registration
                        .getLabSection();
            }
        }


        return "";
    }


    // =========================================================
    // NEXT ID
    // =========================================================

    private int getNextAttendanceNumber() {

        int maxNumber = 0;


        for (Attendance attendance :
                attendanceService
                        .getAllAttendance()) {


            String id =
                    attendance.getAttendanceId();


            if (id == null
                    || !id.startsWith("ATT-")) {

                continue;
            }


            try {

                int number =
                        Integer.parseInt(
                                id.substring(4)
                        );


                if (number > maxNumber) {

                    maxNumber =
                            number;
                }


            } catch (NumberFormatException ignored) {
            }
        }


        return maxNumber + 1;
    }


    // =========================================================
    // GENERATE ID
    // =========================================================

    private String generateAttendanceId() {

        return "ATT-"
                + String.format(
                "%04d",
                getNextAttendanceNumber()
        );
    }


    // =========================================================
    // SUMMARY
    // =========================================================

    private void updateSummary() {

        int total = 0;

        int present = 0;

        int absent = 0;


        for (Attendance attendance :
                attendanceList) {


            total++;


            String status =
                    attendance.getStatus();


            if (status == null) {
                continue;
            }


            if (status.equalsIgnoreCase(
                    "Present")) {

                present++;

            } else if (status.equalsIgnoreCase(
                    "Absent")) {

                absent++;
            }
        }


        totalStudentLabel.setText(
                String.valueOf(total)
        );


        presentLabel.setText(
                String.valueOf(present)
        );


        absentLabel.setText(
                String.valueOf(absent)
        );
    }


    // =========================================================
    // CLEAR
    // =========================================================

    @FXML
    private void clearFields() {

        semesterBox.getSelectionModel()
                .clearSelection();


        courseBox.getItems().clear();

        batchBox.getItems().clear();

        sectionBox.getItems().clear();


        courseBox.getSelectionModel()
                .clearSelection();

        batchBox.getSelectionModel()
                .clearSelection();

        sectionBox.getSelectionModel()
                .clearSelection();


        datePicker.setValue(null);


        attendanceList.clear();


        attendanceTable.setItems(
                attendanceList
        );


        attendanceTable.refresh();


        updateSummary();


        loadSemesterOptions();
    }


    // =========================================================
    // REFRESH
    // =========================================================

    @FXML
    private void refreshTable() {

        if (semesterBox.getValue() != null
                && courseBox.getValue() != null
                && batchBox.getValue() != null
                && sectionBox.getValue() != null
                && datePicker.getValue() != null) {

            loadStudents();

        } else {

            attendanceTable.refresh();

            updateSummary();
        }
    }


    // =========================================================
    // MATCH
    // =========================================================

    private boolean matches(
            String actual,
            String expected) {


        if (actual == null
                || expected == null) {

            return false;
        }


        return actual
                .trim()
                .equalsIgnoreCase(
                        expected.trim()
                );
    }


    // =========================================================
    // SAFE
    // =========================================================

    private String safe(
            String value) {

        return value == null
                ? ""
                : value;
    }
}