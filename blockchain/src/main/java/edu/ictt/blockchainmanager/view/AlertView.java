package edu.ictt.blockchainmanager.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.scene.control.Alert;

@FXMLView(value="/edu/ictt/blockchainmanager/groupmodel/userinterface/fxml/Alert.fxml")
public class AlertView extends AbstractFxmlView{

	public void AlertLogin(){
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("信息提示对话框");
		alert.setHeaderText(null);
		alert.setContentText("只显示文本内容");
	}
}
