package edu.ictt.blockchainmanager.groupmodel.userinterface;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchainmanager.console.TextAreaAppender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
@FXMLController
public class TextAreaSample implements Initializable{

	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	 @FXML
	    private Button button;
	
    @FXML
    private  TextArea textarea;
    
    public void setupLogginView(ActionEvent event) {
    	
    	logger.info("hello");
    	textarea.setWrapText(true);
    	textarea.appendText("Starting Application\n");
    	textarea.setEditable(false);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TextAreaAppender.setTextArea(textarea);
		
	}
}
