package calendarModel;

/**
 * File: Month.java
 * 
 * This class encapsulates the methods that stores and interacts with the different months of the year.
 * 
 * @author Benhur J. Tadiparti tadiparti@email.arizona.edu
 * @author Ngoc Minh Tran leahtran193@email.arizona.edu
 * @author Jasur Jiasuer jasur@email.arizona.edu
 * 
 */
public class Month {
	
	private String month;
	private int days;
	public Week[] weeks;
	private static final int NUM_WEEKS = 6;
	/**
	 * constructor for month
	 * @param month,
	 */
	public Month(String month) {
		this.month = month;
		weeks = new Week[NUM_WEEKS];
		setDays();
	}
	/**
	 * adds week
	 * @param newWeek Week
	 * @param i int
	 */
	public void addWeek(Week newWeek, int i) {
		weeks[i] = newWeek;
	}
	/**
	 * setter for month
	 * @param month string
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * getter month
	 * @return String month stores the month
	 */
	public String getMonth() {
		return this.month;
	}
	/**
	 * set the days of the month
	 */
	public void setDays() {
		if (this.month == "January") {
			this.days = 31;
		} else if (this.month == "February") {
			this.days = 29;
		} else if (this.month == "March") {
			this.days = 31;
		} else if (this.month == "April") {
			this.days = 30;
		} else if (this.month == "May") {
			this.days = 31;
		} else if (this.month == "June") {
			this.days = 30;
		} else if (this.month == "July") {
			this.days = 31;
		} else if (this.month == "August") {
			this.days = 31;
		} else if (this.month == "September") {
			this.days = 30;
		} else if (this.month == "October") {
			this.days = 31;
		} else if (this.month == "November") {
			this.days = 30;
		} else if (this.month == "December") {
			this.days = 31;
		}
	}
	/**
	 * get days
	 * @return days int stores the day as int
	 */
	public int getDays() {
		return this.days;
	}
	
	public String toString() {
		String out = "";
		for(Week week: weeks) {
			if(week != null) {
				out+= week.toString()+"\n";
			}
		}
		return out;
	}
	
}
