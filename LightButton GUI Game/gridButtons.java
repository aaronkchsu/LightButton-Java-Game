package assign9;

import javax.swing.JButton;

public class gridButtons extends JButton {

	private int gridSize;
	
	/**
	 * Takes in grid size representation variable
	 * @param gridSize_
	 */
	public gridButtons(int gridSize_){
		if(gridSize_ == 5){
		gridSize = gridSize_;
		setText("5 X 5 Grid");
		}
		else if(gridSize_ == 6){
		gridSize = gridSize_;
		setText("7 X 7 Grid");
		}
		else if (gridSize_ == 8) {
		gridSize = gridSize_;
		setText("9 X 9 Grid");
		}
		else if(gridSize_ == 10){
		gridSize = gridSize_;
		setText("11 X 11 Grid");
		}
		
	}
	
	/**
	 * Takes number used to get grid
	 * @returns a grid number
	 */
	public int getGrid(){
		
		//Number used to determine which grid is which
		return gridSize;
	}
}
