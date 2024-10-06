
import schedule.Schedule;
import schedule.Students;
import schedule.Timetable;

class Program {

    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        schedule.createSchedule();
        schedule.print();
    }
}
