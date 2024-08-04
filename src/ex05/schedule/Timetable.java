package schedule;

import java.util.*;

public class Timetable {

    private static final int TOTAL_CLASSES_PER_WEEK = 10;

    ArrayList<DateTime> dateTime_;

    public Timetable() {
        dateTime_ = new ArrayList<DateTime>();
    }

    public void addClass(int day, int hour, int minute) throws IllegalArgumentException {
        if (isValidData(day, hour, minute)) {
            dateTime_.add(new DateTime(day, hour, minute));
        } else {
            throw new IllegalArgumentException("Invalid date");
        }
        Collections.sort(dateTime_);
    }

    public void addClass(String dayOfWeek, int hour) {
        GregorianCalendar calendar = new GregorianCalendar(2020, Calendar.SEPTEMBER, 1);
        int weekCode = getNumberDayOfWeek(dayOfWeek);
				final int SEPTEMBER_DAYS_LIMIT = 30;
				final int DAYS_IN_WEEK = 7;
		
        for (int i = 1; i < SEPTEMBER_DAYS_LIMIT - 1; ++i) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == weekCode) {
                addClass(i, hour, 0);
                calendar.add(Calendar.DAY_OF_WEEK, DAYS_IN_WEEK - 1);
                i += (DAYS_IN_WEEK - 1);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public int getClassDay(int classID) {
        return dateTime_.get(classID).getDay();
    }

    public int getClassTime(int classID) {
        return dateTime_.get(classID).getHour();
    }

		public int getClassMinute(int classID) {
		    return dateTime_.get(classID).getMinute();
		}

		public String getClassDayOfWeek(int classID) {
		    return dateTime_.get(classID).getDayOfWeekName();
		}

    public int getClassesCount() {
        return dateTime_.size();
    }

    public void print() {
        for(int i = 0; i < dateTime_.size(); ++i) {
            dateTime_.get(i).print();
        }
    }

    private boolean isValidData(int day, int hour, int minute) {
        boolean isValid = true;
				final int DAYS_IN_SEPTEMBER = 30;
				final int MINUTE_LIMIT = 60;
				final int START_CLASS_HOUR = 13;
				final int FINISH_CLASS_HOUR = 18;

        if ((day <= 0) || (day > DAYS_IN_SEPTEMBER)) {
            isValid = false;
        }
        if ((hour < START_CLASS_HOUR) || (hour > FINISH_CLASS_HOUR)) {
            isValid = false;
        }
        if ((minute < 0) || (minute >= MINUTE_LIMIT)) {
            isValid = false;
        }
        return isValid;
    }

    private int getNumberDayOfWeek(String dayOfWeek) {
        int res = 0;
        if (dayOfWeek == "Monday") {
            res = GregorianCalendar.MONDAY;
        } else if (dayOfWeek == "Tuesday") {
            res = GregorianCalendar.TUESDAY;
        } else if (dayOfWeek == "Wednesday") {
            res = GregorianCalendar.WEDNESDAY;
        } else if (dayOfWeek == "Thursday") {
            res = GregorianCalendar.THURSDAY;
        } else if (dayOfWeek == "Friday") {
            res = GregorianCalendar.FRIDAY;
        } else if (dayOfWeek == "Saturday") {
            res = GregorianCalendar.SATURDAY;
        } else if (dayOfWeek == "Sunday") {
            res = GregorianCalendar.SUNDAY;
        }
        return res;
    }



    public class DateTime implements Comparable<DateTime>{
	  
        static final int MINUTE_IN_HOUR = 60;
        static final int MINUTE_IN_DAY = 60*24;
				static final int CURRENT_YEAR = 2020;
		
        private GregorianCalendar calendar_;
        private int absMinuteData_;



        public DateTime(int day, int hour, int minute) {
            calendar_ = new GregorianCalendar(CURRENT_YEAR, Calendar.SEPTEMBER, 1);
            calendar_.add(Calendar.DAY_OF_MONTH, day - 1);
            calendar_.set(Calendar.HOUR_OF_DAY, hour);
            calendar_.set(Calendar.MINUTE, minute);
            absMinuteData_ = minute + MINUTE_IN_HOUR * hour + MINUTE_IN_DAY * day;
        }

        public int compareTo(DateTime other) {
            int compare_number = -1;
            if (this.absMinuteData_ > other.absMinuteData_) {
                compare_number = 1;
            } else if (this.absMinuteData_ == other.absMinuteData_) {
                compare_number = 0;
            }
            return compare_number;
        }


        public int getYear() {
            return calendar_.get(Calendar.YEAR);
        }

        public int getMonth() {
            return calendar_.get(Calendar.MONTH);
        }

        public int getDay() {
            return calendar_.get(Calendar.DAY_OF_MONTH);
        }

        public int getDayOfWeek() {
            return calendar_.get(Calendar.DAY_OF_WEEK);
        }

        public int getHour() {
            return calendar_.get(Calendar.HOUR_OF_DAY);
        }

        public int getMinute() {
            return calendar_.get(Calendar.MINUTE);
        }

        public void print() {
            System.out.printf("%d  %s\n", getDay(), getDayOfWeekName());
        }

        public String getDayOfWeekName() {
            int dayOfWeek = getDayOfWeek();
            String dayName = new String();
            if (dayOfWeek == Calendar.MONDAY) {
                dayName = "MO";
            } else if (dayOfWeek == Calendar.TUESDAY) {
                dayName = "TU";
            } else if (dayOfWeek == Calendar.WEDNESDAY) {
                dayName = "WE";
            } else if (dayOfWeek == Calendar.THURSDAY) {
                dayName = "TH";
            } else if (dayOfWeek == Calendar.FRIDAY) {
                dayName = "FR";
            } else if (dayOfWeek == Calendar.SATURDAY) {
                dayName = "SA";
            } else if (dayOfWeek == Calendar.SUNDAY) {
                dayName = "SU";
            }
            return dayName;
        }

    }

}
