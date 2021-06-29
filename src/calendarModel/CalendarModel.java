package calendarModel;

/**
 * File: CalendarModel.java
 * 
 * @author Jasur Jiasuer jasur@email.arizona.edu
 * @author Ngoc Minh Tran leahtran193@email.arizona.edu
 * 
 */

import java.util.ArrayList;

public class CalendarModel {
	public ArrayList<Calendar> calendars;
	public int currentDay;
	public int currentMonth;
	/**
	 * Constructor for model 
	 */
	public CalendarModel() {
		calendars = new ArrayList<Calendar>();
	}
	/**
	 * adds a calendar to model
	 * @param cal Calendar
	 */
	public void addCalendar(Calendar cal) {
		calendars.add(cal);
	}
	/**
	 * gets a calendar by its name
	 * @param name string
	 * @return cal Calendar returns the calendar with name string
	 */
	public Calendar getCalendar(String name) {
		for (Calendar cal : calendars) {
			if (cal.getName().equals(name)) {
				return cal;
			}
		}
		return null;
	}
	/**
	 * returns a list of calendars
	 * @return calendars, stores the list of calendars
	 */
	public ArrayList<Calendar> getCalendars() {
		return calendars;
	}
}