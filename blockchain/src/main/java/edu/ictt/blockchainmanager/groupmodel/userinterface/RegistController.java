/**
 * Sample Skeleton for 'Regist.fxml' Controller Class
 */

package edu.ictt.blockchainmanager.groupmodel.userinterface;

import javax.annotation.Resource;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchainmanager.Application;
import edu.ictt.blockchainmanager.groupmodel.Node;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;
import edu.ictt.blockchainmanager.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@FXMLController
public class RegistController {

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="main"
    private TextField main; // Value injected by FXMLLoader

    @FXML // fx:id="type"
    private TextField type; // Value injected by FXMLLoader

    @FXML // fx:id="priKey"
    private TextField priKey; // Value injected by FXMLLoader

    @FXML // fx:id="regist"
    private Button regist; // Value injected by FXMLLoader

    @FXML // fx:id="pubKey"
    private TextField pubKey; // Value injected by FXMLLoader
    
    @Resource
    private NodeService nodeService;

    @FXML
    void regist(ActionEvent event) {
    	NodeState ns=new NodeState(null, name.getText(), null, null, null, main.getText(), 
    					type.getText(), null, pubKey.getText(), priKey.getText());
    	//nodeService.saveLocalNode(ns);
    	Application.showView(MainView.class);
    }

}
