import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import Screens.CustomerScreen;
import Screens.StockScreen;


public class ATDProgramNew extends Application {

	private TabPane tabsScreen;
	private Tab customerAdministration;
	private Tab customerRelations;
	private Tab stockAdministration;
	private Scene mainScene;
	@Override
	public void start(Stage stage) throws Exception {
		//create tabs and add content
		tabsScreen = new TabPane();

		customerAdministration = new Tab("Klantenbestand");
		customerAdministration.setClosable(false);
		customerAdministration.setContent(new CustomerScreen());

		customerRelations = new Tab("Herinneringen");
		customerRelations.setClosable(false);
		//TODO:customerRelations.setContent(arg0);

		stockAdministration = new Tab("Voorraadbeheer");
		stockAdministration.setClosable(false);
		stockAdministration.setContent(new StockScreen());
		
		tabsScreen.getTabs().addAll(customerAdministration, customerRelations,
				stockAdministration);

		// Create Mainscreen
		mainScene = new Scene(tabsScreen, 1024, 655);
		stage.setScene(mainScene);
		stage.setTitle("AutoTotaalDienst");
		stage.setResizable(false);
		stage.show();
	}
	public static void main(String[] args) {launch();}
}
