package com.eclipsercp.swtjface.view.listeners;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

/**
 * A listener which is notified when a "Group" text field changes.
 * It verifies if the input text is a number.
 */
public class PersonGroupVerifyListener implements VerifyListener {

	@Override
	public void verifyText(VerifyEvent e) {
		checkDigitalInput(e);
	}

	private void checkDigitalInput(VerifyEvent e) {
		String string = e.text;
		char[] chars = new char[string.length()];
		string.getChars(0, chars.length, chars, 0);
		for (int i = 0; i < chars.length; i++) {
			if (!('0' <= chars[i] && chars[i] <= '9')) {
				e.doit = false;
			}
		}
	}

}
