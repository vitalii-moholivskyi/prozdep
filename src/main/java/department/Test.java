package department;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"appContext.xml");
		
		ISomeService service = (ISomeService) context.getBean("SomeService");
		
		service.f();
		
		
		// enable annotation processing first in compiler options!
		LombokTest lombok = new LombokTest(1);
		System.out.println(lombok.getField());
	}

}
