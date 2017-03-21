package com.eclipsercp.swtjface.view.actions;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.eclipsercp.swtjface.controller.PersonController;
import com.eclipsercp.swtjface.model.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * An action which is notified when a "File - Open" menu item is selected.
 */
public class FileOpenAction extends Action {

	private TableViewer viewer;

	/**
	 * Constructs a new action for "File - Open" menu item.
	 */
	public FileOpenAction() {
		super("&Open@Ctrl+O", AS_PUSH_BUTTON);
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
			PersonController.getInstance().setPersonList(personList);
			viewer.setInput(personList);
			viewer.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
