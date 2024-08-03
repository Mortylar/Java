package schedule;


public class Schedule {
  
	private Students students_;
	private Timetable timetable_;
	private Attendance attendance_;

    public Schedule() {
        students_ = new Students();
        timetable_ = new Timetable();
//      attendance_ = new Attendance(students_);
    }

    public void createSchedule() {
        createStudentsList();
        CreateTimetable();
        createAttendance();
    }

    public void print() {
        students_.print();
        printSeparator();

        timetable_.print();
        printSeparator();

        printAttendance();
        printSeparator();

        printFullSchedule();
    }

    private void createStudentsList() {
        students_.enrollment("aaa");
        students_.enrollment("bbb");
        students_.enrollment("ccc");
        students_.enrollment("ddd");
        students_.enrollment("eee");
    }

    private void CreateTimetable() {
        timetable_.addClass("Monday", 13);
        timetable_.addClass("Thursday", 16);
       // timetable_.addClass("Tuesday", 13);
       // timetable_.addClass("Saturday", 17);
       // timetable_.addClass("Friday", 13);
       // timetable_.addClass("Friday", 15);
    }

    private void printAttendance() {
        for (int i = 0; i < students_.getStudentsCount(); ++i) {
            String name = students_.getName(i);
            for (int classID = 0; classID < timetable_.getClassesCount(); ++classID) {
                int day = timetable_.getClassDay(classID);
                int time = timetable_.getClassTime(classID);
                int status = attendance_.getStatus(name, day, time);
                if (status == -1) {
                    System.out.printf("%s %d %d NOT_HERE\n", name, time, day);
                } else if (status == 1) { 
                    System.out.printf("%s %d %d HERE\n", name, time, day);
                }
            }
        }
    }

    private void printFullSchedule() {
        printHeader();
        //TODO
    }

    private void printHeader() {
		    System.out.print("          ");
				//System.out.printf("|%2d:%02d %s %2d|", 13, 15, "MO", 21);

        for (int classID = 0; classID < timetable_.getClassesCount(); ++classID) {
            int day = timetable_.getClassDay(classID);
						int hour = timetable_.getClassTime(classID);
						int minute = timetable_.getClassMinute(classID);
						String dayOfWeek = timetable_.getClassDayOfWeek(classID);
            System.out.printf("%2d:%02d %s %2d|", hour, minute, dayOfWeek, day);
				}
		}

    private void createAttendance() {
        attendance_ = new Attendance(students_);
        attendance_.register("aaa", 1, 13, 1);
        attendance_.register("bbb", 1, 13, -1);
    }

    private void printSeparator() {
        System.out.print(".\n");
    }

}
