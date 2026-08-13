package com.sms.controller;

import com.sms.model.Course;
import com.sms.model.Registration;
import com.sms.model.Result;
import com.sms.service.CourseService;
import com.sms.service.RegistrationService;
import com.sms.service.ResultService;
import com.sms.utils.AlertUtil;
import com.sms.utils.GradeCalculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

public class ResultController {

    @FXML private TextField studentIdField;
    @FXML private ComboBox<String> sectionBox;
    @FXML private ComboBox<String> semesterBox;
    @FXML private Button loadCoursesButton;

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> courseCodeColumn;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private TableColumn<Course, Double> courseCreditColumn;
    @FXML private TableColumn<Course, String> courseTypeColumn;

    @FXML private Label selectedCourseLabel;
    @FXML private Label selectedCourseTypeLabel;
    @FXML private Label selectedCourseCreditLabel;

    @FXML private VBox theoryPane;

    @FXML private TextField theoryAttendanceField;
    @FXML private TextField quiz1Field;
    @FXML private TextField quiz2Field;
    @FXML private TextField quiz3Field;
    @FXML private TextField quizAverageField;
    @FXML private TextField midtermField;
    @FXML private TextField presentationField;
    @FXML private TextField assignmentField;
    @FXML private TextField theoryFinalField;

    @FXML private VBox labPane;

    @FXML private TextField labAttendanceField;
    @FXML private TextField classPerformanceField;
    @FXML private TextField labReportField;
    @FXML private TextField labFinalField;

    @FXML private TextField totalMarksField;
    @FXML private TextField gradeField;
    @FXML private TextField gpaField;

    @FXML private Label semesterGPALabel;
    @FXML private Label overallCGPALabel;

    @FXML private TableView<Result> resultTable;
    @FXML private TableColumn<Result, String> resultStudentColumn;
    @FXML private TableColumn<Result, String> resultCourseColumn;
    @FXML private TableColumn<Result, String> resultSemesterColumn;
    @FXML private TableColumn<Result, Double> resultMarksColumn;
    @FXML private TableColumn<Result, String> resultGradeColumn;
    @FXML private TableColumn<Result, Double> resultGpaColumn;


    private final ResultService resultService =
            new ResultService();

    private final RegistrationService registrationService =
            new RegistrationService();

    private final CourseService courseService =
            new CourseService();


    private final ObservableList<Course> registeredCourses =
            FXCollections.observableArrayList();

    private final ObservableList<Result> results =
            FXCollections.observableArrayList();

    private Course selectedCourse;


    // =========================================================
    // INITIALIZE
    // =========================================================

    @FXML
    public void initialize() {

        setupSemesterBox();

        setupSectionBox();

        setupCourseTable();

        setupResultTable();

        setupListeners();

        hideAssessmentPanes();

        refreshResultHistory();

        updateGPAInformation();
    }


    // =========================================================
    // SEMESTER
    // =========================================================

    private void setupSemesterBox() {

        semesterBox.getItems().setAll(
                "Spring-2026",
                "Summer-2026",
                "Fall-2026",
                "Spring-2027",
                "Summer-2027",
                "Fall-2027",
                "Spring-2028",
                "Summer-2028",
                "Fall-2028"
        );
    }


    // =========================================================
    // SECTION
    // =========================================================

    private void setupSectionBox() {

        sectionBox.getItems().setAll(
                "68_A",
                "68_B",
                "68_C",
                "68_D",
                "68_E",
                "68_F",
                "68_G",
                "68_H",
                "68_I",
                "68_J",
                "68_K",
                "68_L",
                "68_M",
                "68_N",
                "68_O"
        );
    }


    // =========================================================
    // COURSE TABLE
    // =========================================================

    private void setupCourseTable() {

        courseCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseCode")
        );

        courseNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseName")
        );

        courseCreditColumn.setCellValueFactory(
                new PropertyValueFactory<>("credit")
        );

        courseTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseType")
        );

        courseTable.setItems(
                registeredCourses
        );

        courseTable.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, oldCourse, newCourse) -> {

                            if (newCourse != null) {

                                selectCourse(newCourse);
                            }
                        }
                );
    }


    // =========================================================
    // RESULT TABLE
    // =========================================================

    private void setupResultTable() {

        resultStudentColumn.setCellValueFactory(
                new PropertyValueFactory<>("studentId")
        );

        resultCourseColumn.setCellValueFactory(
                new PropertyValueFactory<>("courseCode")
        );

        resultSemesterColumn.setCellValueFactory(
                new PropertyValueFactory<>("semester")
        );


        // =====================================================
        // TOTAL MARKS
        // =====================================================

        resultMarksColumn.setCellValueFactory(
                new PropertyValueFactory<>("totalMarks")
        );

        resultMarksColumn.setCellFactory(
                column -> new TableCell<Result, Double>() {

                    @Override
                    protected void updateItem(
                            Double value,
                            boolean empty) {

                        super.updateItem(
                                value,
                                empty
                        );

                        if (empty || value == null) {

                            setText(null);

                        } else {

                            setText(
                                    String.format(
                                            "%.2f",
                                            value
                                    )
                            );
                        }
                    }
                }
        );


        // =====================================================
        // GRADE
        // =====================================================

        resultGradeColumn.setCellValueFactory(
                new PropertyValueFactory<>("grade")
        );


        // =====================================================
        // GPA
        // =====================================================

        resultGpaColumn.setCellValueFactory(
                new PropertyValueFactory<>("gpa")
        );

        resultGpaColumn.setCellFactory(
                column -> new TableCell<Result, Double>() {

                    @Override
                    protected void updateItem(
                            Double value,
                            boolean empty) {

                        super.updateItem(
                                value,
                                empty
                        );

                        if (empty || value == null) {

                            setText(null);

                        } else {

                            setText(
                                    String.format(
                                            "%.2f",
                                            value
                                    )
                            );
                        }
                    }
                }
        );


        resultTable.setItems(
                results
        );
    }


    // =========================================================
    // LISTENERS
    // =========================================================

    private void setupListeners() {

        quiz1Field.textProperty()
                .addListener(
                        (o, a, b) -> calculateQuizAverage()
                );

        quiz2Field.textProperty()
                .addListener(
                        (o, a, b) -> calculateQuizAverage()
                );

        quiz3Field.textProperty()
                .addListener(
                        (o, a, b) -> calculateQuizAverage()
                );


        theoryAttendanceField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );

        midtermField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );

        presentationField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );

        assignmentField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );

        theoryFinalField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );


        labAttendanceField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );

        classPerformanceField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );

        labReportField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );

        labFinalField.textProperty()
                .addListener(
                        (o, a, b) -> calculateCurrentResult()
                );
    }


    // =========================================================
    // LOAD REGISTERED COURSES
    // =========================================================

    @FXML
    private void loadRegisteredCourses() {

        String studentId =
                studentIdField.getText()
                        .trim();

        String selectedSection =
                sectionBox.getValue();

        String semester =
                semesterBox.getValue();


        if (studentId.isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please enter Student ID."
            );

            return;
        }


        if (selectedSection == null
                || selectedSection.isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select Section."
            );

            return;
        }


        if (semester == null
                || semester.isBlank()) {

            AlertUtil.warning(
                    "Validation",
                    "Please select Semester."
            );

            return;
        }


        /*
         * IMPORTANT FIX
         *
         * UI value:
         *
         *     68_O
         *
         * Database:
         *
         *     batch = 68
         *     section = O
         *
         * Therefore we split 68_O into:
         *
         *     batch   = 68
         *     section = O
         */

        String[] sectionData =
                parseSection(selectedSection);

        String batch =
                sectionData[0];

        String section =
                sectionData[1];


        registeredCourses.clear();

        selectedCourse = null;

        clearAssessmentFields();

        hideAssessmentPanes();


        List<Registration> registrations =
                registrationService
                        .getAllRegistrations();


        boolean studentFound = false;


        // =====================================================
        // CHECK STUDENT REGISTRATION
        // =====================================================

        for (Registration registration :
                registrations) {

            if (registration == null) {
                continue;
            }


            if (same(
                    registration.getStudentId(),
                    studentId
            )
                    && same(
                    registration.getSemester(),
                    semester
            )
                    && same(
                    registration.getSection(),
                    section
            )
                    && (
                    batch.isBlank()
                            || same(
                            registration.getBatch(),
                            batch
                    )
            )) {

                studentFound = true;

                break;
            }
        }


        if (!studentFound) {

            AlertUtil.warning(
                    "Student Not Found",

                    "No registration found for this student "
                            + "in the selected section and semester."
                            + "\n\n"
                            + "Student ID: "
                            + studentId
                            + "\n"
                            + "Batch: "
                            + batch
                            + "\n"
                            + "Section: "
                            + section
                            + "\n"
                            + "Semester: "
                            + semester
            );

            return;
        }


        // =====================================================
        // LOAD COURSES
        // =====================================================

        for (Registration registration :
                registrations) {

            if (registration == null) {
                continue;
            }


            if (same(
                    registration.getStudentId(),
                    studentId
            )
                    && same(
                    registration.getSemester(),
                    semester
            )
                    && same(
                    registration.getSection(),
                    section
            )
                    && (
                    batch.isBlank()
                            || same(
                            registration.getBatch(),
                            batch
                    )
            )
                    && registration.getCourseCode() != null) {


                Course course =
                        courseService.searchCourse(
                                registration.getCourseCode()
                        );


                if (course != null) {

                    registeredCourses.add(
                            course
                    );
                }
            }
        }


        if (registeredCourses.isEmpty()) {

            AlertUtil.information(
                    "No Courses",
                    "This student has no registered courses "
                            + "for the selected semester."
            );

            return;
        }


        refreshResultHistory();

        updateGPAInformation();


        AlertUtil.information(
                "Courses Loaded",
                registeredCourses.size()
                        + " registered course(s) found."
        );
    }


    // =========================================================
    // PARSE SECTION
    // =========================================================

    private String[] parseSection(
            String value) {

        if (value == null) {

            return new String[]{
                    "",
                    ""
            };
        }


        String section =
                value.trim()
                        .toUpperCase();


        int underscore =
                section.lastIndexOf('_');


        if (underscore >= 0
                && underscore < section.length() - 1) {

            String batch =
                    section.substring(
                            0,
                            underscore
                    ).trim();


            String actualSection =
                    section.substring(
                            underscore + 1
                    ).trim();


            return new String[]{
                    batch,
                    actualSection
            };
        }


        return new String[]{
                "",
                section
        };
    }


    // =========================================================
    // STRING COMPARISON
    // =========================================================

    private boolean same(
            String first,
            String second) {

        if (first == null
                || second == null) {

            return false;
        }


        return first.trim()
                .equalsIgnoreCase(
                        second.trim()
                );
    }


    // =========================================================
    // SELECT COURSE
    // =========================================================

    private void selectCourse(
            Course course) {

        selectedCourse = course;


        selectedCourseLabel.setText(
                course.getCourseCode()
                        + " - "
                        + course.getCourseName()
        );


        selectedCourseTypeLabel.setText(
                course.getCourseType() == null
                        ? "THEORY"
                        : course.getCourseType()
        );


        selectedCourseCreditLabel.setText(
                String.valueOf(
                        course.getCredit()
                )
        );


        clearAssessmentFields();


        Result existingResult =
                resultService.findResult(
                        studentIdField.getText()
                                .trim(),

                        course.getCourseCode(),

                        semesterBox.getValue()
                );


        if ("LAB".equalsIgnoreCase(
                course.getCourseType())) {

            showLabPane();


            if (existingResult != null) {

                loadLabResult(
                        existingResult
                );
            }

        } else {

            showTheoryPane();


            if (existingResult != null) {

                loadTheoryResult(
                        existingResult
                );
            }
        }


        calculateCurrentResult();
    }


    // =========================================================
    // HIDE PANES
    // =========================================================

    private void hideAssessmentPanes() {

        theoryPane.setVisible(false);

        theoryPane.setManaged(false);


        labPane.setVisible(false);

        labPane.setManaged(false);
    }


    // =========================================================
    // THEORY PANE
    // =========================================================

    private void showTheoryPane() {

        theoryPane.setVisible(true);

        theoryPane.setManaged(true);


        labPane.setVisible(false);

        labPane.setManaged(false);
    }


    // =========================================================
    // LAB PANE
    // =========================================================

    private void showLabPane() {

        labPane.setVisible(true);

        labPane.setManaged(true);


        theoryPane.setVisible(false);

        theoryPane.setManaged(false);
    }


    // =========================================================
    // QUIZ AVERAGE
    // =========================================================

    private void calculateQuizAverage() {

        try {

            String q1Text =
                    quiz1Field.getText().trim();

            String q2Text =
                    quiz2Field.getText().trim();

            String q3Text =
                    quiz3Field.getText().trim();


            if (q1Text.isBlank()
                    && q2Text.isBlank()
                    && q3Text.isBlank()) {

                quizAverageField.clear();

                calculateCurrentResult();

                return;
            }


            double q1 =
                    value(q1Text);

            double q2 =
                    value(q2Text);

            double q3 =
                    value(q3Text);


            double average =
                    (q1 + q2 + q3) / 3.0;


            quizAverageField.setText(
                    String.format(
                            "%.2f",
                            average
                    )
            );


            calculateCurrentResult();


        } catch (Exception e) {

            quizAverageField.clear();
        }
    }


    // =========================================================
    // CALCULATE RESULT
    // =========================================================

    private void calculateCurrentResult() {

        if (selectedCourse == null) {

            return;
        }


        try {

            double total;


            if ("LAB".equalsIgnoreCase(
                    selectedCourse.getCourseType())) {


                total =
                        value(labAttendanceField)
                                + value(classPerformanceField)
                                + value(labReportField)
                                + value(labFinalField);


            } else {


                total =
                        value(theoryAttendanceField)
                                + value(quizAverageField)
                                + value(midtermField)
                                + value(presentationField)
                                + value(assignmentField)
                                + value(theoryFinalField);
            }


            totalMarksField.setText(
                    String.format(
                            "%.2f",
                            total
                    )
            );


            if (total > 100) {

                gradeField.setText(
                        "Invalid"
                );

                gpaField.setText(
                        "0.00"
                );

                return;
            }


            gradeField.setText(
                    GradeCalculator.calculateGrade(
                            total
                    )
            );


            gpaField.setText(
                    String.format(
                            "%.2f",
                            GradeCalculator.calculateGPA(
                                    total
                            )
                    )
            );


        } catch (Exception e) {

            totalMarksField.clear();

            gradeField.clear();

            gpaField.clear();
        }
    }


    // =========================================================
    // GET VALUE
    // =========================================================

    private double value(
            String value) {

        if (value == null
                || value.isBlank()) {

            return 0.0;
        }


        return Double.parseDouble(
                value.trim()
        );
    }


    private double value(
            TextField field) {

        if (field == null) {

            return 0.0;
        }


        return value(
                field.getText()
        );
    }


    // =========================================================
    // SAVE RESULT
    // =========================================================

    @FXML
    private void saveResult() {

        if (selectedCourse == null) {

            AlertUtil.warning(
                    "Validation",
                    "Please load and select a course first."
            );

            return;
        }


        String studentId =
                studentIdField.getText()
                        .trim();

        String semester =
                semesterBox.getValue();


        if (studentId.isBlank()
                || semester == null) {

            AlertUtil.warning(
                    "Validation",
                    "Student ID and Semester are required."
            );

            return;
        }


        try {

            Result result =
                    new Result(
                            studentId,
                            selectedCourse.getCourseCode(),
                            semester
                    );


            double total;


            if ("LAB".equalsIgnoreCase(
                    selectedCourse.getCourseType())) {


                double attendance =
                        value(
                                labAttendanceField
                        );

                double classPerformance =
                        value(
                                classPerformanceField
                        );

                double labReport =
                        value(
                                labReportField
                        );

                double finalExam =
                        value(
                                labFinalField
                        );


                if (!validRange(
                        attendance,
                        0,
                        10
                )
                        || !validRange(
                        classPerformance,
                        0,
                        25
                )
                        || !validRange(
                        labReport,
                        0,
                        25
                )
                        || !validRange(
                        finalExam,
                        0,
                        40
                )) {

                    AlertUtil.warning(
                            "Invalid Marks",
                            "Please check the Lab mark limits."
                    );

                    return;
                }


                result.setAttendance(
                        attendance
                );

                result.setClassPerformance(
                        classPerformance
                );

                result.setLabReport(
                        labReport
                );

                result.setFinalExam(
                        finalExam
                );


                total =
                        attendance
                                + classPerformance
                                + labReport
                                + finalExam;


            } else {


                double attendance =
                        value(
                                theoryAttendanceField
                        );

                double quiz1 =
                        value(quiz1Field);

                double quiz2 =
                        value(quiz2Field);

                double quiz3 =
                        value(quiz3Field);


                double quizAverage =
                        (
                                quiz1
                                        + quiz2
                                        + quiz3
                        ) / 3.0;


                double midterm =
                        value(midtermField);

                double presentation =
                        value(presentationField);

                double assignment =
                        value(assignmentField);

                double finalExam =
                        value(theoryFinalField);


                if (!validRange(
                        attendance,
                        0,
                        7
                )
                        || !validRange(
                        quizAverage,
                        0,
                        15
                )
                        || !validRange(
                        midterm,
                        0,
                        25
                )
                        || !validRange(
                        presentation,
                        0,
                        8
                )
                        || !validRange(
                        assignment,
                        0,
                        5
                )
                        || !validRange(
                        finalExam,
                        0,
                        40
                )) {

                    AlertUtil.warning(
                            "Invalid Marks",
                            "Please check the Theory mark limits."
                    );

                    return;
                }


                result.setAttendance(
                        attendance
                );

                result.setQuiz1(
                        quiz1
                );

                result.setQuiz2(
                        quiz2
                );

                result.setQuiz3(
                        quiz3
                );

                result.setQuizAverage(
                        quizAverage
                );

                result.setMidterm(
                        midterm
                );

                result.setPresentation(
                        presentation
                );

                result.setAssignment(
                        assignment
                );

                result.setFinalExam(
                        finalExam
                );


                total =
                        attendance
                                + quizAverage
                                + midterm
                                + presentation
                                + assignment
                                + finalExam;
            }


            result.setTotalMarks(
                    total
            );


            result.setGrade(
                    GradeCalculator.calculateGrade(
                            total
                    )
            );


            result.setGpa(
                    GradeCalculator.calculateGPA(
                            total
                    )
            );


            Result existing =
                    resultService.findResult(
                            studentId,
                            selectedCourse.getCourseCode(),
                            semester
                    );


            if (existing != null) {

                resultService.updateResult(
                        result
                );


                AlertUtil.information(
                        "Success",
                        "Result updated successfully."
                );


            } else {

                if (resultService.addResult(
                        result
                )) {

                    AlertUtil.information(
                            "Success",
                            "Result saved successfully."
                    );

                } else {

                    AlertUtil.error(
                            "Error",
                            "Could not save result."
                    );

                    return;
                }
            }


            refreshResultHistory();

            updateGPAInformation();


        } catch (NumberFormatException e) {

            AlertUtil.error(
                    "Invalid Input",
                    "Please enter valid numbers only."
            );
        }
    }


    // =========================================================
    // DELETE RESULT
    // =========================================================

    @FXML
    private void deleteResult() {

        if (selectedCourse == null) {

            AlertUtil.warning(
                    "Validation",
                    "Please select a course first."
            );

            return;
        }


        boolean deleted =
                resultService.deleteResult(
                        studentIdField.getText()
                                .trim(),

                        selectedCourse.getCourseCode(),

                        semesterBox.getValue()
                );


        if (deleted) {

            AlertUtil.information(
                    "Success",
                    "Result deleted successfully."
            );


            clearAssessmentFields();

            refreshResultHistory();

            updateGPAInformation();


        } else {

            AlertUtil.error(
                    "Error",
                    "No result found for this course."
            );
        }
    }


    // =========================================================
    // LOAD THEORY RESULT
    // =========================================================

    private void loadTheoryResult(
            Result result) {

        theoryAttendanceField.setText(
                format(
                        result.getAttendance()
                )
        );

        quiz1Field.setText(
                format(
                        result.getQuiz1()
                )
        );

        quiz2Field.setText(
                format(
                        result.getQuiz2()
                )
        );

        quiz3Field.setText(
                format(
                        result.getQuiz3()
                )
        );

        quizAverageField.setText(
                format(
                        result.getQuizAverage()
                )
        );

        midtermField.setText(
                format(
                        result.getMidterm()
                )
        );

        presentationField.setText(
                format(
                        result.getPresentation()
                )
        );

        assignmentField.setText(
                format(
                        result.getAssignment()
                )
        );

        theoryFinalField.setText(
                format(
                        result.getFinalExam()
                )
        );

        totalMarksField.setText(
                format(
                        result.getTotalMarks()
                )
        );

        gradeField.setText(
                result.getGrade()
        );

        gpaField.setText(
                format(
                        result.getGpa()
                )
        );
    }


    // =========================================================
    // LOAD LAB RESULT
    // =========================================================

    private void loadLabResult(
            Result result) {

        labAttendanceField.setText(
                format(
                        result.getAttendance()
                )
        );

        classPerformanceField.setText(
                format(
                        result.getClassPerformance()
                )
        );

        labReportField.setText(
                format(
                        result.getLabReport()
                )
        );

        labFinalField.setText(
                format(
                        result.getFinalExam()
                )
        );

        totalMarksField.setText(
                format(
                        result.getTotalMarks()
                )
        );

        gradeField.setText(
                result.getGrade()
        );

        gpaField.setText(
                format(
                        result.getGpa()
                )
        );
    }


    // =========================================================
    // RESULT HISTORY
    // =========================================================

    private void refreshResultHistory() {

        if (studentIdField == null) {

            return;
        }


        String studentId =
                studentIdField.getText()
                        .trim();


        if (studentId.isBlank()) {

            results.setAll(
                    resultService.getAllResults()
            );

        } else {

            results.setAll(
                    resultService.getStudentResults(
                            studentId
                    )
            );
        }


        if (resultTable != null) {

            resultTable.setItems(
                    results
            );
        }
    }


    // =========================================================
    // GPA
    // =========================================================

    private void updateGPAInformation() {

        if (semesterGPALabel == null
                || overallCGPALabel == null
                || studentIdField == null) {

            return;
        }


        String studentId =
                studentIdField.getText()
                        .trim();

        String semester =
                semesterBox.getValue();


        if (studentId.isBlank()
                || semester == null
                || semester.isBlank()) {

            semesterGPALabel.setText(
                    "0.00"
            );

            overallCGPALabel.setText(
                    "0.00"
            );

            return;
        }


        double semesterGPA =
                resultService.calculateSemesterGPA(
                        studentId,
                        semester
                );


        double overallCGPA =
                resultService.calculateOverallCGPA(
                        studentId
                );


        semesterGPALabel.setText(
                String.format(
                        "%.2f",
                        semesterGPA
                )
        );


        overallCGPALabel.setText(
                String.format(
                        "%.2f",
                        overallCGPA
                )
        );
    }


    // =========================================================
    // CLEAR ASSESSMENT FIELDS
    // =========================================================

    private void clearAssessmentFields() {

        theoryAttendanceField.clear();

        quiz1Field.clear();

        quiz2Field.clear();

        quiz3Field.clear();

        quizAverageField.clear();

        midtermField.clear();

        presentationField.clear();

        assignmentField.clear();

        theoryFinalField.clear();


        labAttendanceField.clear();

        classPerformanceField.clear();

        labReportField.clear();

        labFinalField.clear();


        totalMarksField.clear();

        gradeField.clear();

        gpaField.clear();
    }


    // =========================================================
    // CLEAR ALL
    // =========================================================

    @FXML
    private void clearFields() {

        studentIdField.clear();

        sectionBox.getSelectionModel()
                .clearSelection();

        semesterBox.getSelectionModel()
                .clearSelection();

        registeredCourses.clear();

        courseTable.getSelectionModel()
                .clearSelection();

        selectedCourse = null;


        selectedCourseLabel.setText(
                "No Course Selected"
        );

        selectedCourseTypeLabel.setText(
                "-"
        );

        selectedCourseCreditLabel.setText(
                "-"
        );


        clearAssessmentFields();

        hideAssessmentPanes();

        refreshResultHistory();

        updateGPAInformation();
    }


    // =========================================================
    // VALIDATE RANGE
    // =========================================================

    private boolean validRange(
            double value,
            double minimum,
            double maximum) {

        return value >= minimum
                && value <= maximum;
    }


    // =========================================================
    // FORMAT
    // =========================================================

    private String format(
            double value) {

        return String.format(
                "%.2f",
                value
        );
    }
}