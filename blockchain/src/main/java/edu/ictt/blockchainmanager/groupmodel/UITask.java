package edu.ictt.blockchainmanager.groupmodel;

import java.io.IOException;

import edu.ictt.blockchainmanager.groupmodel.userinterface.AlertController;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;

public class UITask {

	public static Task<Void> task=new Task<Void>(){

		@Override
		protected Void call() throws Exception {
			updateMessage("update");
			return null;
		}
		@Override
    	protected void succeeded(){
    		//alertController.setText("recevid");
    		//StringProperty msg=new SimpleStringProperty("suc");
    		FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("edu/ictt/blockchainmanager/groupmodel/userinterface/Alert.fxml"));
			try {
				loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateMessage("suc");
    		AlertController alertController=loader.getController();
			alertController.set("suc");
    	}
	};
}
