package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.ClientGroupContext;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;

import com.fasterxml.jackson.core.sym.Name;
import com.google.common.collect.Table.Cell;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.BlockChainApplication;
import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.core.event.ChangeEvent;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;
import edu.ictt.blockchainmanager.view.AddSchoolNodeView;
import edu.ictt.blockchainmanager.view.UpdateSchoolNodeView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * NodesInGroup控制器
 * @author zoe
 *
 */
@FXMLController
public class NodesInGroupController implements Initializable{
	
    @FXML
    private ListView<String> nodesList;

    @FXML
    private Button refresh;
    
    @FXML
    private Pane circlePane;
    
    @FXML
    private Label name;
    
    @FXML
    private Label nodeType;
    
    @FXML
    private Label state;
    
    @FXML 
    private Label connectState;
    
    @FXML
    private Label lastConnect;
    
    @FXML
    private Button add;
    
    @FXML
    private Button del;
    
    @FXML
    private Button sel;
    
    @Resource
    AddSchoolNodeController addSchoolNodeController;
    
    ObservableList<String> nodeLists = FXCollections.observableArrayList();
    
    @Resource
    NodeService nodeService;
    
    @Resource
    private ClientGroupContext clientGroupContext;
    
    
    private Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//初始化时从sql数据库读取本地曾连接过的所有节点,默认都显示为灰色
		List<NodeState> nodeStates = nodeService.queryAllNodes();
		int i = 0;
		for(NodeState nodeState:nodeStates) {
			nodeLists.add(nodeState.getName());
			//这个暂时不能用同时根据节点个数生成圆
			//Circle circle = new Circle(0.5, Color.GRAY);
			//circlePane.getChildren().add(circle);
			
		}
		nodesList.setItems(nodeLists);
		nodesList.getSelectionModel().selectedItemProperty().addListener
		((ObservableValue<? extends String> Observable, String oldValue, String newValue) ->{
			System.out.println(newValue);
			//String nodeName = nodesList.getSelectionModel().selectedItemProperty().get();
			if(newValue!=null){
			NodeState nodeState = nodeService.queryByName(newValue);
			System.out.println(nodeState);
			name.setText(nodeState.getName());
			nodeType.setText(nodeState.getNodetype());
			state.setText(nodeState.getState());
			connectState.setText(nodeState.getConnectstate());
			lastConnect.setText(nodeState.getLastConnect());
			}
		}
		);
		//事件处理，点击节点显示节点详细信息
		
		/*nodesList.setOnMouseClicked(EventHandler<MouseEvent>(
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
						NodeState nodeState = nodeService.queryByName(nodesList.getSelectionModel().selectedItemProperty().get());
						name.setText(nodeState.getName());
						nodeType.setText(nodeState.getNodetype());
						state.setText(nodeState.getState());
						connectState.setText(nodeState.getConnectstate());
						lastConnect.setText(nodeState.getLastConnect());
						
					}
				}));*/
		}
		
		//nodesList.setCellFactory(new <Callback<ListView<String>, ListCell<String>>());
		// Set the CellFactory for the ListView
		/*nodesList.setCellFactory(list -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        // There is no item to display in this cell, so leave it empty
                        setGraphic(null);
                    	setTooltip(new Tooltip("这是一条提示信息"));
                        // Clear the style from the cell
                       // setStyle(null);
                    } //else {
                        // If the item is equal to the first item in the list, set the style
                      //  if (item.equalsIgnoreCase(list.getItems().get(0))) {
                            // Set the background color to blue
                       //     setStyle("-fx-background-color: blue; -fx-text-fill: white");
                      //  }
                        // Finally, show the item text in the cell
                       // setText(item);

                   // }
                }
            };
            return cell;
        });*/
	
	
		
		/*//nodesList.getSelectionModel().selectedItemProperty().addListener
		((ObservableValue<? extends String> Observable, String oldValue, String newValue) ->{
			NodeState nodeState = nodeService.queryByName(nodesList.getSelectionModel().selectedItemProperty().get());
			name.setText(nodeState.getName());
			nodeType.setText(nodeState.getNodetype());
			state.setText(nodeState.getState());
			connectState.setText(nodeState.getConnectstate());
			lastConnect.setText(nodeState.getLastConnect());
		}
		);*/
	
	
	//暂时无用
	@FXML
	void refresh(ActionEvent event) {
		/*
		//获取当前连接的节点，设置列表和圆的显示
		SetWithLock<ChannelContext> setWithLock = Tio.getAllChannelContexts(clientGroupContext);
        Set<ChannelContext> set = setWithLock.getObj();
        //已连接的节点集合
        Set<Node> connectedNodes = set.stream().map(ChannelContext::getServerNode).collect(Collectors.toSet());
        int j=0;
        //遍历当前的连接状态
        for (String node : nodeLists) {
            if (!connectedNodes.contains(node)) {
                circlePane.setVisible(false);
                }
            }*/
		nodeLists.clear();
		List<NodeState> nodeStates = nodeService.queryAllNodes();
		for(NodeState nodeState:nodeStates) {
			nodeLists.add(nodeState.getName());
			//这个暂时不能用同时根据节点个数生成圆
			
		}
    }
    
	  @FXML
	    void delete(ActionEvent event) {
		  	String name=nodesList.getSelectionModel().selectedItemProperty().get();
		  	nodeService.deleteByName(name);
		  	nodeLists.remove(name);
	    }

	  @FXML
	  void select(ActionEvent event){
		  String name=nodesList.getSelectionModel().selectedItemProperty().get();
		  //ApplicationContextProvider.getBean(AddSchoolNodeController.class).namemodel.setText(name);
		  ApplicationContextProvider.publishEvent(new ChangeEvent(name));
		  //addSchoolNodeController.namemodel.setText(name);
		  BlockChainApplication.showView(UpdateSchoolNodeView.class, Modality.APPLICATION_MODAL);
	  }


	    @FXML
	    void addnode(ActionEvent event) {
	    	
	    	BlockChainApplication.showView(AddSchoolNodeView.class, Modality.APPLICATION_MODAL);
	    }
	
}
