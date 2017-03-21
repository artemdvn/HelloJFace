package com.eclipsercp.swtjface.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;

import com.eclipsercp.swtjface.controller.PersonController;

/**
 * An action which is notified when a "Edit - Delete" menu item is selected.
 */
public class EditDeleteAction extends Action {

	private TableViewer viewer;

	/**
	 * Constructs a new action for "Edit - Delete" menu item.
	 */
	public EditDeleteAction() {
		super("&Delete@Delete", AS_PUSH_BUTTON);
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
		PersonController.getInstance().deletePerson();
	}	

}
