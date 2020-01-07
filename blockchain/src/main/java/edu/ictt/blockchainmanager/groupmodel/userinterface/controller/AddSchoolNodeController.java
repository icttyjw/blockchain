package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.context.event.EventListener;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchain.core.event.ChangeEvent;
import edu.ictt.blockchainmanager.groupmodel.NameModel;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@FXMLController
public class AddSchoolNodeController implements Initializable{
	
	public static NameModel namemodel=new NameModel();

	
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

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // update text area if text in model changes:
    	//System.out.println("load controller");
    	//System.out.println(""+namemodel.getText());
    	//NodeState nodeState=nodeService.queryByName(namemodel.getText());
    	//id.setText(nodeState.getId()+"");
    	//ip.setText(nodeState.getIp());
    	/*
        namemodel.textProperty().addListener((obs, oldText, newText) -> {
        	System.out.println(oldText);
        	System.out.println(obs.getValue());
        	NodeState nodeState=nodeService.queryByName(newText);
        	id.setText(nodeState.getId()+"");
        	ip.setText(nodeState.getIp());
        });*/
    }
    
    @FXML
    public void change(){
    	System.out.println("change");
    	namemodel.textProperty().addListener((obs, oldText, newText) -> {
        	System.out.println(oldText);
        	System.out.println(obs.getValue());
        	NodeState nodeState=nodeService.queryByName(newText);
        	id.setText(nodeState.getId()+"");
        	ip.setText(nodeState.getIp());
        });
    }
    //@EventListener(ChangeEvent.class)
    public void changename(ChangeEvent changeEvent){
    	
    	String name=(String)changeEvent.getSource();
    	//System.out.println(name);
    	namemodel.setText(name);
    	change();
    	//System.out.println(name);
    	//NodeState nodeState=nodeService.queryByName(name);
    	//System.out.println(nodeState.getId());
    	//id.setText(nodeState.getId()+"");
    	//ip.setText(nodeState.getIp());
    }

    @FXML
    void addnode(ActionEvent event) {
    	String nodeid=id.getText();
    	String nodeip=ip.getText();
    	String nodename=name.getText();
    	String nodemain=main.getText();
    	String nodetype=type.getText();
    	NodeState nodestate=new NodeState(Integer.valueOf(nodeid), nodename, nodeip, null, null, nodemain, nodetype, null, null, null);
    	nodeService.saveLocalNode(nodestate);
    	Stage st=(Stage)add.getScene().getWindow();
    	st.close();
    	id.setText(null);
    	ip.setText(null);
    	name.setText(null);
    	main.setText(null);
    	type.setText(null);
    			
    }
	
}
