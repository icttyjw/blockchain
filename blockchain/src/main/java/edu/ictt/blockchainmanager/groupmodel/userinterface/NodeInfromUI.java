package edu.ictt.blockchainmanager.groupmodel.userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NodeInfromUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// 读取SignUp.fxml文件生成界面
			Parent root = FXMLLoader.load(getClass().getClassLoader()
					.getResource("edu/ictt/blockchainmanager/groupmodel/userinterface/NodesInfromUI.fxml"));

			primaryStage.setTitle("SignUp");
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
