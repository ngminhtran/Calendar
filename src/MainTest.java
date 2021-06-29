


import calendarController.CalendarController;
import calendarModel.CalendarModel;

public class MainTest {

	public static void main(String[] args) {
		CalendarModel m = new CalendarModel();
		CalendarController c = new CalendarController(m);
		calendarModel.Calendar cc = c.generateCalendar("INIT");
		System.out.println(cc);
	}

}
