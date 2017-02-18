package department;

import department.config.MainConfig;
import department.di.Injector;
import department.ui.utils.UiUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class Runner extends Application {
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(final Stage primaryStage) throws Exception {
		// loads and initializes application
		// context from specified XML file
		Injector.initialize(MainConfig.class);
		// load first view to show and setup primary stage
		primaryStage.setScene(new Scene(UiUtils.newLoader("/view/main.fxml").load()));
		primaryStage.show();
	}

}
