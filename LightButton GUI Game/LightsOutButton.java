package assign9;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class LightsOutButton extends JButton {

	// Instance variable to indicate whether on or off
	private boolean lightsOn;
	private int row;
	private int column;
	
	
	/**
	 * Constructs a button to be on at default
	 */
	public LightsOutButton(int rows, int columns) {

		// Sets text on and font on 24
		super("ON");
		setFont(new Font("Dialog", Font.PLAIN, 24));

		//Set parameters
		this.row = rows;
		this.column = columns;
				
		// Light is on
		lightsOn = true;

		// Background is yellow
		setBackground(Color.YELLOW);

	}

	/**
	 * Turns off the button
	 */
	public void turnOff() {

		// Set text as off
		setText("OFF");

		// Switches button to false
		lightsOn = false;

		// Changes background color to white
		setBackground(Color.WHITE);
	}

	/**
	 * Turns the button on which is pretty much reset to constructor settings
	 */
	public void turnOn(){

		// Sets text on and font on 24
		setText("ON");
		setFont(new Font("Dialog", Font.PLAIN, 24));

		// Light is on
		lightsOn = true;

		// Background is yellow
		setBackground(Color.YELLOW);
	}

	/**
	 * Returns the column that the button is on
	 * @return a int position
	 */
	public int getColumn(){
		return column;
	}
	
	/**
	 * 
	 * @return the row that the button is on
	 */
	public int getRow(){
		return row;
	}
	/**
	 * Says if lights on or off
	 * @return the lightsOn instance variable
	 */
	public boolean isOn() {

		// Instance variable
		return lightsOn;
	}
	
	/**
	 * Toggle Method used to change the status of a button
	 */
	public void toggle(){
		if(isOn()){
			turnOff();
		}
		else{
			turnOn();
		}
	}
}
