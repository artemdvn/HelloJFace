package com.eclipsercp.swtjface.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.MessageBox;

import com.eclipsercp.swtjface.controller.MessageBoxService;

public class ActionHelpAbout extends Action {

	private TableViewer viewer;

	public ActionHelpAbout() {
		super("&About@F1", AS_PUSH_BUTTON);
		ImageDescriptor id = ImageDescriptor.createFromFile(null, "resources/about.jpg");
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
		MessageBox dia = MessageBoxService.getInstance().getMessageBox(SWT.ICON_INFORMATION | SWT.OK, "About",
				"JFace Demo project, 2017");
		dia.open();
		return;		
	}	

}
