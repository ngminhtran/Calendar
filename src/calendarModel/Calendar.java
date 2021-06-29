package calendarModel;

import java.util.ArrayList;

/**
 * File: Calendar.java
 * 
 * @author Jasur Jiasuer jasur@email.arizona.edu
 * @author Ngoc Minh Tran leahtran193@email.arizona.edu
 * 
 */
public class Calendar{
	private String name; 
	private int currentMonth;
	private int currentDay;
	private static final int NUM_MONTH = 12;
	public static Month[] months;
	public ArrayList<Event> events;
	/**
	 * Constructor for Calendar class
	 * @param month int
	 * @param day int 
	 * @param name string
	 */
	public Calendar(int month, int day, String name) {
		this.name = name;
		this.currentDay = day;
		this.currentMonth = month;
		months = new Month[NUM_MONTH];
		events = new ArrayList<Event>();
	}
	/**
	 * Adds and event to the calendar
	 * @param e Event stores the event
	 */
	public void addEvent(Event e) {
		events.add(e);
	}
	
	/**
	 * returns the list containing all events
	 * @return events, stores the list of events
	 */
	public ArrayList<Event> getEvents(){
		return events;
	}
	/**
	 * gets a specific event by name
	 * @param name string
	 * @return e Event stores the event to be returned 
	 */
	public Event getEvent(String name) {
		for (Event e : events) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * update the month with the month passed in
	 * @param currMonth int stores the current month
	 * @param m Month stores the month
	 */
	public void update(int currMonth, Month m) {
		months[currMonth] = m;
	}
	/**
	 * This returns the name 
	 * @return name String stores the name
	 */
	public String getName() {
		return name;
	}
	
	public String toString() {
		String out = "        Calendar          \n\n";
		for(Month month : Calendar.months) {
			out += month.getMonth() + "\n" + month.toString() + "\n";
		}
		return out;
	}
	
}
