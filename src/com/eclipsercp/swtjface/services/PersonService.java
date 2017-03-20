package com.eclipsercp.swtjface.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.eclipsercp.swtjface.Person;

public class PersonService {
	
	private static PersonService instance;
	private List<Person> personList = new ArrayList<Person>();
	private TableViewer viewer;
	private Person copiedPerson;

	public static synchronized PersonService getInstance() {
		if (instance == null) {
			instance = new PersonService();
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
	
	public void addPerson(Person newPerson) {
		addPerson(newPerson.getName(), newPerson.getGroup().toString(), newPerson.isSwtDone());
	}
	
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
