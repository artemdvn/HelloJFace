package com.eclipsercp.swtjface.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.eclipsercp.swtjface.controller.PersonController;
import com.eclipsercp.swtjface.model.Person;

/**
 * An action which is notified when a "Edit - Copy" menu item is selected.
 */
public class EditCopyAction extends Action {

	private TableViewer viewer;

	/**
	 * Constructs a new action for "Edit - Copy" menu item.
	 */
	public EditCopyAction() {
		super("&Copy@Ctrl+C", AS_PUSH_BUTTON);
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
		ISelection sel = viewer.getSelection();
		Person selectedPerson = (Person) ((IStructuredSelection) sel).getFirstElement();
		PersonController.getInstance().setCopiedPerson(selectedPerson);
		
	}	

}
