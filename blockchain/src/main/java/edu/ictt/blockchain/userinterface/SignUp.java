package edu.ictt.blockchain.userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignUp extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			//读取SignUp.fxml文件生成界面
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("edu/ictt/blockchain/userinterface/SignUp.fxml"));
			primaryStage.setTitle("SignUp");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
