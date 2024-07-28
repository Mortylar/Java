

public class Attendance {

	public Attendance(Students students_list) {
	  students_list_ = students_list;
		InitAttendanceList();
	}


  public void Register(String name, int day, int hours, int status) {
		(attendance_.get(GetStudentID(name))).Register(GetAbsHours(day, hours), status);
	}

	public int GetStatus(String name, int day, int time) { 
		return (attendance_.get(GetStudentID(name))).GetStatus(GetAbsHours(day, hours));
	}

	Students students_list_;
	ArrayList<IndividualAttendance> attendance_;
	private static final int hours_in_day_ = 24;


	private int GetAbsHours(int day, int hours) {
	  return day* hours_in_day_ + hours;
	}

	private int GetStudentID(String name) {
	  return students_list_.GetID(name);
	}


	private void InitAttendanceList() {
	  int key = 0;
		String name = student_list_.GetName(key);
		while (name != null) {
		  attendance_.add(new IndividualAttendance(name));
			name = student_list_.GetName(++key);
		}
	}


  class IndividualAttendance {
	  public IndividualAttendance(String name) {
		  name_ = name;
			attendance_ = new HashMap<Integer, Integer>();
		}

		public void Register(int date_time, int status) {
		  attendance_.put(date_time, status);
		}

    public int GetStatus(int date_time) {
		  int status = 0;
			if (attendance_.containsKey(date_time)) status = attendance_.get(date_time);
			return status;
		}


		private String name_;
		private HashMap<Integer, Integer> attendance_;
	}
}
