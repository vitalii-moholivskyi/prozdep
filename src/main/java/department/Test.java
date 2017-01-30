package department;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test extends Application {
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> {
				System.out.println("Hello");
		});

		BorderPane root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
		Tab _tabList = FXMLLoader.load(getClass().getResource("/view/partials/_listTab.fxml"));
		Tab _tabCreateMaster = FXMLLoader.load(getClass().getResource("/view/partials/_createMasterTab.fxml"));
		TabPane contentData = (TabPane) root.lookup("#contentPane");
		_tabList.setText("Список");
		_tabCreateMaster.setText("Створити");
		contentData.getTabs().add(_tabList);
		contentData.getTabs().add(_tabCreateMaster);
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"appContext.xml");
		
		ISomeService service = (ISomeService) context.getBean("SomeService");
		
		service.f();
		
		
		//enable annotation processing first in compiler options!
		LombokTest lombok = new LombokTest(1);
		System.out.println(lombok.getField());
	}

}
