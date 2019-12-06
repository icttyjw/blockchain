package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;

@FXMLController
public class LogController implements Initializable{

	@FXML
    private Hyperlink link1;
	
	@FXML
    private Hyperlink link2;
	
	@FXML
    private Hyperlink link3;
	
	 @FXML
	    private Button refresh;

	    @FXML
	    private TextArea text;
	    
	    @FXML
	    private ChoiceBox<String> choicebox;

	    @FXML
	    void refresh(ActionEvent event) {
	    	text.clear();
	    	String value=choicebox.getValue();
			String direct;
	    	if(value.equals("log")){
				direct="logs1/log/logFile.";
			}else{
				direct="logs1/error/errorFile.";
			}
	    	String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
	    	printtext(direct+date+".log");
	    }
	    
	    @FXML
	    void link1(ActionEvent event) {
	    	String value=choicebox.getValue();
			String direct;
	    	if(value.equals("log")){
				direct="logs1/log/logFile.";
			}else{
				direct="logs1/error/errorFile.";
			}
	    	text.clear();
	    	String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
	    	printtext(direct+date+".log");
	    	
	    }
	    @FXML
	    void link2(ActionEvent event) {
	    	String value=choicebox.getValue();
			String direct;
	    	if(value.equals("log")){
				direct="logs1/log/logFile.";
			}else{
				direct="logs1/error/errorFile.";
			}
	    	text.clear();
	    	Date d=new Date();
	    	Calendar c= Calendar.getInstance();
			c.setTime(d);
			int day=c.get(Calendar.DATE);
			c.set(Calendar.DATE,day-1);
	    	String date=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()).toString();
	    	printtext(direct+date+".log");
	    	
	    }
	    @FXML
	    void link3(ActionEvent event) {
	    	String value=choicebox.getValue();
			String direct;
	    	if(value.equals("log")){
				direct="logs1/log/logFile.";
			}else{
				direct="logs1/error/errorFile.";
			}
	    	text.clear();
	    	Date d=new Date();
	    	Calendar c= Calendar.getInstance();
			c.setTime(d);
			int day=c.get(Calendar.DATE);
			c.set(Calendar.DATE,day-2);
	    	String date=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()).toString();
	    	printtext(direct+date+".log");
	    	
	    }

		@Override
		public void initialize(URL location, ResourceBundle resources){
			settime();
			choicebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					// TODO Auto-generated method stub
					System.out.println(choicebox.getItems().get((Integer) newValue));
					System.out.println(choicebox.getValue());
					String value=choicebox.getItems().get((Integer) newValue);
					String direct;
					if(value.equals("log")){
						direct="logs1/log/logFile.";
					}else{
						direct="logs1/error/errorFile.";
					}
			    	text.clear();
			    	Date d=new Date();
			    	Calendar c= Calendar.getInstance();
					c.setTime(d);
					int day=c.get(Calendar.DATE);
					c.set(Calendar.DATE,day);
			    	String date=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()).toString();
			    	printtext(direct+date+".log");
				}

			});;
			String direct="logs1/log/logFile.";
	    	String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
	    	printtext(direct+date+".log");
		}
		
		
		private  void settime(){
			Date d=new Date();
			String date=new SimpleDateFormat("MM-dd").format(d).toString();
			Calendar c= Calendar.getInstance();
			c.setTime(d);
			int day=c.get(Calendar.DATE);
			c.set(Calendar.DATE,day-1);
			String dayBefore=new SimpleDateFormat("MM-dd").format(c.getTime()).toString();
			c.set(Calendar.DATE,day-2);
			String dayBefore2=new SimpleDateFormat("MM-dd").format(c.getTime()).toString();
			link1.setText(date);
			link2.setText(dayBefore);
			link3.setText(dayBefore2);
		}
		
		private void printtext(String direct){
			try{
		    	FileInputStream fstream=new FileInputStream(new File(direct));
		    	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));//构造一个BufferedReader，里面存放在控制台输入的字节转换后成的字符。
		    	for(String str=br.readLine();str!=null;str=br.readLine()){
		    		text.appendText(str+"\n");
		    	}
		    	br.close();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		text.appendText("当前无日志信息");
	    	}
		}


}

