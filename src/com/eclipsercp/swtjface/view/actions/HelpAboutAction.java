package com.eclipsercp.swtjface.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.eclipsercp.swtjface.services.MessageBoxService;

/**
 * An action which is notified when a "Help - About" menu item is selected.
 */
public class HelpAboutAction extends Action {

	private TableViewer viewer;

	/**
	 * Constructs a new action for "Help - About" menu item.
	 */
	public HelpAboutAction() {
		super("&About@F1", AS_PUSH_BUTTON);
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
		MessageBox dia = MessageBoxService.getInstance().getMessageBox(SWT.ICON_INFORMATION | SWT.OK, "About",
				"JFace Demo project, 2017");
		dia.open();
		return;		
	}	

}
