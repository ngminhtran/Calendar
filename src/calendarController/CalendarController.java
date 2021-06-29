package calendarController;

/**
 * File: CalendarController.java
 * 
 * This is the controller of the calendar, which implements
 * the logic of the calendar generator and save state.
 * 
 * @author Jasur Jiasuer jasur@email.arizona.edu
 * @author Ngoc Minh Tran leahtran193@email.arizona.edu
 * 
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import calendarModel.Calendar;
import calendarModel.CalendarModel;
import calendarModel.Day;
import calendarModel.Event;
import calendarModel.Month;
import calendarModel.Week;

public class CalendarController {

	private CalendarModel model;
	private ArrayList<String> days;
	private static final String[] MONTHS = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	
	/**
	 * This is the constructor of CalendarController.
	 * This initializes model and create week days.
	 * @param model CalendarModel stores the model
	 */
	public CalendarController(CalendarModel model) {
		this.model = model;
		createDays();
	}

	/**
	 * Creates an array list of days in a week
	 */
	private void createDays() {
		days = new ArrayList<String>();
		days.add("Sunday");
		days.add("Monday");
		days.add("Tuesday");
		days.add("Wednesday");
		days.add("Thursday");
		days.add("Friday");
		days.add("Saturday");
	}

	/**
	 * Generates Calendar object given specific name, by
	 * iterating to generate Month, then adding Week,
	 * containing Day. Adds newly-generated Calendar
	 * to the CalendarModel.
	 * @param name, representing the name of the Calendar
	 * @return, generated Calendar object
	 */
	public Calendar generateCalendar(String name) {
		Calendar calendar = new Calendar(model.currentMonth,model.currentDay,name);
		String currDay = "Wednesday";
		int currMonth = 0;
		for(String month: MONTHS) {
			int currDate = 1;
			Month m = new Month(month);
			//each month 5 weeks
			int i = 0;
			int weekLimit = 5;
			if(month.equals("May")||month.equals("August")) {
				weekLimit = 6;
			}
			while(i < weekLimit) {
				Week week = new Week();
				//week field
				Day[] daysInWeek = week.week;
				int j = 0;
				for(String day: days) {
					if(day.equals(currDay)&&m.getDays()>=currDate) {
						//update Day obj
						daysInWeek[j] = new Day(currDate,day);
						currDate = currDate +1;
						currDay = getNextDay(currDay);
					}
					j++;
				}
				for (int k = 0; k < daysInWeek.length; k++) {
					if (daysInWeek[k] == null) {
						daysInWeek[k] = new Day(0,"RandomDay");
					}
				}
				//update week obj
				week.week = daysInWeek;
				m.addWeek(week, i);
				i++;
			}
			//update month obj
			m.setMonth(month);
			calendar.update(currMonth, m);
			currMonth++;
		}
		//System.out.println(calendar);
		return calendar;
	}
	
	/**
	 * Returns the following day of a given day
	 * in the days array list.
	 * @param day
	 * @return
	 */
	private String getNextDay(String day) {
		if (day.equals("Saturday")) {
			return days.get(0);
		} else {
			int index = days.indexOf(day);
			return days.get(index+1);
		}
	}

	/**
	 * Saves all Calendar names into a single text file and 
	 * their corresponding Events into separated text files.
	 */
	public void saveData() {
		//get all existed Calendars
		ArrayList<Calendar> allCalendars = model.getCalendars();
		//create a file storing all calendar names
		File calFile = new File("Calendars.txt");
		createFile(calFile);
		try {
			FileWriter fwCal = new FileWriter(calFile);
			// load all data in calendars file into an array list
			Scanner calScan = new Scanner(calFile);
			ArrayList<String> calData = new ArrayList<>();
			while (calScan.hasNextLine()) {
				calData.add(calScan.nextLine());
			}
			for (Calendar calendar : allCalendars) {
				if (!calData.contains(calendar.getName() + "\n")) {
					fwCal.write(calendar.getName() + "\n");
				}	
				ArrayList<Event> events = calendar.getEvents();
				if (!events.isEmpty()) {
					//create a file storing all events in current calendar
					File eventFile = new File(calendar.getName() + "_Event.txt");
					createFile(eventFile);
					FileWriter fwEvent = new FileWriter(eventFile);
					// load all data in event file into an array list
					Scanner eventScan = new Scanner(eventFile);
					ArrayList<String> eventData = new ArrayList<>();
					while (eventScan.hasNextLine()) {
						eventData.add(eventScan.nextLine());
					}
					for (Event event : events) {
						String name = event.getName();
						String day = event.getDay() + "";
						String month = event.getMonth() + "";
						String startHour = event.getStartHour() + "";
						String startMin = event.getStartMinute() + "";
						String endHour = event.getEndHour() + "";
						String endMin = event.getEndMinute() + "";
						String color = event.getColor();
						String location = event.getLocation();
						String notes = event.getNotes();
						String toWrite = String.join(", ", name, day, month, startHour, startMin, endHour, endMin, color, location, notes);
						if (!eventData.contains(toWrite)) {
							System.out.println(toWrite);
							fwEvent.write(toWrite);
						}
					}
					fwEvent.close();
				}
			}
			fwCal.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a file if it does not exist, otherwise do nothing
	 * @param file
	 */
	private void createFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads data from the file containing Calendar names
	 * and generates multiple calendars. For every calendar,
	 * checks if it contains any event. If it does, adds all 
	 * existing events into the calendar, otherwise, do nothing.
	 */
	public void loadData() {
		File calFile = new File("Calendars.txt");
		if (calFile.exists()) {
			try {
				Scanner calScan = new Scanner(calFile);
				//load Calendar names from text file
				while (calScan.hasNextLine()) {
					String calendarName = calScan.nextLine();
					//create saved Calendar 
					Calendar calendar = generateCalendar(calendarName);
					File eventFile = new File(calendarName + "_Event.txt");
					if (eventFile.exists()) {
						Scanner eventScan = new Scanner(eventFile);
						//load all Event in current Calendar from text file
						while (eventScan.hasNextLine()) {
							String[] eventData = eventScan.nextLine().split(", ");
							String name = eventData[0];
							int day = Integer.parseInt(eventData[1]);
							int month = Integer.parseInt(eventData[2]);
							int startHour = Integer.parseInt(eventData[3]);
							int startMin = Integer.parseInt(eventData[4]);
							int endHour = Integer.parseInt(eventData[5]);
							int endMin = Integer.parseInt(eventData[6]);
							String color = eventData[7];
							String location = eventData[8];
							String notes = eventData[9];
							// create new Event and add to Calendar
							calendar.addEvent(new Event(name, day, month-1, startHour, startMin, endHour, endMin, color, location, notes));
						}
					}
					//add Calendar to model
					model.addCalendar(calendar);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
}