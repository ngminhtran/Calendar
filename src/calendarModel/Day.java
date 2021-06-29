package calendarModel;

/**
 * File: Day.java
 * 
 * This class encapsulates the methods that stores and interacts with the different days of the year.
 * 
 * @author Benhur J. Tadiparti tadiparti@email.arizona.edu
 * @author Jasur Jiasuer jasur@email.arizona.edu
 * 
 */
public class Day{
	
	private int date;
	String day;
	/**
	 * contructor for Day
	 * @param date int
	 * @param day string
	 */
	public Day(int date, String day) {
		this.date = date;
		this.day = day;
	}
	/**
	 * sets the date
	 * @param date int 
	 */
	public void setDate(int date) {
		this.date = date;
	}
	/**
	 * gets the date
	 * @return date int stores the date
	 */
	public int getDate() {
		return this.date;
	}

	public String toString() {
		return date+".";
	}
	
} 
