package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.print.DocFlavor.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchainmanager.Application;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.groupmodel.UITask;
import edu.ictt.blockchainmanager.socket.test.server.BlockServerStarter;
import edu.ictt.blockchainmanager.sql.service.NodeService;
import edu.ictt.blockchainmanager.view.AlertView;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;

/**
 * NodesInfrom Controller
 * @author zoe
 *
 */
@FXMLController
public class NodesInfromUIController  implements Initializable{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
    @FXML
    private Label LastCon;

    @FXML
    private Label Ip;

    @FXML
    private Label ip;

    @FXML
    private Label constate;

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
    
    @FXML
    private Button refresh;
    
    @Resource
    private NodeService nodeService;
    
    @FXML
    private Button start;

    @FXML
    void jumpToSchool(ActionEvent event) {

    }
    
    public void save(ActionEvent event){
    	NodeState nodeState=new NodeState(null, "node", "127", "1", "0", "1","1", "0", "pubKey", "priKey");
    	nodeService.saveLocalNode(nodeState);
    }

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		logger.info("Node initial");
		//Application.getScene().getWindow().setWidth(480);
		//Application.getScene().getWindow().setHeight(250);
		NodeState nodeState=nodeService.queryLocalNode("1");
		id.setText(nodeState.getId());
		name.setText(nodeState.getName());
		pubkey.setText(nodeState.getPubKey());
		state.setText(nodeState.getState());
		ip.setText(nodeState.getIp());
	}
	
	public void statr(ActionEvent event){
		Task<Void> task=new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				BlockServerStarter.serverStart();
				return null;
			}
			
			@Override
			protected void succeeded(){
				Application.showView(AlertView.class, Modality.WINDOW_MODAL);
			}
			
		};
		Thread th=new Thread(task);
		th.setDaemon(true);
		th.start();
		
	}

}
