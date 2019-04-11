package edu.ictt.blockchainmanager.console;

import java.util.List;

import javax.annotation.Resource;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.Status;
import edu.ictt.blockchainmanager.groupmodel.userinterface.controller.TextAreaSample;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import ch.qos.logback.core.UnsynchronizedAppenderBase;


/**
 *
 * @author Russell Shingleton <shingler@oclc.org>
 */
public class TextAreaAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	Layout<ILoggingEvent> layout;
	
	public Layout<ILoggingEvent> getLayout() {
		return layout;
	}

	public void setLayout(Layout<ILoggingEvent> layout) {
		this.layout = layout;
	}

	private static  TextArea textArea;
	public static void setTextArea(TextArea textArea){
		TextAreaAppender.textArea=textArea;
	}
	
	 @Override
	    public void start(){
	        //这里可以做些初始化判断 比如layout不能为null ,
	        if(layout == null) {
	            addWarn("Layout was not defined");
	        }
	        //或者写入数据库 或者redis时 初始化连接等等
	         super.start();
	    }
	
	@Override
	protected void append(ILoggingEvent eventObject) {
		// TODO Auto-generated method stub
		if(eventObject==null||!isStarted()){
			return;
		}
		if (textArea != null) {
            if (textArea.getText().length() == 0) {
            	textArea.setText("hello1/n");
                textArea.setText(layout.doLayout(eventObject));
            } else {
            	 
                textArea.selectEnd();
                //textArea.insertText(textArea.getText().length(),"hello\n");
               // textArea.insertText(textArea.getText().length(),"2\n");
                textArea.insertText(textArea.getText().length(),
                		layout.doLayout(eventObject));
            }
		}
	}



}