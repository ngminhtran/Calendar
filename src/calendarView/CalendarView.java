package calendarView;

import java.util.ArrayList;
import java.util.Date;
import calendarController.CalendarController;
import calendarModel.Calendar;
import calendarModel.CalendarModel;
import calendarModel.Event;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * 
 * @author Flynn Gaur abhyudaygaur@email.arizona.edu
 * @author Ben Tadiparti 
 */

public class CalendarView extends Application {
	
	private BorderPane window = new BorderPane();
	private Stage primaryStage = new Stage(); 
	private GridPane board = new GridPane();
	private TilePane tile = new TilePane();
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private Button addEvent, addCal, saveBtn, cancelBtn, saveCalBtn, cancelCalBtn ;
	private DialogBox addEventBox, addCalBox;
	private TextField nameField, dayField, monthField, yearField, 
	startTimeField, endTimeField, locationField, notesField, calNameField;	
	private Label currCalName;
	private ComboBox colorBox;
	private CalendarModel model;
	private CalendarController control;
	private Calendar defaultCal;
	private Calendar cal;
	private String[] Days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private int month = 0;
	private int week = 0;
	private int date = 1;
	private int view = 2;
	private int currentCalendar = 0;
	
	
	@Override
	/**
	 * This function creates the view part of the calendar by arranging the 
	 * necessary javafx components such that a user friendly interface is displayed
	 * on the stage
	 * @param primaryStage stage 
	 */
	public void start(Stage primaryStage) throws Exception {
		
		model = new CalendarModel();
		control = new CalendarController(model);
		defaultCal = control.generateCalendar("Default");
		model.addCalendar(defaultCal);
		control.loadData();
		
		board.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		double width = this.screen.getWidth();
		double height = this.screen.getHeight();
		
		Button dayButton = new Button("Day");
		dayButton.setOnAction((event) -> {
			view = 0;
			view(3, 1,  width, height);
		});
		
		Button weekButton = new Button("Week");
		weekButton.setOnAction((event) -> {
			view = 1;
			view(3, 7, 240, height - 183);
		});
		
		Button monthButton = new Button("Month");
		monthButton.setOnAction((event) -> {
			date = 1;
			week = 0;
			view = 2;
			int x = 7;
			if (month == 4 || month == 7) {
				x = 8;
			}
			view(x, 7, 240, 200);
		});
		
		addEvent = new Button("Add Event");		
		addCal = new Button("Add Calendar");		
    
		Label changeDay = new Label("Change Day");
		Button prevDay = new Button("<-");
		prevDay.setOnAction((event) -> {
			view = 0;
			if (date == 1) {
				if (month == 0) {
					month = 11;
				} else {
					month--;
				}
				date = defaultCal.months[month].getDays();
				week = 4;
				if (month == 4 || month == 7) {
					week = 5;
				}
			} else {
				if (defaultCal.months[month].weeks[week].week[0].toString().equals(date + ".")) {
					week--;
				}
				date--;
			}
			view(3, 1,  width, height);
		});
		
		Button nextDay = new Button("->");
		nextDay.setOnAction((event) -> {
			view = 0;
			if (date == defaultCal.months[month].getDays()) {
				if (month == 11) {
					month = 0;
				} else {
					month++;
				}
				date = 1;
				week = 0;
			} else {
				if (defaultCal.months[month].weeks[week].week[6].toString().equals(date + ".")) {
					week++;
				}
				date++;
			}
			view(3, 1,  width, height);
		});
		
		Label changeWeek = new Label("Change Week");
		Button prevWeek = new Button("<-");
		prevWeek.setOnAction((event) -> {
			view = 1;
			if (week == 0) {
				if (month == 0) {
					month = 11;
				} else {
					month--;
				}
				date = defaultCal.months[month].getDays();
				week = 4;
				if (month == 4 || month == 7) {
					week = 5;
				}
			} else {
				week--;
				int first = 0;
				while (defaultCal.months[month].weeks[week].week[first].getDate() == 0) {
					first++;
				}
				date = defaultCal.months[month].weeks[week].week[first].getDate();
			}
			view(3, 7, 240, height - 183);
		});
		
		Button nextWeek = new Button("->");
		nextWeek.setOnAction((event) -> {
			if (month == 4 || month == 7) {
				if (week == 5) {
					month++;
					week = 0;
					date = 1;
				} else {
					week++;
					int first = 0;
					while (defaultCal.months[month].weeks[week].week[first].getDate() == 0) {
						first++;
					}
					date = defaultCal.months[month].weeks[week].week[first].getDate();
				}
			} else {
				if (week == 4) {
					if (month == 11) {
						month = 0;
					} else {
						month++;
					}
					week = 0;
					date = 1;
				} else {
					week++;
					int first = 0;
					while (defaultCal.months[month].weeks[week].week[first].getDate() == 0) {
						first++;
					}
					date = defaultCal.months[month].weeks[week].week[first].getDate();
				}
			}
			view(3, 7, 240, height - 183);
		});
    
		Label changeMonth = new Label("Change Month");
		Button prevMonth = new Button("<-");
		prevMonth.setOnAction((event) -> {
			view = 2;
			if (month == 0) {
				month = 11;
			} else {
				month--;
			}
			date = 1;
			week = 0;
			int x = 7;
			if (month == 4 || month == 7) {
				x = 8;
			}
			view(x, 7, 240, 200);
		});
		
		Button nextMonth = new Button("->");
		nextMonth.setOnAction((event) -> {
			view = 2;
			if (month == 11) {
				month = 0;
			} else {
				month++;
			}
			date = 1;
			week = 0;
			int x = 7;
			if (month == 4 || month == 7) {
				x = 8;
			}
			view(x, 7, 240, 200);
		});
		
		Label currCalLabel = new Label("Calendar: ");
		currCalName = new Label("Default");
		
		Label changeCal = new Label("Change Calendar");
		Button prevCal = new Button("<-");
		prevCal.setOnAction((event) -> {
			if (currentCalendar == 0) {
				currentCalendar = model.calendars.size() - 1;
			} else {
				currentCalendar--;
			}
			String calName = model.calendars.get(currentCalendar).getName();
			currCalName.setText(calName);
			defaultCal = model.getCalendar(calName);
			month = 0;
			week = 0;
			date = 1;
			view = 2;
			int x = 7;
			if (month == 4 || month == 7) {
				x = 8;
			}
			view(x, 7, 240, 200);
		});
		
		Button nextCal = new Button("->");
		nextCal.setOnAction((event) -> {
			if (currentCalendar == model.calendars.size() - 1) {
				currentCalendar = 0;
			} else {
				currentCalendar++;
			}
			String calName = model.calendars.get(currentCalendar).getName();
			currCalName.setText(calName);
			defaultCal = model.getCalendar(calName);
			month = 0;
			week = 0;
			date = 1;
			view = 2;
			int x = 7;
			if (month == 4 || month == 7) {
				x = 8;
			}
			view(x, 7, 240, 200);
		});

		dayButton.setPrefWidth(80);
		dayButton.setPrefHeight(20);
		monthButton.setPrefWidth(80);
		weekButton.setPrefWidth(80);
		changeDay.setPrefWidth(120);
		changeWeek.setPrefWidth(120);
		changeMonth.setPrefWidth(120);
		changeCal.setPrefWidth(120);
		addEvent.setPrefWidth(250);
		addCal.setPrefWidth(250);
		
		dayButton.setFont(new Font(15));
		weekButton.setFont(new Font(15));
		monthButton.setFont(new Font(15));
		currCalLabel.setFont(new Font(15));
		currCalName.setFont(new Font(15));
		changeDay.setFont(new Font(15));
		changeWeek.setFont(new Font(15));
		changeMonth.setFont(new Font(15));
		changeCal.setFont(new Font(15));
		nextDay.setFont(new Font(15));
		prevDay.setFont(new Font(15));
		nextWeek.setFont(new Font(15));
		prevWeek.setFont(new Font(15));
		nextMonth.setFont(new Font(15));
		prevMonth.setFont(new Font(15));
		nextCal.setFont(new Font(15));
		prevCal.setFont(new Font(15));
		addEvent.setFont(new Font(15));
		addCal.setFont(new Font(15));

		dayButton.setAlignment(Pos.CENTER);
		monthButton.setAlignment(Pos.CENTER);
		weekButton.setAlignment(Pos.CENTER);
		currCalLabel.setAlignment(Pos.CENTER);
		currCalName.setAlignment(Pos.CENTER);
		changeDay.setAlignment(Pos.CENTER);
		changeWeek.setAlignment(Pos.CENTER);
		changeMonth.setAlignment(Pos.CENTER);
		changeCal.setAlignment(Pos.CENTER);
		addEvent.setAlignment(Pos.CENTER);
		addCal.setAlignment(Pos.CENTER);

		weekButton.setPrefWidth(70);
		monthButton.setPrefWidth(80);
		
		HBox changeView = new HBox(dayButton, weekButton, monthButton);
		HBox dateBox = new HBox(prevDay, changeDay, nextDay);
		HBox weekBox = new HBox(prevWeek, changeWeek, nextWeek);
		HBox viewBox = new HBox(prevMonth, changeMonth, nextMonth);
		HBox calNameBox = new HBox(currCalLabel, currCalName);
		HBox calBox = new HBox(prevCal, changeCal, nextCal);
		
		changeView.setAlignment(Pos.CENTER);
		changeView.setPrefHeight(20);
		viewBox.setAlignment(Pos.CENTER);
		calNameBox.setAlignment(Pos.CENTER);
		calBox.setAlignment(Pos.CENTER);
		dateBox.setAlignment(Pos.CENTER);
		weekBox.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(changeView,addEvent,addCal,dateBox,weekBox,viewBox,calBox,calNameBox);
		
		window.setLeft(vBox);
		view(7, 7, 240, 200); // Default dimensions 
		
		tile.getChildren().add(board);
		tile.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		TilePane.setMargin(board, new Insets(9, 9, 9, 9));
		
		window.setCenter(board);
		setAction();
		primaryStage.setTitle("Calendar");
		primaryStage.setScene(new Scene(window, this.screen.getWidth(), this.screen.getHeight()));
		primaryStage.show();
	}
	
