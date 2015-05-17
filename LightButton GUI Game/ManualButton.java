package assign9;

import java.awt.Color;

import javax.swing.JButton;

public class ManualButton extends JButton{

	private boolean manual;
	
	/**
	 * Button begins at false
	 */
	public ManualButton() {
		super("Enter Manual Setup");
		//Begins false until button is clicked
		manual = false;
	}
	
	/**
	 * Toggle method for manual
	 */
	public void toggle(){
		
		// If manual is true send it to false else make it true
		if(manual){
			manual = false;
			setText("Enter Manual Setup");
			setForeground(Color.BLACK);
		}
		else{
			manual = true;
			setText("Exit Manual Setup");
			// Background is yellow
			setForeground(Color.RED);
		}
	}
	
	/**
	 * Indicates wether manual is on or off
	 */
	public boolean manualOn(){
		return manual;
	}
}
