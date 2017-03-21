package com.eclipsercp.swtjface.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.eclipsercp.swtjface.model.Person;
import com.eclipsercp.swtjface.services.MessageBoxService;

/**
 * A controller to perform CRUD operations with the list of persons.
 */
public class PersonController {
	
	private static PersonController instance;
	private List<Person> personList = new ArrayList<Person>();
	private TableViewer viewer;
	private Person copiedPerson;

	/**
	 * Constructs a new instance of this class or return existing instance if it have been already instantiated.
	 */
	public static synchronized PersonController getInstance() {
		if (instance == null) {
			instance = new PersonController();
		}
		return instance;
	}
	
	public TableViewer getViewer() {
		return viewer;
	}

	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}
	
	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	
	public Person getCopiedPerson() {
		return copiedPerson;
	}

	public void setCopiedPerson(Person copiedPerson) {
		this.copiedPerson = copiedPerson;
	}

	/**
	 * Creates new person using its name, group and "SWT Done" parameter and adds it to the list of persons.
	 * 
	 * @param name
	 *            the name of the new person
	 * @param group
	 *            the group of the new person
	 * @param swtDone
	 *            the "SWT Done" parameter of the new person
	 */
	public void addPerson(String nameString, String groupString, Boolean swtDone) {
		if (nameString.length() == 0) {
			MessageBox dia = MessageBoxService.getInstance().getMessageBox(SWT.ICON_INFORMATION | SWT.OK, "Empty name",
					"The name of new person cannot be empty!");
			dia.open();
			return;
		}

		Integer group = 0;
		if (groupString.length() > 0) {
			try {
				group = Integer.parseInt(groupString);
			} catch (NumberFormatException e) {
			}
		}

		Person newPerson = new Person(nameString, group, swtDone);
		personList.add(newPerson);
		
		viewer.setInput(personList);
		viewer.refresh();

	}
	
	/**
	 * Adds existing person to the list of persons.
	 * 
	 * @param newPerson
	 *           existing person reference
	 */
	public void addPerson(Person newPerson) {
		addPerson(newPerson.getName(), newPerson.getGroup().toString(), newPerson.isSwtDone());
	}
	
	/**
	 * Rewrites existing person using new name, group and "SWT Done" parameter.
	 * 
	 * @param name
	 *            the new name of the existing person
	 * @param group
	 *            the new group of the existing person
	 * @param swtDone
	 *            the new "SWT Done" parameter of the existing person
	 */
	public void savePerson(String nameString, String groupString, Boolean swtDone) {
		
		setPersonList(getPersonListFromViewer());

		if (viewer.getTable().getSelectionIndex() == -1) {
			// no item is selected
			return;
		}

		MessageBox messageBox = MessageBoxService.getInstance().getMessageBox(SWT.ICON_QUESTION | SWT.YES | SWT.NO,
				"Save item", "Do you really want to save changes of the current item?");
		int response = messageBox.open();
		if (response == SWT.NO) {
			return;
		}

		if (nameString.length() == 0) {
			MessageBox dia = MessageBoxService.getInstance().getMessageBox(SWT.ICON_INFORMATION | SWT.OK, "Empty name",
					"The name of the person cannot be empty!");
			dia.open();
			return;
		}

		Integer group = 0;
		if (groupString.length() > 0) {
			try {
				group = Integer.parseInt(groupString);
			} catch (NumberFormatException e) {
			}
		}

		Person currentPerson = personList.get(viewer.getTable().getSelectionIndex());
		currentPerson.setName(nameString);
		currentPerson.setGroup(group);
		currentPerson.setSwtDone(swtDone);

		viewer.setInput(personList);
		viewer.refresh();

	}
	
	/**
	 * Deletes selected person from the list of persons.
	 * 
	 */
	public void deletePerson() {

		setPersonList(getPersonListFromViewer());

		if (viewer.getTable().getSelectionIndex() == -1) {
			// no item is selected
			return;
		}

		MessageBox messageBox = MessageBoxService.getInstance().getMessageBox(SWT.ICON_QUESTION | SWT.YES | SWT.NO,
				"Delete item", "Do you really want to delete current item?");
		int response = messageBox.open();
		if (response == SWT.YES) {
			personList.remove(viewer.getTable().getSelectionIndex());
			viewer.setInput(personList);
			viewer.refresh();
		}

	}
	
	@SuppressWarnings("unchecked")
	private List<Person> getPersonListFromViewer() {
		return (List<Person>) viewer.getInput();
	}

}