	/**
	 * This function sets the action handlers for the event and calendar buttons
	 */
	public void setAction() {
		EventHandler<ActionEvent> addEventClick = e -> addEventClick(e);
		addEvent.setOnAction(addEventClick);
		EventHandler<ActionEvent> addCalendarClick = e -> addCalClick(e);
		addCal.setOnAction(addCalendarClick);
	}

	/**
	 * This is used to open the event box when clicked
	 * @param e ActionEvent stores the ActionEvent for clicking event box
	 */
	private void addEventClick(ActionEvent e) {
		addEventBox();
		addEventBox.showAndWait();

	}
	
	/**
	 * This is used to open the calendar box when clicked 
	 * @param e ActionEvent stores the click for clicking the calendar box
	 */
	private void addCalClick(ActionEvent e) {
		addCalendarBox();
		addCalBox.showAndWait();
	}
	
	@SuppressWarnings({ "unchecked", "static-access" })
	/**
	 * This stores the javafx objects for creating the add event window
	 */
	private void addEventBox() {
		
		addEventBox = new DialogBox();
		addEventBox.setTitle("Add New Event");
		Label createLabel = new Label("Event Name: ");
		createLabel.setFont(new Font(16));
		nameField = new TextField();
		
		Label dayLabel = new Label("Day: ");
		dayLabel.setFont(new Font(16));
	    	dayField = new TextField();
		
		Label monthLabel = new Label("    Month: ");
		monthLabel.setFont(new Font(16));
		monthField = new TextField();
		
		Label startTimeLabel = new Label("Start Time: ");
		startTimeLabel.setFont(new Font(16));
		startTimeField = new TextField();
		
		Label endTimeLabel = new Label("End Time: ");
		endTimeLabel.setFont(new Font(16));
		endTimeField = new TextField();
		
		Label locationLabel = new Label("Event Location: ");
		locationLabel.setFont(new Font(16));
		locationField = new TextField();
		
		Label notesLabel = new Label("Event Notes: ");
		notesLabel.setFont(new Font(16));
		notesField = new TextField();
		
		Label formatInfo = new Label("Note: Time is in military format. Example: 23:59");
		formatInfo.setFont(new Font(12));
		
		Label optionalNote = new Label("(optional)");
		optionalNote.setFont(new Font(12));
	    	optionalNote.setTextFill(Color.web("#808080"));
	    
	    	Label optionalNote2 = new Label("(optional)");
		optionalNote2.setFont(new Font(12));
	    	optionalNote2.setTextFill(Color.web("#808080"));

		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Red",
			        "Blue",
			        "Yellow",
			        "Green"
			    );
			colorBox = new ComboBox(options);
			
