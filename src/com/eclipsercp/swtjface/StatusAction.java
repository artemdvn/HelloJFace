package com.eclipsercp.swtjface;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;

public class StatusAction extends Action {
	StatusLineManager statman;
	short triggercount = 0;

	public StatusAction(StatusLineManager sm) {
		super("&Trigger@Ctrl+T", AS_PUSH_BUTTON);
		statman = sm;
		setToolTipText("Trigger the Action");
		//setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(), "task_rcp.png"));
	}

	@Override
	public void run() {
		triggercount++;
		statman.setMessage("The status action has fired. Count: " + triggercount);
	}
}
