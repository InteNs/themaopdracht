
package screens;
import javafx.scene.layout.HBox;
import main.ATDProgram;

public abstract class Screen extends HBox {
	protected ATDProgram controller;
	public Screen(ATDProgram controller) {
		this.controller = controller;
	}
	public abstract void refreshList();
}

