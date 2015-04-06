
package screens;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import main.ATDProgram;
import main.Customer;

public abstract class Screen extends HBox {
	private final ATDProgram controller;
	private ListItem selectedItem;
	private boolean isChanging;
	private static final double
	space_Small = 10,
	space_Big 	= 20,
	space_3 	= 15,
	button_3 	= 140,
	label_Normal= 120;

	public Screen(ATDProgram controller) {
		this.controller = controller;
	}
		
	protected abstract void changeFilter(int newValue);
	/*
	 * removes the selected item
	 */
	protected abstract void remove();
	/**
	 * saves the input in either a selected item or a new item
	 */
	protected abstract void save();
	/**
	 * refreshes the list and every item itself
	 */
	protected abstract void refreshList();
	/**
	 * disables/enables the left or right side of the stage
	 * @param enable
	 */
	protected abstract void setEditable(boolean enable);	
	/**
	 * clears all the inputfields
	 */
	protected abstract void clearInput();
	/**
	 * checks if all the inputfields are filled in
	 * @return false if one of the inputs is null
	 */
	protected abstract boolean checkInput();
	/**
	 * checks if an item is selected in the list
	 * @return false if nothing is selected
	 */
	protected abstract boolean checkSelected();
	/**
	 * searches through all items in the list
	 * @param oldVal the previous content of the searchfield
	 * @param newVal the new content of the searchfield
	 */
	protected abstract void search(String oldVal, String newVal);
	// this class represents every item in the list
	public class ListItem extends HBox{
		private Customer object;
		private Label itemPostalLabel = new Label(),itemNameLabel = new Label(),itemEmailLabel = new Label();
		public ListItem(Customer object){
			//no filter
			this.object = object;
			refresh();
			setSpacing(5);
			getChildren().addAll(
					itemNameLabel,
					new Separator(Orientation.VERTICAL),
					itemPostalLabel,
					new Separator(Orientation.VERTICAL),
					itemEmailLabel);
			for (Node node : getChildren()) 
				if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		/**
		 * fills in all the labels with the latest values
		 */
		public void refresh(){
			itemNameLabel.setText(object.getName());
			itemPostalLabel.setText(object.getPostal());
			itemEmailLabel.setText(object.getEmail());
		}
		/**
		 * @return the object this item represents
		 */
		public Customer getCustomer(){
			return object;
		}
	}
}

