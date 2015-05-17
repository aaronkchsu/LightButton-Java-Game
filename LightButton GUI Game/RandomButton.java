package assign9;

import javax.swing.JButton;

public class RandomButton extends JButton {

	private String type;
	
	/**
	 * Two types of random buttons one that randoms with style one that randoms boring
	 * @param type_ 1 is style 2 is boring
	 */
	public RandomButton(int type_){
		super("");
		//Two types depending on how you create it
		if(type_ == 1){
			type = "style";
			setText("Random with STYLE");
		}
		if(type_ == 2){
			type = "boring";
			setText("Boring Random");
		}
	}
	
	/**
	 * Figures out what type of button
	 * @return style or boring
	 */
	public String getType(){
		return type;
	}
}
