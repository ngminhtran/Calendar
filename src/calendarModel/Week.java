package calendarModel;

/**
 * File: Week.java
 * 
 * This class encapsulates the methods that stores and interacts with the different weeks of a month.
 * 
 * @author Benhur J. Tadiparti tadiparti@email.arizona.edu
 * 
 */
public class Week {
	public Day[] week;
	/**
	 * contructor for Week
	 */
	public Week() { 
		week = new Day[7];
	}
	
	public String toString() {
		String out = "";
		for(Day day: week) {
			if(day!=null) {
				out += day.toString();
			}
		}
		return out;
	}
}
