package edu.ictt.blockchainmanager.groupmodel;

import javafx.application.Platform;

public enum UiBaseService {

	INSTANCE;
	
	public void runTaskInFxThread(Runnable task){
		Platform.runLater(task);
	}
}
