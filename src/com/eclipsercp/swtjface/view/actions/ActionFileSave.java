package com.eclipsercp.swtjface.view.actions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.eclipsercp.swtjface.model.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * An action which is notified when a "File - Save" menu item is selected.
 */
public class ActionFileSave extends Action {

	private TableViewer viewer;

	/**
	 * Constructs a new action for "File - Save" menu item.
	 */
	public ActionFileSave() {
		super("&Save@Ctrl+S", AS_PUSH_BUTTON);
		ImageDescriptor id = ImageDescriptor.createFromFile(null, "resources/save.jpg");
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
		saveListToFile();
	}

	@SuppressWarnings("unchecked")
	private void saveListToFile() {
		
		FileDialog dialog = new FileDialog(Display.getDefault().getActiveShell(), SWT.SAVE);
		dialog.setFilterExtensions(new String[] {"*.json"});
		String fullName = dialog.open();
		
		if (fullName != null){
			
			try (FileWriter file = new FileWriter(fullName)) {
				Gson gson = new GsonBuilder().create();
				List<Person> personList = (List<Person>) viewer.getInput();
			    gson.toJson(personList, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
