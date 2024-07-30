import schedule.Students;
import schedule.Timetable;
import java.util.*;


class Program {

  public static void main(String[] args) {
		Students st = new Students(Arrays.asList("first", "second", "third"));
		st.Print();
		Timetable tt = new Timetable();
		tt.AddClass("Monday", 14);
		tt.AddClass("Friday", 13);
		//tt.AddClass(2, 14, 15);
		//st.AddClass(-1, 18, 00);
		tt.Print();
		//GregorianCalendar calendar = new GregorianCalendar();
		//System.out.print("\nSunday = \n" + calendar.SUNDAY.ordinal());
  }
}
