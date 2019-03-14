package edu.ictt.blockchainmanager.groupmodel.userinterface;

import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.print.DocFlavor.URL;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * NodesInfrom Controller
 * @author zoe
 *
 */
@FXMLController
public class NodesInfromUIController  implements Initializable{

	@FXML
	private AnchorPane anchorPane;
    @FXML
    private Label LastCon;

    @FXML
    private Label Ip;

    @FXML
    private Label ip;

    @FXML
    private Label constate;

    @FXML
    private Button refresh;

    @FXML
    private Label Name;

    @FXML
    private Label ConState;

    @FXML
    private Label State;

    @FXML
    private Label name;

    @FXML
    private Label Id;

    @FXML
    private Label state;

    @FXML
    private Label id;

    @FXML
    private Label lasocon;

    @FXML
    private Label pubKey;

    @FXML
    private Label pubkey;
    
    @FXML
    private Button save;
    
    @Resource
    private NodeService nodeService;

    @FXML
    void jumpToSchool(ActionEvent event) {

    }
    
    public void save(ActionEvent event){
    	
    }

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		//anchorPane.getScene().getWindow().setHeight(223);
		anchorPane.setMaxSize(450, 223);
		anchorPane.setPrefHeight(223);
		anchorPane.setPrefWidth(450);
		//anchorPane.getScene().getWindow().setWidth(450);
		NodeState nodeState=nodeService.queryLocalNode("1");
		id.setText(nodeState.getId());
		name.setText(nodeState.getName());
		pubkey.setText(nodeState.getPubKey());
		state.setText(nodeState.getState());
		ip.setText(nodeState.getIp());
	}

}
