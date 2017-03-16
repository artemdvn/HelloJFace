package com.eclipsercp.swtjface;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class HelloJFace extends ApplicationWindow {

	private List<Person> personList;
	private TableViewer viewer;

	public HelloJFace() {
		super(null);
		personList = new ArrayList<Person>();
		personList.add(new Person("Alex", 1, true));
	}

	@Override
	protected Control createContents(Composite parent) {

		getShell().setText("JFace homework log");
		parent.setSize(500, 500);
		parent.setLayout(new GridLayout(1, true));
		
		SashForm sf = new SashForm(parent, SWT.HORIZONTAL);

		// list of persons
		createListOfPersons(sf);
		
		//person info
		createPersonInfo(sf);				
		
		
		parent.pack();
		return parent;
	}

	private void createListOfPersons(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);

		// make lines and header visible
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(personList);

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		// gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}
	
	private void createPersonInfo(Composite parent) {
		GridData gridDataResult = new GridData();
		gridDataResult.horizontalAlignment = GridData.FILL;
		
		Group personInfoGroup = new Group(parent, SWT.SHADOW_ETCHED_OUT);
		personInfoGroup.setText("Person info");
		personInfoGroup.setLayout(new GridLayout(2, true));
		personInfoGroup.setLayoutData(gridDataResult);

		Label labelPersonName = new Label(personInfoGroup, SWT.NONE);
		labelPersonName.setText("Name:");

		Text personName = new Text(personInfoGroup, SWT.BORDER | SWT.RIGHT);

		Label labelGroupName = new Label(personInfoGroup, SWT.NONE);
		labelGroupName.setText("Group #:");

		Text personGroup = new Text(personInfoGroup, SWT.BORDER | SWT.RIGHT);
		personGroup.addListener(SWT.Verify, event -> checkDigitalInput(event));

		Label labelSwtDone = new Label(personInfoGroup, SWT.NONE);
		labelSwtDone.setText("SWT task done");

		Button swtDoneBtn = new Button(personInfoGroup, SWT.CHECK | SWT.RIGHT_TO_LEFT);
		swtDoneBtn.setLayoutData(gridDataResult);

		personInfoGroup.pack();

		// button 'New'
		Button newBtn = new Button(personInfoGroup, SWT.PUSH);
		newBtn.setText("New");
		newBtn.addListener(SWT.Selection,
				event -> addPerson(personName.getText(), personGroup.getText(), swtDoneBtn.getSelection()));
	}
	
	private void addPerson(String nameString, String groupString, Boolean swtDone) {
		if (nameString.length() == 0) {
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

		viewer.refresh();

	}

	// create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Name", "Group", "SWT done" };
		int[] bounds = { 100, 100, 100 };

		// first column is for the name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Person p = (Person) element;
				return p.getName();
			}
		});

		// second column is for the group
		col = createTableViewerColumn(titles[1], bounds[1]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Person p = (Person) element;
				return p.getGroup().toString();
			}
		});

		// SWT done
		col = createTableViewerColumn(titles[2], bounds[2]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Person p = (Person) element;
				return p.isSwtDone().toString();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
	
	private void checkDigitalInput(Event e) {

		String string = e.text;
		char[] chars = new char[string.length()];
		string.getChars(0, chars.length, chars, 0);
		for (int i = 0; i < chars.length; i++) {
			if (!('0' <= chars[i] && chars[i] <= '9')) {
				e.doit = false;
			}
		}

	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public static void main(String[] args) {

		HelloJFace awin = new HelloJFace();
		awin.setBlockOnOpen(true);
		// awin.addMenuBar();
		// awin.addStatusLine();
		// awin.setStatus("11111");

		awin.open();
		Display.getCurrent().dispose();
	}

}
