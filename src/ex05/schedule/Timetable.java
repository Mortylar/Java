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

	public void AddClass(String day_of_week, int hour) // TODO

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





  public class DateTime implements Comparable<DateTime>{
	  
		public DateTime(int day, int hour, int minute) {
		  calendar_ = new GregorianCalendar(2022, Calendar.SEPTEMBER, 1);
      calendar_.add(Calendar.DAY_OF_MONTH, day - 1);
			calendar_.set(Calendar.HOUR_OF_DAY, hour);
			calendar_.set(Calendar.MINUTE, minute);
      abs_minute_data_ = minute + minute_in_hour_ * hour + minute_in_day_ * day;
		}

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
		  System.out.printf("%d  %d:%d\n", GetDay(), GetHour(), GetMinute());
		}




  }


}
