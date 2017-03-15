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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

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

		//Text helloText = new Text(parent, SWT.CENTER);
		//helloText.setText("Hello SWT and JFace!");
		
		getShell().setText("JFace homework log");
		parent.setSize(500, 500);
		parent.setLayout(new GridLayout(3, true));

		createViewer(parent);
		
		Button swtDoneBtn = new Button(parent, SWT.CHECK);
		swtDoneBtn.setText("SWT task done");
		swtDoneBtn.pack();
		
		RadioGroupFieldEditor rgfe = new RadioGroupFieldEditor(
				"UserChoice", "Choose an option:", 3,
				new String[][] {{"Choice1", "ch1"},
				{"Choice2", "ch2"},
				{"Choice3", "ch3"}},
				parent, true);

		parent.pack();
		return parent;
	}

	private void createViewer(Composite parent) {
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
