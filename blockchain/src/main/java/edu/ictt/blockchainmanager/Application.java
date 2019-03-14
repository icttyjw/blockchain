package edu.ictt.blockchainmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import edu.ictt.blockchainmanager.view.MainView;
import javafx.stage.Stage;

@SpringBootApplication
@EntityScan(value="edu.ictt.blockchainmanager.groupmodel")
@EnableJpaRepositories(value="edu.ictt.blockchainmanager.sql.repository")
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
