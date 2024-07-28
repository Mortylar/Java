import schedule.Students;
import schedule.Timetable;
import java.util.*;


class Program {

  public static void main(String[] args) {
		Timetable st = new Timetable();
		st.AddClass(3, 14, 00);
		st.AddClass(1, 13, 0);
		st.AddClass(2, 14, 15);
		//st.AddClass(-1, 18, 00);
		st.Print();
  }
}
