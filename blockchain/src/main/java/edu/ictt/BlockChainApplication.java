package edu.ictt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import edu.ictt.blockchainmanager.view.MainView;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BlockChainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockChainApplication.class, args);
	}
}*/
@SpringBootApplication
@EntityScan(value="edu.ictt.blockchainmanager.groupmodel")
@EnableJpaRepositories(value="edu.ictt.blockchainmanager.sql.repository")
//@ComponentScan(basePackages = {"edu.ictt.blockchainmanager.sql.repository","edu.ictt.blockchainmanager.groupmodel",
//		"edu.ictt.blockchainmanager.sql.service"})
public class BlockChainApplication  extends AbstractJavaFxApplicationSupport {
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
	     launchApp(BlockChainApplication.class, MainView.class, args);
	 }
}
