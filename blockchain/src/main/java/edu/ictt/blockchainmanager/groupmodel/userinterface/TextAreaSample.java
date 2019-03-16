package edu.ictt.blockchainmanager.groupmodel.userinterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchainmanager.console.TextAreaAppender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
@FXMLController
public class TextAreaSample {

	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	 @FXML
	    private Button button;
	
    @FXML
    private  TextArea textarea;
    
    public TextArea getTextarea(){
    	return this.textarea;
    }

    public void setupLogginView(ActionEvent event) {
    	logger.info("hello");
    	TextAreaAppender.setTextArea(textarea);
    	textarea.setWrapText(true);
    	textarea.appendText("Starting Application");
    	textarea.setEditable(false);
    }
}
