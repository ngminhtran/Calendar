package calendarModel;

/**
 * File: Event.java
 * 
 * @author Ngoc Minh Tran leahtran193@email.arizona.edu
 * 
 */

public class Event { 
	private String name;
	private int day;
	private int month;
	private int startHour; 
	private int endHour;
	private int startMin;
	private int endMin;
	private String color;
	private String location;
	private String notes;
	/**
	 * contructor for event
	 * @param name string 
	 * @param day int
	 * @param month int 
	 * @param startHour int 
	 * @param startMin int 
	 * @param endHour int
	 * @param endMin int
	 * @param color string
	 * @param location string
	 * @param notes string
	 */
	public Event(String name, int day, int month, int startHour, int startMin, int endHour, int endMin, String color, String location, String notes) {
		this.name = name;
		this.day = day;
		this.month = month;
		this.startHour = startHour;
		this.startMin = startMin;
		this.endHour = endHour;
		this.endMin = endMin;
		this.color = color;
		this.location = location;
		if (location.isEmpty()) {
			this.location = " ";
		}
		this.notes = notes;	
		if (notes.isEmpty()) {
			this.notes = " ";
		}
	}
	/**
	 * getter for name
	 * @return name String stores the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * getter for day
	 * @return day int stores the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * getter for month
	 * @return month int returns the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * getter for start time
	 * @return String returns the string with starttime
	 */
	public String getStartTime() {
		return (startHour + ":" + startMin);
	}
	/**
	 * getter for end time
	 * @return String returns the string with endtime
	 */
	public String getEndTime() {
		return (endHour + ":" + endMin);
	}
	/**
	 * getter for starthour
	 * @return int startHour stores the start hour
	 */
	public int getStartHour() {
		return startHour;
	}
	/**
	 * getter for end hour
	 * @return int endHour stores the end hour
	 */
	public int getEndHour() {
		return endHour;
	}
	/**
	 * getter for start minute
	 * @return int startMin stores the startMin
	 */
	public int getStartMinute() {
		return startMin;
	}
	/**
	 * getter for end minute
	 * @return int endMin stores the end minute
	 */
	public int getEndMinute() {
		return endMin;
	}
	/**
	 * getter for color
	 * @return String color stores the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * getter for location
	 * @return String stores the event location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * getter for notes
	 * @return String notes stores the notes
	 */
	public String getNotes() {
		return notes;
	}
}