			colorBox.setPromptText("Choose color option: ");
			colorBox.setEditable(true);        
			colorBox.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {        
				
			});
		
		saveBtn = new Button("Save");
		cancelBtn = new Button("Cancel");
		
		eventEventHandlers();
		
		HBox hBox1 = new HBox(createLabel, nameField);
		HBox hBox2 = new HBox(startTimeLabel, startTimeField);
		HBox hBox8 = new HBox(formatInfo);
		HBox hBox3 = new HBox(endTimeLabel, endTimeField);
		HBox hBox4 = new HBox(dayLabel, dayField, monthLabel, monthField);
		HBox hBox7 = new HBox(locationLabel, locationField, optionalNote);
		HBox hBox5 = new HBox(notesLabel, notesField, optionalNote2);
		HBox hBox6 = new HBox(colorBox);
		HBox hBox9 = new HBox(saveBtn, cancelBtn);
		
		VBox vBox = new VBox(hBox1, hBox8, hBox2, hBox3, hBox4, hBox7, hBox5, hBox6, hBox9);	
		VBox.setMargin(hBox1, new Insets( 30, 250, 20, 30 ));
		VBox.setMargin(hBox2, new Insets( 5, 250, 20, 30 ));
		VBox.setMargin(hBox3, new Insets( 5, 250, 20, 30 ));
		VBox.setMargin(hBox4, new Insets( 5, 250, 20, 30 ));
		VBox.setMargin(hBox5, new Insets( 5, 250, 20, 30 ));
		VBox.setMargin(hBox7, new Insets( 5, 250, 20, 30 ));
		VBox.setMargin(hBox8, new Insets( 5, 250, 20, 30 ));
		VBox.setMargin(hBox6, new Insets( 5, 250, 20, 30 ));
		VBox.setMargin(hBox9, new Insets( 5, 250, 20, 30 ));
		HBox.setMargin(saveBtn, new Insets( 0, 10, 0, 0 ));

		window = new BorderPane();
		Scene scene = new Scene(window);
		window.setCenter(vBox);	
		addEventBox.setScene(scene);
	}
	
	/**
	 * This function is used call the eventHandlers for closing the event window and saving the event
	 */
	public void eventEventHandlers() {
		EventHandler<ActionEvent> closeEventDialog = e -> closeEvent(e);
		cancelBtn.setOnAction(closeEventDialog);
		
		EventHandler<ActionEvent> saveBtnClick = e -> saveEventClick(e);
		saveBtn.setOnAction(saveBtnClick);
	}
	
	/**
	 * This function is used to close the event window
	 * @param e ActionEvent stores the ActionEvent to click the cancel button
	 */
	public void closeEvent(ActionEvent e) {
		addEventBox.close();
	}
	
	/**
	 * This function is used to close the calendar window
	 * @param e ActionEvent stores the ActionEvent to click the cancel button
	 */
	public void closeCal(ActionEvent e) {
		addCalBox.close();
	}
	
	/**
	 * 
	 * This function is used to store the event inside of the calendar model
	 * @param e ActionEvent used to determine clicks on the save button
	 */
	public void saveEventClick(ActionEvent e) {	
		boolean check = checkEventFields();
		boolean validInput = false;
		
		if (check) {
			try {
				int numbers = Integer.valueOf(dayField.getText()) + Integer.valueOf(monthField.getText());
				validInput = true;
	        	}
	       	catch (NumberFormatException e1) {
	        		new Alert(Alert.AlertType.ERROR, "Date can only accept as an integer").showAndWait();
	        		e.consume();
	        	}
			if ((!startTimeField.getText().contains(":")) || (!endTimeField.getText().contains(":"))
			|| (startTimeField.getText().length()!=5) || (startTimeField.getText().length()!=5)) {
				new Alert(Alert.AlertType.ERROR, "Time is not in military format. Please try again.").showAndWait();	
				e.consume();
			}
			
	        if (validInput) {
				String name = nameField.getText();
				int day = Integer.valueOf(dayField.getText());
				int month = Integer.valueOf(monthField.getText());
				String[] startTime = startTimeField.getText().split(":");
				String[] endTime = endTimeField.getText().split(":");
				int startHour = Integer.valueOf(startTime[0]);
				int startMin = Integer.valueOf(startTime[1]);
				int endHour = Integer.valueOf(endTime[0]);
				int endMin = Integer.valueOf(endTime[1]);
				String color = (String) colorBox.getValue();
				String location = (String)locationField.getText();
				String notes = (String)notesField.getText();
				Event newEvent = new Event(name, day, month-1, startHour, startMin, endHour, endMin, color, location, notes);
				cal = model.getCalendar(currCalName.getText());
				cal.addEvent(newEvent);
				addEventBox.close();
				if (view == 0) {
					view(3, 1,  this.screen.getWidth(), this.screen.getHeight());
		        	} else if (view == 1) {
		        		view(3, 7, 240, this.screen.getHeight() - 183);
		        	} else {
		        		int x = 7;
					month--;
					if (month == 4 || month == 7) {
						x = 8;
					}
					view(x, 7, 240, 200);
				}
				control.saveData();
			}
		} else {
    		new Alert(Alert.AlertType.ERROR, "Some fields were left empty.").showAndWait();
    		e.consume();
		}
	}
	
	/**
	 * This returns a boolean value telling the function if the fields have been filled.
	 * @return check boolean stores true if required fields are filled and false elsewise
	 */
	public boolean checkEventFields() {
		boolean check = false;
		check = !nameField.getText().equals("") && !dayField.getText().equals("") && !monthField.getText().equals("") 
				&& !monthField.getText().equals("") && !startTimeField.getText().equals("") && !endTimeField.getText().equals("");
		return check;
	}
	
	@SuppressWarnings("static-access")
	/**
	 * This stores the java fx objects for the calendar box
	 */
	private void addCalendarBox() {
		
		addCalBox = new DialogBox();
		addCalBox.setTitle("Add New Calendar");
		Label createLabel = new Label("Calendar Name: ");
		createLabel.setFont(new Font(16));
		calNameField = new TextField();
	
		saveCalBtn = new Button("Save");
		cancelCalBtn = new Button("Cancel");
		
		calEventHandlers();
		
		HBox hBox1 = new HBox(createLabel, calNameField);
		HBox hBox9 = new HBox(saveCalBtn, cancelCalBtn);
		
		VBox vBox = new VBox(hBox1, hBox9);
		VBox.setMargin(hBox1, new Insets( 30, 250, 20, 30 ));
		VBox.setMargin(hBox9, new Insets( 5, 250, 20, 30 ));
		HBox.setMargin(saveCalBtn, new Insets( 0, 10, 0, 0 ));

		window = new BorderPane();
		Scene scene = new Scene(window);
		window.setCenter(vBox);	
		addCalBox.setScene(scene);
	}
	
	/**
	 * This method calls the event handlers to close the calendar window and to save the name
	 */
	public void calEventHandlers() {
		EventHandler<ActionEvent> closeCalDialog = e -> closeCal(e);
		cancelCalBtn.setOnAction(closeCalDialog);
		
		EventHandler<ActionEvent> saveCalBtnClick = e -> saveCal(e);
		saveCalBtn.setOnAction(saveCalBtnClick);
	}
	
	/**
	 * This function saves the calnedar name 
	 * @param e ActionEvent This stores the click on the save button
	 */
	public void saveCal(ActionEvent e) {
		String calName = calNameField.getText();
		Calendar newCal = control.generateCalendar(calName);
		model.addCalendar(newCal);
		addCalBox.close();
		control.saveData();
	}
	
	/**
	 * This function is used to create a dialogue box
	 */
	private class DialogBox extends Stage{
		public DialogBox() {
			this.initModality(Modality.APPLICATION_MODAL);
			this.initOwner(primaryStage);
			this.setX(primaryStage.getX()+30);
			this.setY(primaryStage.getY()+100);
		}
	}
	
	/**
	* This function implements the different views in the calendar based on its parameters and fills them with the 
	* appropriate events.
	* @param i - columns 
	* @param j - rows
	* @param width - grid box width
	* @param height - grid box height
	*/
  	public void view(int i, int j, double width, double height) {
		board.getChildren().clear();
		for(int x = 0; x < i; x++) {
			for(int y = 0; y < j; y++) {
				StackPane tempPane = new StackPane();
				if (x == 0) { // Month Block -- populations the first row with the correct month etc.
					if (y == 0) {
						Text text = new Text();
						if (view != 2) {
							int first = 0;
							while (!defaultCal.months[month].weeks[week].week[first].toString().equals(date + ".")) {
								first++;
							}
							if (view == 0) {
								// Month: Year | Date
								text.setText(defaultCal.months[month].getMonth() + ": 2020 | " + 
										defaultCal.months[month].weeks[week].week[first].toString());
							}
							if (view == 1) {
								first = 0;
								// Gets index of the first day of the week
								while (defaultCal.months[month].weeks[week].week[first].getDate() == 0) {
									first++;
								}
								int last = 6;
								// Gets index of the last day of the week
								while (defaultCal.months[month].weeks[week].week[last].getDate() == 0) {
									last--;
								}
								if (week == 0) {
									// Month: Year | 1. - last date of week
									// Default text of the first day of the month
									text.setText(defaultCal.months[month].getMonth() + ": 2020 | " + "1. - "
									+ defaultCal.months[month].weeks[week].week[last].toString());
								} else {
									// Month: Year | first date - last date of the week
									text.setText(defaultCal.months[month].getMonth() + ": 2020 | " + 
								defaultCal.months[month].weeks[week].week[first].toString() + " - "
								+ defaultCal.months[month].weeks[week].week[last].toString());
								}
							}
						} else {
							// Default text
							text.setText(defaultCal.months[month].getMonth() + ": 2020");
						}
					    text.setFont(Font.font(null, FontWeight.BOLD, 30));     
					    text.setFill(Color.BLACK);
					    tempPane.getChildren().add(text);
					    tempPane.setAlignment(Pos.CENTER_LEFT);
					}
					tempPane.setPrefSize(240, 100);
				} else if (x == 1) { // Days Block -- populations the second row with the correct day
					if (view != 0) {
						Text text = new Text(Days[y]); 
					    text.setFont(Font.font(null, FontWeight.BOLD, 30));     
					    text.setFill(Color.BLACK); 
						tempPane.getChildren().add(text);
						tempPane.setPrefSize(240, 200);	
					}
				} else {
					int pos = x-2;
					ArrayList<Event> events = populate(defaultCal.months[month].weeks[pos].week[y].getDate(), month);
					if (view != 0) { // Dates Block -- populations each stack pane with the correct date
						Text text = new Text();
						if (view == 1) {
							pos = week;
						}
						if (defaultCal.months[month].weeks[pos].week[y].getDate() == 0) {
							continue;
						} else {
							// Appropriate Date
							text.setText(defaultCal.months[month].weeks[pos].week[y].toString());
							text.setFont(Font.font(null, FontWeight.BOLD, 25));
						    text.setFill(Color.BLACK);
							VBox eventBox = new VBox();
							// Populates the earliest event of the day in month view (event name only)
							if (view == 2) {
								Text eventText = new Text();
							    
								if (events.size() != 0) {
									eventText.setText(events.get(0).getName());
									eventText.setFont(Font.font(null, FontWeight.BOLD, 25));
								    eventText.setFill(Color.web(events.get(0).getColor()));
								    eventBox.getChildren().add(eventText);
								    eventBox.setAlignment(Pos.CENTER);
								}
								tempPane.getChildren().addAll(text, eventBox);
								StackPane.setAlignment(text, Pos.TOP_RIGHT);
							} else {
								// Populates the events of the day in week view 
								// event name: start time - end time
								// Location: LOCATION (Blank if empty)
								for (int e = 0; e < events.size(); e++) {
									Text eventText = new Text();
									eventText.setText(events.get(e).getName() + ": " + 
											events.get(e).getStartTime() + "-" + events.get(e).getEndTime()
											+ "\nLocation: " + events.get(e).getLocation());
									eventText.setFont(Font.font(null, FontWeight.BOLD, 25));
									eventText.setFill(Color.web(events.get(e).getColor()));
									eventBox.getChildren().add(eventText);
									eventBox.setAlignment(Pos.CENTER);
								}
								tempPane.getChildren().addAll(text, eventBox);
								StackPane.setAlignment(text, Pos.TOP_RIGHT);
							}
						}
					} else {
						int first = 0;
						// Gets the appropriate date
						while (!defaultCal.months[month].weeks[week].week[first].toString().equals(date + ".")) {
							first++;
						}
						// Gets the ordered events
						events = populate(defaultCal.months[month].weeks[pos].week[first].getDate(), month);
						VBox eventBox = new VBox();
						// Populates the events of the day in day view 
						// event name: start time - end time
						// Location: LOCATION (Blank if empty)
						// Notes: NOTES (Blank if empty)
						for (int e = 0; e < events.size(); e++) {
							Text eventText = new Text();
							eventText.setText(events.get(e).getName() + ": " + 
									events.get(e).getStartTime() + "-" + events.get(e).getEndTime()
									+ "\nLocation: " + events.get(e).getLocation() + "\nNotes: " + events.get(e).getNotes());
							eventText.setFont(Font.font(null, FontWeight.BOLD, 25));
							eventText.setFill(Color.web(events.get(e).getColor()));
							eventBox.getChildren().add(eventText);
							eventBox.setAlignment(Pos.TOP_LEFT);
						}
						tempPane.getChildren().add(eventBox);
					}
					tempPane.setPrefSize(width, height);
				}
				tempPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
				board.add(tempPane, y, x, 1, 1);
				// Margins for the top row
				if (x == 0 && y == 0) {
					GridPane.setMargin(tempPane, new Insets(1, 0, 1, 1));
				} else if (y == j - 1 && x == 0) {
					GridPane.setMargin(tempPane, new Insets(1, 1, 1, 0));
				} else if (x == 0){
					GridPane.setMargin(tempPane, new Insets(1, 0, 1, 0));
				} else {
					GridPane.setMargin(tempPane, new Insets(1, 1, 1, 1));
				}
			}
		}
	}
	
	/**
   	* This function iterates through every event in the calendar and returns an arraylist of ordered events
   	* that occur in a specific day in a specific month.
   	* @param date - current date the cal is on
   	* @param month - current month the cal is on
   	* @return an ordered list of events of that specific day in that specific month
   	*/
	public ArrayList<Event> populate (int date, int month) {
		ArrayList<Event> events = new ArrayList<Event>();
		//Adds the events that occur a specific day of the month and adds them to an arraylist
		for (int i = 0; i < defaultCal.events.size(); i++) {
			if ((defaultCal.events.get(i).getDay() == date) && (defaultCal.events.get(i).getMonth() == month)) {
				events.add(defaultCal.events.get(i));
			}
		}
		ArrayList<Event> ordered = new ArrayList<Event>();
		int size = events.size();
		//Orders the events of that day based on their start time 
		while (size != 0) {
			int first = 0;
			if (size != 1) {
				for (int i = 1; i < size; i++) {
					if ((events.get(first).getStartHour() >= events.get(i).getStartHour())
							&& (events.get(first).getStartMinute() >= events.get(i).getStartMinute())) {
						first = i;
					}
				}
			}
			ordered.add(events.get(first));
			events.remove(first);
			size = events.size();
		}
		return ordered;
	}
}
