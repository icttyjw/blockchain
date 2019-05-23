package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import javax.annotation.Resource;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@FXMLController
public class AddSchoolNodeController {
    @FXML
    private Button add;

    @FXML
    private TextField ip;

    @FXML
    private TextField name;

    @FXML
    private TextField main;

    @FXML
    private TextField type;
    
    @FXML
    private TextField id;
    
    @Resource
    private NodeService nodeService;

    @FXML
    void addnode(ActionEvent event) {
    	String nodeid=id.getText();
    	String nodeip=ip.getText();
    	String nodename=name.getText();
    	String nodemain=main.getText();
    	String nodetype=main.getText();
    	NodeState nodestate=new NodeState(Integer.valueOf(nodeid), nodename, nodeip, null, null, nodemain, nodetype, null, null, null);
    	nodeService.saveLocalNode(nodestate);
    	Stage st=(Stage)add.getScene().getWindow();
    	st.close();
    			
    }
	
}
