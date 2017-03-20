package com.eclipsercp.swtjface.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.ImageData;

import com.eclipsercp.swtjface.Person;
import com.eclipsercp.swtjface.services.PersonService;

public class ActionEditCopy extends Action {

	private TableViewer viewer;

	public ActionEditCopy() {
		super("&Copy@Ctrl+C", AS_PUSH_BUTTON);
		ImageDescriptor id = ImageDescriptor.createFromFile(null, "resources/copy.jpg");
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
		ISelection sel = viewer.getSelection();
		Person selectedPerson = (Person) ((IStructuredSelection) sel).getFirstElement();
		PersonService.getInstance().setCopiedPerson(selectedPerson);
		
	}	

}
