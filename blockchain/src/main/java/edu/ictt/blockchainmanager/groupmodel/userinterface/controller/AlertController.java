package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchainmanager.Application;
import edu.ictt.blockchainmanager.groupmodel.UITask;
import edu.ictt.blockchainmanager.view.AlertView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class AlertController implements Initializable{
	 @FXML
	    private Label text;
	 
	 public Label getText() {
		return text;
	}
	 
	 //@Resource
	 //private MessageProperty messageProperty;

	public void set(String cont){
		 text.textProperty().bind(UITask.task.messageProperty());;
		 Application.showView(AlertView.class, Modality.WINDOW_MODAL);
	 }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 text.textProperty().bind(UITask.task.messageProperty());;
		
	}
}
