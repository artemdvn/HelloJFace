package com.eclipsercp.swtjface.view.listeners;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.eclipsercp.swtjface.controller.PersonController;

/**
 * A listener which is notified when a "Save" button is pressed.
 * It rewrites current person in the table list.
 */
public class SavePersonSelectionAdapter extends SelectionAdapter {
	
	private Text personName;
	private Text personGroup;
	private Button swtDoneBtn;
	
	public Text getPersonName() {
		return personName;
	}

	public void setPersonName(Text personName) {
		this.personName = personName;
	}

	public Text getPersonGroup() {
		return personGroup;
	}

	public void setPersonGroup(Text personGroup) {
		this.personGroup = personGroup;
	}

	public Button getSwtDoneBtn() {
		return swtDoneBtn;
	}

	public void setSwtDoneBtn(Button swtDoneBtn) {
		this.swtDoneBtn = swtDoneBtn;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		PersonController.getInstance().savePerson(personName.getText(),
				personGroup.getText(), swtDoneBtn.getSelection());
		
	}

}
