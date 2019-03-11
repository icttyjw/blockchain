package edu.ictt.blockchainmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import edu.ictt.blockchainmanager.view.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class Application  extends AbstractJavaFxApplicationSupport {
	 @Override
	    public void start(Stage primaryStage) throws Exception {
		 primaryStage.setTitle("SignUp");
			primaryStage.setResizable(false);
	        super.start(primaryStage);
	    }
	 public static void main(String[] args) {
		 launchApp(Application.class, MainView.class, args);
	 }
}
