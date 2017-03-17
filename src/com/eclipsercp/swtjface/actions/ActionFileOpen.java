package com.eclipsercp.swtjface.actions;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.eclipsercp.swtjface.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ActionFileOpen extends Action {

	private TableViewer viewer;

	public ActionFileOpen() {
		super("&Open@Ctrl+O", AS_PUSH_BUTTON);
		ImageDescriptor id = ImageDescriptor.createFromFile(null, "resources/open.jpg");
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
		loadListFromFile();
	}

	@SuppressWarnings("unchecked")
	private void loadListFromFile() {

		FileDialog dialog = new FileDialog(Display.getDefault().getActiveShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.json" });
		String selectedFile = dialog.open();
		if (selectedFile == null) {
			return;
		}

		Gson gson = new Gson();

		try (FileReader file = new FileReader(selectedFile)) {
			Type listOfTestObject = new TypeToken<List<Person>>() {
			}.getType();
			List<Person> personList = (List<Person>) viewer.getInput();
			personList = gson.fromJson(file, listOfTestObject);
			viewer.setInput(personList);
			viewer.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
