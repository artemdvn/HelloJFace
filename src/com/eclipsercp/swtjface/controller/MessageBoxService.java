package com.eclipsercp.swtjface.controller;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class MessageBoxService {
	
	private static MessageBoxService instance;
	
	public static synchronized MessageBoxService getInstance() {
		if (instance == null) {
			instance = new MessageBoxService();
		}
		return instance;
	}
	
	public MessageBox getMessageBox(int style, String text, String message){
		MessageBox dia = new MessageBox(Display.getDefault().getActiveShell(), style);
		dia.setText(text);
		dia.setMessage(message);
		return dia;
	}

}
