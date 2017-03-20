package com.eclipsercp.swtjface.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.ImageData;

import com.eclipsercp.swtjface.services.PersonService;

public class ActionEditDelete extends Action {

	private TableViewer viewer;

	public ActionEditDelete() {
		super("&Delete@Delete", AS_PUSH_BUTTON);
		ImageDescriptor id = ImageDescriptor.createFromFile(null, "resources/delete.jpg");
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
		PersonService.getInstance().deletePerson();
	}	

}
