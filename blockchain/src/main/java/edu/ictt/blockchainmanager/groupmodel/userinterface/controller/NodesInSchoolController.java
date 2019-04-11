package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * NodesInSchool控制器
 * @author zoe
 *
 */
@FXMLController
public class NodesInSchoolController implements Initializable {
	
	@FXML
	private ListView nodesList;
	
	public void showList() {
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.add("I'm a test item");
		list.add("Me,too");
		nodesList.setItems(list);
		
	}

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showList();
	}
	
	
}
