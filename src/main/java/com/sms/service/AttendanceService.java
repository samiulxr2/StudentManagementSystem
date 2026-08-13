package com.sms.service;

import java.util.ArrayList;
import java.util.List;

import com.sms.model.Attendance;
import com.sms.model.Registration;
import com.sms.repository.AttendanceRepository;
import com.sms.repository.RegistrationRepository;

public class AttendanceService {

    private final AttendanceRepository attendanceRepository =
            new AttendanceRepository();

    private final RegistrationRepository registrationRepository =
            new RegistrationRepository();


    // =========================================================
    // BASIC ATTENDANCE CRUD
    // =========================================================

    public void addAttendance(Attendance attendance) {

        List<Attendance> attendanceList =
                attendanceRepository.loadAttendance();

        attendanceList.add(attendance);

        attendanceRepository.saveAttendance(attendanceList);
    }


    public void addAttendanceBatch(
            List<Attendance> attendanceRecords) {

        if (attendanceRecords == null
                || attendanceRecords.isEmpty()) {

            return;
        }

        List<Attendance> attendanceList =
                attendanceRepository.loadAttendance();

        attendanceList.addAll(attendanceRecords);

        attendanceRepository.saveAttendance(attendanceList);
    }


    public List<Attendance> getAllAttendance() {

        return attendanceRepository.loadAttendance();
    }


    // =========================================================
    // GET ALL REGISTRATIONS
    // Used by AttendanceController
    // =========================================================

    public List<Registration> getAllRegistrations() {

        return registrationRepository.loadRegistrations();
    }


    // =========================================================
    // UPDATE ATTENDANCE
    // =========================================================

    public boolean updateAttendance(
            Attendance updatedAttendance) {

        List<Attendance> attendanceList =
                attendanceRepository.loadAttendance();

        for (int i = 0;
             i < attendanceList.size();
             i++) {

            Attendance existing =
                    attendanceList.get(i);

            if (existing.getAttendanceId() != null
                    && updatedAttendance.getAttendanceId() != null
                    && existing.getAttendanceId()
                    .equals(
                            updatedAttendance
                                    .getAttendanceId()
                    )) {

                attendanceList.set(
                        i,
                        updatedAttendance
                );

                attendanceRepository.saveAttendance(
                        attendanceList
                );

                return true;
            }
        }

        return false;
    }


    // =========================================================
    // DELETE ATTENDANCE
    // =========================================================

    public boolean deleteAttendance(
            String attendanceId) {

        List<Attendance> attendanceList =
                attendanceRepository.loadAttendance();

        boolean removed =
                attendanceList.removeIf(
                        attendance ->
                                attendance.getAttendanceId() != null
                                        && attendanceId != null
                                        && attendance
                                        .getAttendanceId()
                                        .equals(attendanceId)
                );

        if (removed) {

            attendanceRepository.saveAttendance(
                    attendanceList
            );
        }

        return removed;
    }


    // =========================================================
    // SEARCH ATTENDANCE
    // =========================================================

    public Attendance searchAttendance(
            String attendanceId) {

        for (Attendance attendance :
                attendanceRepository.loadAttendance()) {

            if (attendance.getAttendanceId() != null
                    && attendanceId != null
                    && attendance.getAttendanceId()
                    .equals(attendanceId)) {

                return attendance;
            }
        }

        return null;
    }


    // =========================================================
    // GET ATTENDANCE OF A COMPLETE CLASS
    // =========================================================

    public List<Attendance> getAttendanceByClass(
            String teacherId,
            String courseCode,
            String semester,
            String batch,
            String section,
            String date) {

        List<Attendance> result =
                new ArrayList<>();

        for (Attendance attendance :
                attendanceRepository.loadAttendance()) {

            if (matches(
                    attendance.getTeacherId(),
                    teacherId)

                    && matches(
                    attendance.getCourseCode(),
                    courseCode)

                    && matches(
                    attendance.getSemester(),
                    semester)

                    && matches(
                    attendance.getBatch(),
                    batch)

                    && matches(
                    attendance.getSection(),
                    section)

                    && matches(
                    attendance.getDate(),
                    date)) {

                result.add(attendance);
            }
        }

        return result;
    }


