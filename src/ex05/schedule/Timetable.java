package schedule;

import java.util.*;

public class Timetable {

	public Timetable() {
		date_time_ = new ArrayList<DateTime>();
	}

	public void AddClass(int day, int hour, int minute) throws IllegalArgumentException {
	  if (IsValidData(day, hour, minute)) {
		  date_time_.add(new DateTime(day, hour, minute));
    } else {
		  throw new IllegalArgumentException("Invalid date");
		}
		Collections.sort(date_time_);
	}

	public void AddClass(String day_of_week, int hour) {
		GregorianCalendar calendar = new GregorianCalendar(2020, Calendar.SEPTEMBER, 1);
		int week_code = GetNumberDayOfWeek(day_of_week);
	//	System.out.println("\n week_code = " + week_code);
		
		for (int i = 1; i < 29; ++i) {
			if (calendar.get(Calendar.DAY_OF_WEEK) == week_code) {
			  AddClass(i, hour, 0);
				calendar.add(Calendar.DAY_OF_WEEK, 6);
				i += 6;
			}
		//	System.out.println("\n i; day_of_week;" + i + " " + calendar.get(Calendar.DAY_OF_WEEK));
		  calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

  ArrayList<DateTime> date_time_;
	private static final int total_classes_per_week_ = 10;

	public void Print() {
	  for(int i = 0; i < date_time_.size(); ++i) {
		  date_time_.get(i).Print();
		}
	}

	private boolean IsValidData(int day, int hour, int minute) {
	  boolean is_valid = true;
		if ((day < 1) || (day > 30)) is_valid = false;
		if ((hour < 13) || (hour > 18)) is_valid = false; //TODO add class length
		if ((minute < 0) || (minute > 59)) is_valid = false;
		return is_valid;
	}

	private int GetNumberDayOfWeek(String day_of_week) {
	  int res = 0;
		if (day_of_week == "Monday") {
			res = GregorianCalendar.MONDAY;
		} else if (day_of_week == "Tuesday") {
			res = GregorianCalendar.TUESDAY;
		} else if (day_of_week == "Wednesday") {
			res = GregorianCalendar.WEDNESDAY;
		} else if (day_of_week == "Thursday") {
			res = GregorianCalendar.THURSDAY;
		} else if (day_of_week == "Friday") {
			res = GregorianCalendar.FRIDAY;
		} else if (day_of_week == "Saturday") {
			res = GregorianCalendar.SATURDAY;
		} else if (day_of_week == "Sunday") {
			res = GregorianCalendar.SUNDAY;
		} 
  return res;
	}





  public class DateTime implements Comparable<DateTime>{
	  
		public DateTime(int day, int hour, int minute) {
		  calendar_ = new GregorianCalendar(2020, Calendar.SEPTEMBER, 1);
      calendar_.add(Calendar.DAY_OF_MONTH, day - 1);
			calendar_.set(Calendar.HOUR_OF_DAY, hour);
			calendar_.set(Calendar.MINUTE, minute);
      abs_minute_data_ = minute + minute_in_hour_ * hour + minute_in_day_ * day;
		}

		//public DateTime(int day_of_week, int hour) {
		//  
		//}

		private GregorianCalendar calendar_;
		static final int minute_in_hour_ = 60;
		static final int minute_in_day_ = 60*24;
		private int abs_minute_data_;



    public int compareTo(DateTime other) {
		  int compare_number = -1;
			if (this.abs_minute_data_ > other.abs_minute_data_) {
			  compare_number = 1;
			} else if (this.abs_minute_data_ == other.abs_minute_data_) {
			
			  compare_number = 0;
			}
			return compare_number;
		}


		public int GetYear() {
		  return calendar_.get(Calendar.YEAR);
		}

		public int GetMonth() {
		  return calendar_.get(Calendar.MONTH);
		}

		public int GetDay() {
		  return calendar_.get(Calendar.DAY_OF_MONTH);
		}

		public int GetDayOfWeek() {
		  return calendar_.get(Calendar.DAY_OF_WEEK);
		}

		public int GetHour() {
		  return calendar_.get(Calendar.HOUR_OF_DAY);
		}

		public int GetMinute() {
		  return calendar_.get(Calendar.MINUTE);
		}

		public void Print() {

		  System.out.printf("%d  %s\n", GetDay(), GetDayOfWeekName());
		}

		private String GetDayOfWeekName() {
			int day_of_week = GetDayOfWeek();
			String day_name = new String();
		  if (day_of_week == Calendar.MONDAY) {
			  day_name = "MO";
			} else if (day_of_week == Calendar.TUESDAY) {
			  day_name = "TU";
			} else if (day_of_week == Calendar.WEDNESDAY) {
			  day_name = "WE";
			} else if (day_of_week == Calendar.THURSDAY) {
			  day_name = "TH";
			} else if (day_of_week == Calendar.FRIDAY) {
			  day_name = "FR";
			} else if (day_of_week == Calendar.SATURDAY) {
			  day_name = "SA";
			} else if (day_of_week == Calendar.SUNDAY) {
			  day_name = "SU";
			}
			return day_name;
		}




  }


}
