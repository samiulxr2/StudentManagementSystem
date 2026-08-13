package com.sms.controller;

import com.sms.repository.CourseRepository;
import com.sms.repository.ResultRepository;
import com.sms.repository.StudentRepository;
import com.sms.repository.TeacherRepository;
import com.sms.session.Session;
import com.sms.utils.AlertUtil;
import com.sms.utils.SceneManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * =========================================================
 * DASHBOARD CONTROLLER
 * =========================================================
 *
 * Handles:
 *
 * Dashboard
 * Students
 * Teachers
 * Courses
 * Registration
 * Attendance
 * Results
 * Settings
 *
 * Module pages are loaded inside contentPane.
 *
 * =========================================================
 */
public class DashboardController {


    // =========================================================
    // TOP BAR
    // =========================================================

    @FXML
    private Label welcomeLabel;


    // =========================================================
    // DASHBOARD STATISTICS
    // =========================================================

    @FXML
    private Label studentCountLabel;

    @FXML
    private Label teacherCountLabel;

    @FXML
    private Label courseCountLabel;

    @FXML
    private Label resultCountLabel;


    // =========================================================
    // RECENT ACTIVITY
    // =========================================================

    @FXML
    private ListView<String> activityList;


    // =========================================================
    // MAIN CONTENT
    // =========================================================

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox dashboardPane;

    @FXML
    private StackPane contentPane;


    // =========================================================
    // REPOSITORIES
    // =========================================================

    private final StudentRepository studentRepository =
            new StudentRepository();

    private final TeacherRepository teacherRepository =
            new TeacherRepository();

    private final CourseRepository courseRepository =
            new CourseRepository();

    private final ResultRepository resultRepository =
            new ResultRepository();


    // =========================================================
    // INITIALIZE
    // =========================================================

    @FXML
    public void initialize() {

        try {

            // -------------------------------------------------
            // USER
            // -------------------------------------------------

            if (welcomeLabel != null) {

                if (Session.getCurrentUser() != null) {

                    welcomeLabel.setText(
                            "Welcome, "
                                    + Session
                                    .getCurrentUser()
                                    .getUsername()
                    );

                } else {

                    welcomeLabel.setText(
                            "Welcome"
                    );
                }
            }


            // -------------------------------------------------
            // SHOW DASHBOARD
            // -------------------------------------------------

            showDashboard();


        } catch (Exception e) {

            /*
             * Do not allow dashboard initialization errors
             * to completely break the login -> dashboard flow.
             */

            e.printStackTrace();
        }
    }


    // =========================================================
    // REFRESH DASHBOARD
    // =========================================================

    @FXML
    private void refreshDashboard() {

        try {

            // -------------------------------------------------
            // LOAD COUNTS
            // -------------------------------------------------

            int studentCount =
                    studentRepository
                            .loadStudents()
                            .size();

            int teacherCount =
                    teacherRepository
                            .loadTeachers()
                            .size();

            int courseCount =
                    courseRepository
                            .loadCourses()
                            .size();

            int resultCount =
                    resultRepository
                            .loadResults()
                            .size();


            // -------------------------------------------------
            // UPDATE COUNTS
            // -------------------------------------------------

            if (studentCountLabel != null) {

                studentCountLabel.setText(
                        String.valueOf(studentCount)
                );
            }


            if (teacherCountLabel != null) {

                teacherCountLabel.setText(
                        String.valueOf(teacherCount)
                );
            }


            if (courseCountLabel != null) {

                courseCountLabel.setText(
                        String.valueOf(courseCount)
                );
            }


            if (resultCountLabel != null) {

                resultCountLabel.setText(
                        String.valueOf(resultCount)
                );
            }


            // -------------------------------------------------
            // RECENT ACTIVITY
            // -------------------------------------------------

            if (activityList != null) {

                activityList
                        .getItems()
                        .clear();


                activityList
                        .getItems()
                        .add(
                                "Dashboard Loaded"
                        );


                activityList
                        .getItems()
                        .add(
                                "Students : "
                                        + studentCount
                        );


                activityList
                        .getItems()
                        .add(
                                "Teachers : "
                                        + teacherCount
                        );


                activityList
                        .getItems()
                        .add(
                                "Courses : "
                                        + courseCount
                        );


                activityList
                        .getItems()
                        .add(
                                "Results : "
                                        + resultCount
                        );
            }


        } catch (Exception e) {

            e.printStackTrace();


            if (activityList != null) {

                activityList
                        .getItems()
                        .clear();


                activityList
                        .getItems()
                        .add(
                                "Unable to load dashboard data."
                        );
            }
        }
    }


    // =========================================================
    // SHOW DASHBOARD
    // =========================================================