    // =========================================================
    // GET REGISTERED STUDENTS FOR A CLASS
    // =========================================================

    public List<Registration> getRegisteredStudents(
            String courseCode,
            String semester,
            String batch,
            String section) {

        List<Registration> registeredStudents =
                new ArrayList<>();

        List<Registration> registrations =
                registrationRepository
                        .loadRegistrations();

        for (Registration registration :
                registrations) {

            if (matches(
                    registration.getCourseCode(),
                    courseCode)

                    && matches(
                    registration.getSemester(),
                    semester)

                    && matches(
                    registration.getBatch(),
                    batch)

                    && matches(
                    registration.getSection(),
                    section)) {

                registeredStudents.add(
                        registration
                );
            }
        }

        return registeredStudents;
    }


    // =========================================================
    // CHECK WHETHER ATTENDANCE ALREADY EXISTS
    // =========================================================

    public boolean attendanceExists(
            String studentId,
            String courseCode,
            String date) {

        for (Attendance attendance :
                attendanceRepository.loadAttendance()) {

            if (matches(
                    attendance.getStudentId(),
                    studentId)

                    && matches(
                    attendance.getCourseCode(),
                    courseCode)

                    && matches(
                    attendance.getDate(),
                    date)) {

                return true;
            }
        }

        return false;
    }


    // =========================================================
    // GET STUDENT ATTENDANCE
    // =========================================================

    public List<Attendance> getStudentAttendance(
            String studentId,
            String courseCode) {

        List<Attendance> result =
                new ArrayList<>();

        for (Attendance attendance :
                attendanceRepository.loadAttendance()) {

            if (matches(
                    attendance.getStudentId(),
                    studentId)

                    && matches(
                    attendance.getCourseCode(),
                    courseCode)) {

                result.add(attendance);
            }
        }

        return result;
    }


    // =========================================================
    // TOTAL CLASSES
    // =========================================================

    public int getTotalClasses(
            String studentId,
            String courseCode) {

        return getStudentAttendance(
                studentId,
                courseCode
        ).size();
    }


    // =========================================================
    // PRESENT COUNT
    // =========================================================

    public int getPresentCount(
            String studentId,
            String courseCode) {

        int count = 0;

        for (Attendance attendance :
                getStudentAttendance(
                        studentId,
                        courseCode)) {

            if (matches(
                    attendance.getStatus(),
                    "Present")) {

                count++;
            }
        }

        return count;
    }


    // =========================================================
    // ABSENT COUNT
    // =========================================================

    public int getAbsentCount(
            String studentId,
            String courseCode) {

        int count = 0;

        for (Attendance attendance :
                getStudentAttendance(
                        studentId,
                        courseCode)) {

            if (matches(
                    attendance.getStatus(),
                    "Absent")) {

                count++;
            }
        }

        return count;
    }


    // =========================================================
    // LATE COUNT
    // =========================================================

    public int getLateCount(
            String studentId,
            String courseCode) {

        int count = 0;

        for (Attendance attendance :
                getStudentAttendance(
                        studentId,
                        courseCode)) {

            if (matches(
                    attendance.getStatus(),
                    "Late")) {

                count++;
            }
        }

        return count;
    }


    // =========================================================
    // ATTENDANCE PERCENTAGE
    // =========================================================

    public double getAttendancePercentage(
            String studentId,
            String courseCode) {

        int total =
                getTotalClasses(
                        studentId,
                        courseCode
                );

        if (total == 0) {

            return 0.0;
        }

        int present =
                getPresentCount(
                        studentId,
                        courseCode
                );

        return (present * 100.0) / total;
    }


    // =========================================================
    // ATTENDANCE MARKS OUT OF 7
    // =========================================================

    public double getAttendanceMarks(
            String studentId,
            String courseCode) {

        double percentage =
                getAttendancePercentage(
                        studentId,
                        courseCode
                );

        return (percentage / 100.0) * 7.0;
    }


    // =========================================================
    // HELPER METHOD
    // =========================================================

    private boolean matches(
            String actual,
            String expected) {

        if (actual == null
                || expected == null) {

            return false;
        }

        return actual.trim()
                .equalsIgnoreCase(
                        expected.trim()
                );
    }
}