package com.eclipsercp.swtjface;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class HelloJFace extends ApplicationWindow {

	public HelloJFace() {
		super(null);
	}

	@Override
	protected Control createContents(Composite parent) {

		Text helloText = new Text(parent, SWT.CENTER);
		helloText.setText("Hello SWT and JFace!");
		parent.pack();
		return parent;
	}

	public static void main(String[] args) {

		HelloJFace awin = new HelloJFace();
		awin.setBlockOnOpen(true);
		awin.open();
		Display.getCurrent().dispose();
	}

}
