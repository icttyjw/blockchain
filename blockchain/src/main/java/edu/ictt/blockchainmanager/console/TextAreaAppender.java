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
import edu.ictt.blockchainmanager.groupmodel.userinterface.TextAreaSample;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import ch.qos.logback.core.UnsynchronizedAppenderBase;


/**
 *
 * @author Russell Shingleton <shingler@oclc.org>
 */
public class TextAreaAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	Layout<ILoggingEvent> layout;
	private static  TextArea textArea;
	public static void setTextArea(TextArea textArea){
		TextAreaAppender.textArea=textArea;
	}
	
	@Override
	protected void append(ILoggingEvent eventObject) {
		// TODO Auto-generated method stub
		if(eventObject==null||!isStarted()){
			System.out.println(3);
			return;
		}
		if (textArea != null) {
            if (textArea.getText().length() == 0) {
            	System.out.println(1);
            	textArea.setText("hello");
                textArea.setText(layout.doLayout(eventObject));
            } else {
            	 
                textArea.selectEnd();
                textArea.insertText(textArea.getText().length(),"hello\n");
                textArea.insertText(textArea.getText().length(),"2\n");
                textArea.insertText(textArea.getText().length(),
                		eventObject.getMessage()+"\n");
                System.out.println(eventObject.getMessage());
            }
		}
	}



}