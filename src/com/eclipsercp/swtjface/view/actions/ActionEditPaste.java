package com.eclipsercp.swtjface.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.ImageData;

import com.eclipsercp.swtjface.controller.PersonController;
import com.eclipsercp.swtjface.model.Person;

/**
 * An action which is notified when a "Edit - Paste" menu item is selected.
 */
public class ActionEditPaste extends Action {

	private TableViewer viewer;

	/**
	 * Constructs a new action for "Edit - Paste" menu item.
	 */
	public ActionEditPaste() {
		super("&Paste@Ctrl+V", AS_PUSH_BUTTON);
		ImageDescriptor id = ImageDescriptor.createFromFile(null, "resources/paste.jpg");
		ImageData imgData = id.getImageData();
		imgData = imgData.scaledTo(32, 32);
		setImageDescriptor(ImageDescriptor.createFromImageData(imgData));
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
		Person copiedPerson = PersonController.getInstance().getCopiedPerson();
		if (copiedPerson != null) {
			PersonController.getInstance().addPerson(copiedPerson);
		}
		
	}	

}
