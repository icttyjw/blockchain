package edu.ictt.blockchainmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import edu.ictt.blockchainmanager.console.TextAreaAppender;
import edu.ictt.blockchainmanager.view.MainView;
import edu.ictt.blockchainmanager.view.RegistView;
import edu.ictt.blockchainmanager.view.TextView;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@SpringBootApplication
@EntityScan(value="edu.ictt.blockchainmanager.groupmodel")
@EnableJpaRepositories(value="edu.ictt.blockchainmanager.sql.repository")
//@ComponentScan(basePackages = {"edu.ictt.blockchainmanager.sql.repository","edu.ictt.blockchainmanager.groupmodel",
//		"edu.ictt.blockchainmanager.sql.service"})
public class Application  extends AbstractJavaFxApplicationSupport {
	private final TextArea loggingView = new TextArea(); 
	
	
	@Override
	    public void start(Stage primaryStage) throws Exception {
		 //primaryStage.setTitle("SignUp");
		   primaryStage.setResizable(false);
		//TextAreaAppender.setTextArea(loggingView);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
	        super.start(primaryStage);
	       // setupLogginView();
	    }
	 public static void main(String[] args) {
	     launchApp(Application.class, MainView.class, args);
	 }
	 private void setupLogginView() {
	        loggingView.setLayoutX(17);
	        loggingView.setLayoutY(64);
	        loggingView.setPrefWidth(723);
	        loggingView.setPrefHeight(170);
	        loggingView.setWrapText(true);
	        loggingView.appendText("Starting Application");
	        loggingView.setEditable(false);
	    }
}
