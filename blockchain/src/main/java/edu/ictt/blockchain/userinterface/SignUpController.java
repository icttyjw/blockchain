package edu.ictt.blockchain.userinterface;
import edu.ictt.blockchain.bean.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

public class SignUpController {
	
	/**
	 * 菜单栏：校内节点
	 */
	@FXML
	private Menu NodesInSchool;
	
	/**
	 * 菜单栏：组间节点
	 */
	@FXML
	private Menu NodesInGroup;
	
	/**
	 * 菜单栏：所有高校节点
	 */
	@FXML
	private Menu AllSchoolNodes;
	
	/**
	 * 当前节点信息
	 */
	private Label CurNodeInfo;
	
	/**
	 * 当前节点id值
	 */
	private Label id;
	
	/**
	 * 当前节点代表的高校名值
	 */
	private Label name;
	
	/**
	 * 当前节点通信状态值
	 */
	private Label constate;

	/**
	 * 当前节点最后一次通信时间值
	 */
	private Label lastcon;
	
	/**
	 * 当前节点ip值
	 */
	@FXML
	private Label ip;
	
	/**
	 * 当前节点运行状态值
	 */
	@FXML
	private Label state;
	
	/**
	 * 当前节点公钥值
	 */
	@FXML
	private Label pubkey;
	
	
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	
	
	
}
