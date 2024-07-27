package schedule;

import java.util.*

public class Timetable {

	public TimeTable() {
		date_time_ = new ArrayList<DateTime>();
	}

	public AddClass(int day, int hour, int minute) {
	  //TODO check arguments

	}

  ArrayList<DateTime> date_time_;



  public class DateTime {
	  
		public DateTime(int day, int hour, int minute) {
			if ()
		  calendar_ = new GregorianCalendar(2022, Calendar.SEPTEMBER, 1);
      calendar_.add(Calendar.DAY, day - 1);
			calendar_.set(Calendar.HOUR, hour);
			calendar_.set(Calendar.MINUTE, minute);
		}

		private GregorianCalendar calendar_;

		public int GetYear() {
		  return calendar_.Get(Calendar.YEAR);
		}

		public int GetMonth() {
		  return calendar_.Get(Calendar.MONTH);
		}

		public int GetDay() {
		  return calendar_.Get(Calendar.DAY);
		}

		public int GetHour() {
		  return calendar_.Get(Calendar.HOUR);
		}

		public int GetMinute() {
		  return calendar_.Get(Calendar.MINUTE);
		}




  }

	public enum DayOfWeek {
	  MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY;
	}


}
