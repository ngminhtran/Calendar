package calendarTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import calendarController.CalendarController;
import calendarModel.Calendar;
import calendarModel.CalendarModel;
import calendarModel.Day;
import calendarModel.Event;
import calendarModel.Month;

class CalendarTest {

	@Test
	// test controller generateCalendar()
	void testGenerateCalendar() {
		CalendarModel m = new CalendarModel();
		CalendarController c = new CalendarController(m);
		m.addCalendar(c.generateCalendar("INIT"));
		assertEquals(m.getCalendar("INIT").getName(), "INIT");
		assertEquals(m.getCalendar("SOMETHING"), null);
		System.out.println(m.getCalendar("INIT"));
	}
	
	@Test
	// test controller saveData() and loadData() with empty location & note
	void testSaveState01() {
		CalendarModel m = new CalendarModel();
		CalendarController c = new CalendarController(m);
		Calendar cal = c.generateCalendar("CLASS SCHEDULE");
		Event e = new Event("CSC 335", 1, 5, 9, 30, 10, 45, "blue", "", "");
		cal.addEvent(e);
		m.addCalendar(cal);
		c.saveData(); //test saveData()
		CalendarModel mSave = new CalendarModel();
		CalendarController cSave = new CalendarController(mSave);
		cSave.loadData(); //test load Data()
		Calendar calSave = mSave.getCalendar("CLASS SCHEDULE");
		Event eSave2 = calSave.getEvent("CSC 335");
		assertNotEquals(eSave2.getNotes(), "Note");
		assertNotEquals(eSave2.getLocation(), "Location");
		assertEquals(eSave2.getNotes(), " ");
		assertEquals(eSave2.getLocation(), " ");
	}
	
	@Test
	void testMultipleCalendars() {
		CalendarModel m = new CalendarModel();
		CalendarController c = new CalendarController(m);
		Calendar classCal = c.generateCalendar("CLASS SCHEDULE");
		Calendar workCal = c.generateCalendar("WORK SCHEDULE");
		m.addCalendar(classCal);
		m.addCalendar(workCal);
		c.saveData();
		CalendarModel mSave = new CalendarModel();
		CalendarController cSave = new CalendarController(mSave);
		cSave.loadData();
		Calendar classSave = mSave.getCalendar("CLASS SCHEDULE");
		Calendar workSave = mSave.getCalendar("WORK SCHEDULE");
		assertEquals(classSave.getName(), "CLASS SCHEDULE");
		assertEquals(workSave.getName(), "WORK SCHEDULE");
		assertEquals(workSave.getEvent("event"), null); // test event not found
	}
	
	@Test
	// test controller saveData() and loadData() with non-empty location & note
	void testSaveState02() {
		CalendarModel m = new CalendarModel();
		CalendarController c = new CalendarController(m);
		Calendar cal = c.generateCalendar("CLASS SCHEDULE");
		Event e = new Event("CSC 345", 1, 5, 12, 30, 1, 45, "red", "Chavez", "Exam Day"); 
		cal.addEvent(e);
		m.addCalendar(cal);
		c.saveData(); //test saveData()
		CalendarModel mSave = new CalendarModel();
		CalendarController cSave = new CalendarController(mSave);
		cSave.loadData(); //test load Data()
		Calendar calSave = mSave.getCalendar("CLASS SCHEDULE");
		Event eSave = calSave.getEvent("CSC 345");
		assertEquals(eSave.getName(), "CSC 345");
		assertEquals(eSave.getStartTime(), "12:30");
		assertEquals(eSave.getEndTime(), "1:45");
	}
	
	@Test
	// test controller saveData() and loadData() with non-empty location & note
	void testSaveState03() {
		CalendarModel m = new CalendarModel();
		CalendarController c = new CalendarController(m);
		Calendar cal = c.generateCalendar("CLASS SCHEDULE");
		Event e = new Event("CSC 445", 15, 6, 12, 30, 1, 45, "red", "Chavez", "Exam Day"); 
		cal.addEvent(e);
		m.addCalendar(cal);
		c.saveData(); //test saveData()
		CalendarModel mSave = new CalendarModel();
		CalendarController cSave = new CalendarController(mSave);
		cSave.loadData(); //test load Data()
		Calendar calSave = mSave.getCalendar("CLASS SCHEDULE");
		Event eSave = calSave.getEvent("CSC 445");
		assertEquals(eSave.getName(), "CSC 445");
		assertEquals(eSave.getStartTime(), "12:30");
		assertEquals(eSave.getEndTime(), "1:45");
	}
	
	@Test
	void testDayMonth() {
		Day day = new Day(0, "");
		day.setDate(15);
		assertNotEquals(day.getDate(), 30);
		assertEquals(day.toString(), "15.");
		Month month = new Month("May");
		assertNotEquals(month.getMonth(), "June");
		assertEquals(month.getMonth(), "May");
	}
	
}
