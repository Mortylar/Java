package schedule;

import java.util.*;

public class Attendance {

    private static final int HOURS_IN_DAY = 24;

    Students studentsList_;
    ArrayList<IndividualAttendance> attendance_;

    public Attendance(Students studentsList) {
        studentsList_ = studentsList;
        attendance_ = new ArrayList<IndividualAttendance>();
        initAttendanceList();
    }

    public void register(String name, int day, int hours, int status) {
        (attendance_.get(getStudentID(name)))
            .register(getAbsHours(day, hours), status);
    }

    public int getStatus(String name, int day, int time) {
        return (attendance_.get(getStudentID(name)))
            .getStatus(getAbsHours(day, time));
    }

    private int getAbsHours(int day, int hours) {
        return day * HOURS_IN_DAY + hours;
    }

    private int getStudentID(String name) { return studentsList_.getID(name); }

    private void initAttendanceList() {
        int key = 0;
        String name = studentsList_.getName(key);
        while (name != null) {
            attendance_.add(new IndividualAttendance(name));
            name = studentsList_.getName(++key);
        }
    }

    class IndividualAttendance {

        private String name_;
        private HashMap<Integer, Integer> attendance_;

        public IndividualAttendance(String name) {
            name_ = name;
            attendance_ = new HashMap<Integer, Integer>();
        }

        public void register(int date_time, int status) {
            attendance_.put(date_time, status);
        }

        public int getStatus(int date_time) {
            int status = 0;
            if (attendance_.containsKey(date_time)) {
                status = attendance_.get(date_time);
            }
            return status;
        }
    }
}
