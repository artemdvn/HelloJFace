package com.eclipsercp.swtjface.view.listeners;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MessageBox;

import com.eclipsercp.swtjface.services.MessageBoxService;

/**
 * A listener which is notified when a "Cancel" button is pressed.
 * It closes application window.
 */
public class CancelAppSelectionAdapter extends SelectionAdapter {

	private ApplicationWindow appWindow;

	public ApplicationWindow getAppWindow() {
		return appWindow;
	}

	public void setAppWindow(ApplicationWindow appWindow) {
		this.appWindow = appWindow;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		cancelApp();

	}

	private void cancelApp() {
		MessageBox messageBox = MessageBoxService.getInstance().getMessageBox(SWT.ICON_QUESTION | SWT.YES | SWT.NO,
				"Exiting Application", "Do you really want to exit?");
		int response = messageBox.open();
		if (response == SWT.YES) {
			appWindow.close();
		}

	}

}