    @FXML
    private void showDashboard() {

        try {

            // -------------------------------------------------
            // CLEAR MODULE CONTENT
            // -------------------------------------------------

            if (contentPane != null) {

                contentPane
                        .getChildren()
                        .clear();


                contentPane.setVisible(
                        false
                );


                contentPane.setManaged(
                        false
                );
            }


            // -------------------------------------------------
            // SHOW DASHBOARD
            // -------------------------------------------------

            if (dashboardPane != null) {

                dashboardPane.setVisible(
                        true
                );


                dashboardPane.setManaged(
                        true
                );
            }


            // -------------------------------------------------
            // REFRESH DATA
            // -------------------------------------------------

            refreshDashboard();


            // -------------------------------------------------
            // RESET SCROLL
            // -------------------------------------------------

            if (mainScrollPane != null) {

                mainScrollPane.setVvalue(
                        0
                );

                mainScrollPane.setHvalue(
                        0
                );
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // =========================================================
    // OPEN STUDENTS
    // =========================================================

    @FXML
    private void openStudents() {

        loadPage(
                "Students.fxml"
        );
    }


    // =========================================================
    // OPEN TEACHERS
    // =========================================================

    @FXML
    private void openTeachers() {

        loadPage(
                "Teachers.fxml"
        );
    }


    // =========================================================
    // OPEN COURSES
    // =========================================================

    @FXML
    private void openCourses() {

        loadPage(
                "Courses.fxml"
        );
    }


    // =========================================================
    // OPEN REGISTRATION
    // =========================================================

    @FXML
    private void openRegistration() {

        loadPage(
                "Registration.fxml"
        );
    }


    // =========================================================
    // OPEN ATTENDANCE
    // =========================================================

    @FXML
    private void openAttendance() {

        loadPage(
                "Attendance.fxml"
        );
    }


    // =========================================================
    // OPEN RESULTS
    // =========================================================

    @FXML
    private void openResults() {

        loadPage(
                "Results.fxml"
        );
    }


    // =========================================================
    // OPEN SETTINGS
    // =========================================================

    @FXML
    private void openSettings() {

        loadPage(
                "Settings.fxml"
        );
    }


    // =========================================================
    // LOAD PAGE
    // =========================================================
    //
    // IMPORTANT:
    //
    // mainScrollPane is the main container.
    //
    // dashboardPane = dashboard
    //
    // contentPane = module pages
    //
    // We NEVER hide mainScrollPane.
    //
    // =========================================================

    private void loadPage(
            String page) {

        try {

            // -------------------------------------------------
            // FIND FXML
            // -------------------------------------------------

            java.net.URL resource =
                    getClass().getResource(
                            "/fxml/" + page
                    );


            if (resource == null) {

                throw new RuntimeException(
                        "FXML file not found: /fxml/"
                                + page
                );
            }


            // -------------------------------------------------
            // LOAD FXML
            // -------------------------------------------------

            FXMLLoader loader =
                    new FXMLLoader(
                            resource
                    );


            Node pageNode =
                    loader.load();


            // -------------------------------------------------
            // CREATE MODULE SCROLL PANE
            // -------------------------------------------------

            ScrollPane moduleScrollPane =
                    new ScrollPane();


            moduleScrollPane.setContent(
                    pageNode
            );


            // -------------------------------------------------
            // SCROLL SETTINGS
            // -------------------------------------------------

            moduleScrollPane.setFitToWidth(
                    true
            );


            moduleScrollPane.setFitToHeight(
                    false
            );


            moduleScrollPane.setHbarPolicy(
                    ScrollPane.ScrollBarPolicy.NEVER
            );


            moduleScrollPane.setVbarPolicy(
                    ScrollPane.ScrollBarPolicy.AS_NEEDED
            );


            moduleScrollPane.setStyle(
                    "-fx-background-color: transparent;"
            );


            // -------------------------------------------------
            // SHOW MODULE
            // -------------------------------------------------

            if (contentPane != null) {

                contentPane
                        .getChildren()
                        .setAll(
                                moduleScrollPane
                        );


                contentPane.setVisible(
                        true
                );


                contentPane.setManaged(
                        true
                );
            }


            // -------------------------------------------------
            // HIDE DASHBOARD
            // -------------------------------------------------

            if (dashboardPane != null) {

                dashboardPane.setVisible(
                        false
                );


                dashboardPane.setManaged(
                        false
                );
            }


            // -------------------------------------------------
            // RESET SCROLL
            // -------------------------------------------------

            if (mainScrollPane != null) {

                mainScrollPane.setVvalue(
                        0
                );

                mainScrollPane.setHvalue(
                        0
                );
            }


        } catch (Exception e) {

            // -------------------------------------------------
            // IMPORTANT:
            // DASHBOARD REMAINS AVAILABLE IF MODULE FAILS
            // -------------------------------------------------

            e.printStackTrace();


            // Restore dashboard

            if (contentPane != null) {

                contentPane.setVisible(
                        false
                );

                contentPane.setManaged(
                        false
                );
            }


            if (dashboardPane != null) {

                dashboardPane.setVisible(
                        true
                );

                dashboardPane.setManaged(
                        true
                );
            }


            // -------------------------------------------------
            // FIND REAL ROOT ERROR
            // -------------------------------------------------

            Throwable cause =
                    e;


            while (cause.getCause() != null) {

                cause =
                        cause.getCause();
            }


            String message =
                    "Cannot open "
                            + page
                            + "\n\n"
                            + cause
                                    .getClass()
                                    .getSimpleName()
                            + ": "
                            + String.valueOf(
                                    cause.getMessage()
                            );


            AlertUtil.error(
                    "Navigation Error",
                    message
            );
        }
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    @FXML
    private void logout() {

        try {

            Session.logout();


            Stage stage =
                    (Stage) welcomeLabel
                            .getScene()
                            .getWindow();


            SceneManager.switchScene(
                    stage,
                    "Login.fxml",
                    "Student Management System"
            );


        } catch (Exception e) {

            e.printStackTrace();


            AlertUtil.error(
                    "Logout Error",
                    "Unable to logout."
            );
        }
    }
}