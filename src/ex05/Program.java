import schedule.Students;
import schedule.Timetable;
import java.util.*;


class Program {

  public static void main(String[] args) {
		Students st = new Students(Arrays.asList("first", "second", "third"));
		st.Print();
		Timetable tt = new Timetable();
		tt.AddClass(3, 14, 00);
		tt.AddClass(1, 13, 0);
		tt.AddClass(2, 14, 15);
		//st.AddClass(-1, 18, 00);
		tt.Print();
  }
}
