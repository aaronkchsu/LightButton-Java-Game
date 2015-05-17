package assign9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;


public class LightsOutGame extends JFrame implements ActionListener{


	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		LightsOutGame console = new LightsOutGame();
		console.setVisible(true);
		console.styleRandom();

	}

	// Buttons on game board currently selected
	private LightsOutButton[][] buttonGrid; 
	protected int gridSize;

	// The full containers to control whole interface
	private JPanel panButtons;
	private JPanel mainPanel;
	private JPanel mainPanelFinal;

	// Menu Variables
	JRadioButtonMenuItem rbMenuItem;

	// A JLabel that Changes according to how many boxes are left
	private JLabel msgLabel;
	private int onButtons;
	private boolean manual;

	// The number of rows and columns for the grid
	private int rows;
	private int columns;

	public LightsOutGame() throws InterruptedException {

		// Exit on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Game starts out not on manual mode
		manual = false;

		gridSize = 1;

		// Set look and feel also taking away of exceptions
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		buildPanel();
	}


	/**
	 * Takes the action of pushing the button and performs the lights off/on actions
	 * @Override
	 */
	public void actionPerformed(ActionEvent action) {

		if(action.getSource() instanceof LightsOutButton) {

			//Gets the button selected from the action
			LightsOutButton buttonSelected = (LightsOutButton) action.getSource();

			// Initialize position variable outside of loop
			int rowPosition = 0;
			int columnPosition =  0;

			// Searches through grid to find where button is located
			rowPosition = buttonSelected.getRow();
			columnPosition = buttonSelected.getColumn();

			// Store all affected buttons into a group
			ArrayList<LightsOutButton> group = new ArrayList<LightsOutButton>();

			// Adds this button to the group no matter what
			group.add(buttonGrid[rowPosition][columnPosition]);

			// Only do this when manual mode is not on
			if(!manual){

				// Add the rows into group that exist; skip over ones that don't exist
				if(rowPosition != 0){
					group.add(buttonGrid[rowPosition -1][columnPosition]);

				}
				if(rowPosition != rows - 1){
					group.add(buttonGrid[rowPosition + 1][columnPosition]);

				}
				if(columnPosition != 0){
					group.add(buttonGrid[rowPosition][columnPosition - 1]);

				}
				if(columnPosition != columns - 1){
					group.add(buttonGrid[rowPosition][columnPosition + 1]);

				}

			}

			// Loops Through the whole group and switches each button 
			for(LightsOutButton b: group){

				if(!b.isOn()){
					onButtons++;
					msgLabel.setText("There are "  + onButtons + " lights still on.");
				}
				if(b.isOn()){
					onButtons--;
					msgLabel.setText("There are "  + onButtons + " lights still on.");
				}

				// Toggles each button in the group
				b.toggle();

			}

			// If all the buttons are off you win
			if(onButtons == 0){

				//Loops through each grid spot adding each button to the grid as well as the matrix
				for(int i = 0; i < rows; i++) {
					for(int j = 0; j < columns; j++) {

						buttonGrid[i][j].setEnabled(false);

						// Adds one to buttons only if original button wasn't on
						if(!buttonGrid[i][j].isOn()){
							onButtons++;
							msgLabel.setText("There are "  + onButtons + " lights still on.");
						}

						buttonGrid[i][j].turnOn();
					}

				}


				JOptionPane.showMessageDialog(null, "Hey looks like you WON!! Restart program to play again!:]");
				System.exit(0);
			}
		}

		else if(action.getSource() instanceof gridButtons){

			// Button grid
			gridButtons grid = (gridButtons) action.getSource();
			int gridNumb = grid.getGrid();

			//Clears what is in the panel
			mainPanelFinal.removeAll();

			if(gridNumb == 5){
				gridSize = 1;
			}
			else if(gridNumb == 6){
				gridSize = 2;
			}
			else if(gridNumb == 8){
				gridSize = 3;
			}
			else if(gridNumb == 10){
				gridSize = 4;

			}

			//Builds Panel
			buildPanel();
		}

		// Rebuilds the panel according to random option
		else if(action.getSource() instanceof RandomButton){
			RandomButton source = (RandomButton)action.getSource();
			String type = source.getType();
			if(type.equals("style")) {

				// Clears what is in the panel
				mainPanelFinal.removeAll();

				// Randoms with style
				randomStyle();

				// Changes panel according to style
				changePanel();

			}

			//If not style button random the buttons normally
			else {

				//Clears what is in the panel
				mainPanelFinal.removeAll();

				// Randoms buttons
				randomButtons();

				// Changes panel according to random
				changePanel();
			}

		}

		// If manual button is clicked toggle the button
		else if(action.getSource() instanceof ManualButton){
			ManualButton manual = (ManualButton) action.getSource();

			// Toggles manual
			manual.toggle();

			// Change the private instance variable for manual
			this.manual = manual.manualOn(); 

		}

	}

	/**
	 * Used to change the panel for random methods
	 */
	private void changePanel(){

		// Creates a label where we constantly show the buttons to the user
		msgLabel = new JLabel("There are " + onButtons + " left.", JLabel.CENTER);

		// Create a container to hold and organized the 16 buttons.
		panButtons = new JPanel();

		// Panel to put text box in
		mainPanel = new JPanel();

		// Border layout used to order the containers
		mainPanel.setLayout(new BorderLayout());

		// Grid Container
		JPanel gridContainer = new JPanel();

		// Sets up new layout
		makeGrid();


		// Adds Each button to panButtons
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				panButtons.add(buttonGrid[i][j]);

			}

		}

		// Grid making buttons
		gridButtons five = new gridButtons(5);
		five.addActionListener(this);
		gridButtons six = new gridButtons(6);
		six.addActionListener(this);
		gridButtons eight = new gridButtons(8);
		eight.addActionListener(this);
		gridButtons ten = new gridButtons(10);
		ten.addActionListener(this);

		// Make Random Buttons
		RandomButton style = new RandomButton(1);
		style.addActionListener(this);
		RandomButton boring = new RandomButton(2);
		boring.addActionListener(this);

		// Creates a manual button
		ManualButton mButton = new ManualButton();
		mButton.addActionListener(this);

		// Add Every button
		gridContainer.setLayout(new GridLayout(7, 1));
		gridContainer.add(style);
		gridContainer.add(boring);
		gridContainer.add(five);
		gridContainer.add(six);
		gridContainer.add(eight);
		gridContainer.add(ten);
		gridContainer.add(mButton);

		mainPanel.add(msgLabel, BorderLayout.SOUTH);
		mainPanel.add(panButtons, BorderLayout.CENTER);

		mainPanelFinal = new JPanel();

		mainPanelFinal.add(mainPanel, BorderLayout.CENTER);
		mainPanelFinal.add(gridContainer, BorderLayout.EAST);

		// Set up the JFrame.
		setTitle("Lights Out!!");
		setContentPane(mainPanelFinal);

		// Change preferred size according 
		if(gridSize == 1) 
			setPreferredSize(new Dimension(700, 300));
		if(gridSize == 2) 
			setPreferredSize(new Dimension(850, 350));
		if(gridSize == 3)
			setPreferredSize(new Dimension(900, 450));
		if(gridSize == 4) 
			setPreferredSize(new Dimension(1050, 525));

		//pack the contents
		pack();

	}

	/**
	 * After making the grid re make the interface
	 */
	private void buildPanel() {

		// Creates a label where we constantly show the buttons to the user
		msgLabel = new JLabel("There are " + onButtons + " left.", JLabel.CENTER);

		// Create a container to hold and organized the 16 buttons.
		panButtons = new JPanel();

		// Panel to put text box in
		mainPanel = new JPanel();

		// Border layout used to order the containers
		mainPanel.setLayout(new BorderLayout());

		// Grid Container
		JPanel gridContainer = new JPanel();

		//Sets up new layout
		makeGrid();

		//Creates the buttonGrid based on number of rows and colunms selected
		buttonGrid = new LightsOutButton[rows][columns];

		// Sets them on or off at random
		Random rng = new Random();

		// There are 0 on buttons until we start adding some
		onButtons = 0; 

		//Loops through each grid spot adding each button to the grid as well as the matrix
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				// This is used to find a random boolean each time
				boolean indicator = rng.nextBoolean();

				// If true add a on button if false add a off button
				if(indicator){

					// Creates button adds a actionlistener to it and stores it
					LightsOutButton onButton = new LightsOutButton(i, j);

					// Adds listeners before storing
					onButton.addActionListener(this);

					// Storing places
					buttonGrid[i][j] = onButton;
					panButtons.add(buttonGrid[i][j]);

					// Keeps track of how many buttons are on
					onButtons++;
					msgLabel.setText("There are "  + onButtons + " lights still on.");

				}

				else {

					// Creates Button Turns it off and then adds it to the panel and array
					LightsOutButton offButton = new LightsOutButton(i, j);

					// Turns off button and adds listener before storing
					offButton.turnOff();
					offButton.addActionListener(this);

					// Storing places 
					buttonGrid[i][j] = offButton;
					panButtons.add(buttonGrid[i][j]);

				}

			}

		}

		// Grid making buttons
		gridButtons five = new gridButtons(5);
		five.addActionListener(this);
		gridButtons six = new gridButtons(6);
		six.addActionListener(this);
		gridButtons eight = new gridButtons(8);
		eight.addActionListener(this);
		gridButtons ten = new gridButtons(10);
		ten.addActionListener(this);

		// Make Random Buttons
		RandomButton style = new RandomButton(1);
		style.addActionListener(this);
		RandomButton boring = new RandomButton(2);
		boring.addActionListener(this);

		// Creates a manual button
		ManualButton mButton = new ManualButton();
		mButton.addActionListener(this);

		// Add Every button
		gridContainer.setLayout(new GridLayout(7, 1));
		gridContainer.add(style);
		gridContainer.add(boring);
		gridContainer.add(five);
		gridContainer.add(six);
		gridContainer.add(eight);
		gridContainer.add(ten);
		gridContainer.add(mButton);

		mainPanel.add(msgLabel, BorderLayout.SOUTH);
		mainPanel.add(panButtons, BorderLayout.CENTER);

		mainPanelFinal = new JPanel();

		mainPanelFinal.add(mainPanel, BorderLayout.CENTER);
		mainPanelFinal.add(gridContainer, BorderLayout.EAST);

		// Set up the JFrame.
		setTitle("Lights Out!!");
		setContentPane(mainPanelFinal);

		// Change preferred size according 
		if(gridSize == 1) 
			setPreferredSize(new Dimension(700, 300));
		if(gridSize == 2) 
			setPreferredSize(new Dimension(850, 350));
		if(gridSize == 3)
			setPreferredSize(new Dimension(900, 400));
		if(gridSize == 4) 
			setPreferredSize(new Dimension(1050, 500));

		//pack the contents
		pack();

	}

	/**
	 * Randomizes buttons to a new grid size
	 */
	private void randomButtons() {

		//To random each button
		Random rng = new Random();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				boolean indicator = rng.nextBoolean(); 
				if(indicator){
					// Counts down if button turned off
					if(!buttonGrid[i][j].isOn()){
						onButtons++;
						msgLabel.setText("There are "  + onButtons + " lights still on.");
					}
					if(buttonGrid[i][j].isOn()){
						onButtons--;
						msgLabel.setText("There are "  + onButtons + " lights still on.");
					}

					buttonGrid[i][j].toggle();

				}
			}

		}
	}


	/**
	 * Randoms the current grid size with style
	 * @throws InterruptedException 
	 */
	private void styleRandom() throws InterruptedException{

		//To random each button
		Random rng = new Random();

		//Loops through each grid spot adding each button to the grid as well as the matrix
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				buttonGrid[i][j].setEnabled(false);

				// Adds one to buttons only if original button wasn't on
				if(!buttonGrid[i][j].isOn()){
					onButtons++;
					msgLabel.setText("There are "  + onButtons + " lights still on.");
				}

				buttonGrid[i][j].turnOn();
			}

		}

		//Style loops these three loops random with style haha..
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				boolean indicator = rng.nextBoolean(); 
				if(indicator){
					buttonGrid[i][j].toggle();

					Thread.sleep(80);

					// Counts down if button turned off
					if(!buttonGrid[i][j].isOn()){
						onButtons--;
						msgLabel.setText("There are "  + onButtons + " lights still on.");
					}

				}
			}
		}

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				boolean indicator = rng.nextBoolean(); 
				if(indicator){
					// Counts down if button turned off
					if(!buttonGrid[i][j].isOn()){
						onButtons++;
						msgLabel.setText("There are "  + onButtons + " lights still on.");
					}
					if(buttonGrid[i][j].isOn()){
						onButtons--;
						msgLabel.setText("There are "  + onButtons + " lights still on.");
					}

					buttonGrid[i][j].toggle();

					Thread.sleep(35);
				}
			}

		}

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				boolean indicator = rng.nextBoolean(); 
				if(indicator){

					// Counts down if button turned off
					if(!buttonGrid[i][j].isOn()){
						onButtons++;
						msgLabel.setText("There are "  + onButtons + " lights still on.");
					}
					if(buttonGrid[i][j].isOn()){
						onButtons--;
						msgLabel.setText("There are "  + onButtons + " lights still on.");
					}

					buttonGrid[i][j].toggle();

					Thread.sleep(42);

				}
			}
		}


		//Enable all the buttons..
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				buttonGrid[i][j].setEnabled(true);
			}
		}
	}

	/**
	 * Makes a grid according to input
	 */
	private void makeGrid(){

		if(gridSize == 1){
			rows = 5;
			columns = 5;
			panButtons.setLayout(new GridLayout(rows, columns));
		}

		else if(gridSize == 2){
			rows = 7;
			columns = 7;
			panButtons.setLayout(new GridLayout(rows, columns));
		}
		else if(gridSize == 3){
			rows = 9;
			columns = 9;
			panButtons.setLayout(new GridLayout(rows, columns));
		}
		else if(gridSize == 4){
			rows = 11;
			columns = 11;
			panButtons.setLayout(new GridLayout(rows, columns));
		}

	}

	/**
	 * Randoms the bored into a certain style haha
	 * 
	 */
	private void randomStyle() {

		// To random each button
		Random rng = new Random();

		// Decides board placement
		int style = rng.nextInt(7);

		// Loops through each grid spot adding each button to the grid as well as the matrix
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {

				// Adds one to buttons only if original button wasn't on
				if(!buttonGrid[i][j].isOn()){
					onButtons++;
					msgLabel.setText("There are "  + onButtons + " lights still on.");
				}

				buttonGrid[i][j].turnOn();
			}

		}

		if(style == 0){

			//Style loops this into checkered patter
			int indicator = 0;
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {

					if(indicator % 2 == 0){
						buttonGrid[i][j].toggle();

						// Counts down if button turned off
						if(!buttonGrid[i][j].isOn()){
							onButtons--;
							msgLabel.setText("There are "  + onButtons + " lights still on.");
						}

					}

					// Makes patter possible
					indicator++;
				}
			}

		}

		// Second style
		else if(style == 1){

			int count = 0;

			//Style loops this into checkered patter
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {

					if(count % 3 == 1){
						buttonGrid[i][j].toggle();

						// Counts down if button turned off
						if(!buttonGrid[i][j].isOn()){
							onButtons--;
							msgLabel.setText("There are "  + onButtons + " lights still on.");
						}

					}

					count++;
				}
			}
		}

		// Second style
		else if(style == 2){

			int count = 0;

			//Style loops this into checkered patter
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {

					if(count % 3 == 0){
						buttonGrid[i][j].toggle();

						// Counts down if button turned off
						if(!buttonGrid[i][j].isOn()){
							onButtons--;
							msgLabel.setText("There are "  + onButtons + " lights still on.");
						}

					}

					count++;
				}
			}
		}


		// Second style
		else if(style == 3){

			int count = 0;

			//Style loops this into checkered patter
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {

					if(count % 3 == 2){
						buttonGrid[i][j].toggle();

						// Counts down if button turned off
						if(!buttonGrid[i][j].isOn()){
							onButtons--;
							msgLabel.setText("There are "  + onButtons + " lights still on.");
						}

					}

					count++;
				}
			}
		}

		// Second style
		else if(style == 4){

			//Style loops this into checkered patter
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {

					if(i != rows - 1 && i != 0 && j != columns -1 && j != 0){
						buttonGrid[i][j].toggle();

						// Counts down if button turned off
						if(!buttonGrid[i][j].isOn()){
							onButtons--;
							msgLabel.setText("There are "  + onButtons + " lights still on.");
						}

					}

				}
			}
		}

		// Second style
		else if(style == 5){

			//Style loops this into checkered patter
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {

					if(i == 1  || i == rows - 2 || j == 1 || j == columns - 2){
						buttonGrid[i][j].toggle();

						// Counts down if button turned off
						if(!buttonGrid[i][j].isOn()){
							onButtons--;
							msgLabel.setText("There are "  + onButtons + " lights still on.");
						}

					}

				}
			}
		}

		// Second style
		else if(style == 6){

			//Style loops this into checkered patter
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {

					if(i == (rows - 1) / 2  || j == (columns - 1) / 2 ){
						buttonGrid[i][j].toggle();

						// Counts down if button turned off
						if(!buttonGrid[i][j].isOn()){
							onButtons--;
							msgLabel.setText("There are "  + onButtons + " lights still on.");
						}

					}

				}
			}
		}

	}
}

