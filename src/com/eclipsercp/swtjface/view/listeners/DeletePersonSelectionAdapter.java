package com.eclipsercp.swtjface.view.listeners;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.eclipsercp.swtjface.controller.PersonController;

/**
 * A listener which is notified when a "Delete" button is pressed.
 * It deletes current person from the table list.
 */
public class DeletePersonSelectionAdapter extends SelectionAdapter {
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		PersonController.getInstance().deletePerson();
		
	}

}
