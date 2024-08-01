package schedule;


public class Schedule {
  public Schedule() {
    students_ = new Students();
		timetable_ = new Timetable();
//		attendance_ = new Attendance(students_);
  }

	public void CreateSchedule() {
	  CreateStudentsList();
		CreateTimetable();
		CreateAttendance();
	}


  public void Print() {
	  students_.Print();
		PrintSeparator();

		timetable_.Print();
		PrintSeparator();

    PrintAttendance();
    PrintSeparator();

		PrintFullSchedule();
	}

	private Students students_;
	private Timetable timetable_;
	private Attendance attendance_;

	private void CreateStudentsList() {
	 students_.Enrollment("aaa");
	 students_.Enrollment("bbb");
	 students_.Enrollment("ccc");
	 students_.Enrollment("ddd");
	 students_.Enrollment("eee");
	}

	private void CreateTimetable() {
	  timetable_.AddClass("Monday", 13);
	  timetable_.AddClass("Thursday", 16);
	  timetable_.AddClass("Tuesday", 13);
	  timetable_.AddClass("Saturday", 17);
	  timetable_.AddClass("Friday", 13);
	  timetable_.AddClass("Friday", 15);
	}

	private void PrintAttendance() {
	  for (int i = 0; i < students_.GetStudentsCount(); ++i) {
		  String name = students_.GetName(i);
      for (int class_id = 0; class_id < timetable_.GetClassesCount(); ++class_id) {
				int day = timetable_.GetClassDay(class_id);
				int time = timetable_.GetClassTime(class_id);
			  int status = attendance_.GetStatus(name, day, time);
				//System.out.printf("\nday = %d time = %d\n", day, time);
				if (status == -1) {
				  System.out.printf("%s %d %d NOT_HERE\n", name, time, day);
				} else if (status == 1) { 
				  System.out.printf("%s %d %d HERE\n", name, time, day);
				}
			}
		}
	}

  private void PrintFullSchedule() {
	  PrintHeader();

	}


	private void CreateAttendance() {
		attendance_ = new Attendance(students_);
	  attendance_.Register("aaa", 1, 13, 1);
	  attendance_.Register("bbb", 1, 13, -1);
	}

  private void PrintSeparator() {
	  System.out.print(".\n");
	}

}
