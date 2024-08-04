package schedule;


public class Schedule {
  
	private Students students_;
	private Timetable timetable_;
	private Attendance attendance_;

    public Schedule() {
        students_ = new Students();
        timetable_ = new Timetable();
    }

    public void createSchedule() {
        createStudentsList();
        CreateTimetable();
        createAttendance();
    }

    private void createStudentsList() {
        students_.enrollment("alelyka");
        students_.enrollment("vladiput");
				students_.enrollment("valstain");
        students_.enrollment("joba");
        students_.enrollment("xijin");
        students_.enrollment("emmacro");
				students_.expulsion("valstain");
				students_.enrollment("dimoniva");
        students_.enrollment("ankara");
    }

    private void CreateTimetable() {
        timetable_.addClass("Saturday", 13);
        timetable_.addClass("Sunday", 13);
        timetable_.addClass("Sunday", 15);
    }

    private void createAttendance() {
        attendance_ = new Attendance(students_);  //day, time, status
        attendance_.register("alelyka", 5, 13, 1);
        attendance_.register("vladiput", 5, 13, 1);
        attendance_.register("joba", 5, 13, -1);
        attendance_.register("xijin", 5, 13, 1);
        attendance_.register("emmacro", 5, 13, -1);
        attendance_.register("dimoniva", 5, 13, 1);
        attendance_.register("ankara", 5, 13, 1);

        attendance_.register("alelyka", 6, 13, 1);
        attendance_.register("vladiput", 6, 13, 1);
        attendance_.register("joba", 6, 13, -1);
        attendance_.register("xijin", 6, 13, 1);
        attendance_.register("emmacro", 6, 13, -1);
        attendance_.register("dimoniva", 6, 13, 1);
        attendance_.register("ankara", 6, 13, 1);

        attendance_.register("alelyka", 6, 15, -1);
        attendance_.register("vladiput", 6, 15, 1);
        attendance_.register("joba", 6, 15, 1);
        attendance_.register("xijin", 6, 15, 1);
        attendance_.register("emmacro", 6, 15, 1);
        attendance_.register("dimoniva", 6, 15, -1);
        attendance_.register("ankara", 6, 15, -1);

        attendance_.register("alelyka", 20, 13, 1);
        attendance_.register("vladiput", 20, 13, 1);
        attendance_.register("joba", 20, 13, -1);
        attendance_.register("xijin", 20, 13, -1);
        attendance_.register("emmacro", 20, 13, -1);
        attendance_.register("dimoniva", 20, 13, -1);
        attendance_.register("ankara", 20, 13, -1);


        attendance_.register("alelyka", 26, 13, -1);
        attendance_.register("vladiput", 26, 13, 1);
        attendance_.register("joba", 26, 13, -1);
        attendance_.register("xijin", 26, 13, -1);
        attendance_.register("emmacro", 26, 13, -1);
        attendance_.register("dimoniva", 26, 13, -1);
        attendance_.register("ankara", 26, 13, -1);

        attendance_.register("vladiput", 13, 15, 1);

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

    private void printSeparator() {
        System.out.print(".\n");
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
				printStudentsInfo();
    }

    private void printHeader() {
		    System.out.printf("%10s", "");

        for (int classID = 0; classID < timetable_.getClassesCount(); ++classID) {
            int day = timetable_.getClassDay(classID);
						int hour = timetable_.getClassTime(classID);
						int minute = timetable_.getClassMinute(classID);
						String dayOfWeek = timetable_.getClassDayOfWeek(classID);
            System.out.printf("%2d:%02d %s %2d|", hour, minute, dayOfWeek, day);
				}
				System.out.print("\n");
		}

		private void printStudentsInfo() {
		    for (int studentID = 0; studentID < students_.getStudentsCount(); ++studentID) {
			      String studentName = students_.getName(studentID);
				    printStudentAttendance(studentName);
			  }
		}

		private void printStudentAttendance(String name) {
			  System.out.printf("%10s", name);
		    for (int classID = 0; classID < timetable_.getClassesCount(); ++classID) {
				    String statusString = new String();
			      int status = attendance_.getStatus(name, timetable_.getClassDay(classID),
                                                     timetable_.getClassTime(classID));
				    if (status == 1) {
				        statusString = "1";
				    } else if (status == -1) {
				        statusString = "-1";
				    }
						System.out.printf("%11s|", statusString);
			  }
			  System.out.print("\n");
		}


}
