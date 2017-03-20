package com.eclipsercp.swtjface;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.eclipsercp.swtjface.actions.ActionEditCopy;
import com.eclipsercp.swtjface.actions.ActionEditDelete;
import com.eclipsercp.swtjface.actions.ActionEditPaste;
import com.eclipsercp.swtjface.actions.ActionFileOpen;
import com.eclipsercp.swtjface.actions.ActionFileSave;
import com.eclipsercp.swtjface.actions.ActionHelpAbout;

public class HelloJFace extends ApplicationWindow {

	private List<Person> personList = new ArrayList<Person>();
	private TableViewer viewer;
	private Text personName;
	private Text personGroup;
	private Button swtDoneBtn;

	private ActionFileOpen actionFileOpen;
	private ActionFileSave actionFileSave;
	private ActionEditCopy actionEditCopy;
	private ActionEditPaste actionEditPaste;
	private ActionEditDelete actionEditDelete;
	private ActionHelpAbout actionHelpAbout;

	// images for "SWT done" column assumes that we have these two icons in the
	private final ImageDescriptor CHECKED = getImageDescriptor("checked.gif");
	private final ImageDescriptor UNCHECKED = getImageDescriptor("unchecked.gif");

	/**
	 * Constructs a new instance of this class.
	 */
	public HelloJFace() {
		super(null);
		addMenuBar();
		addToolBar(SWT.FLAT | SWT.WRAP);
	}

	@Override
	protected Control createContents(Composite parent) {

		getShell().setText("JFace homework log");
		parent.setSize(500, 500);
		parent.setLayout(new GridLayout(1, true));

		SashForm sf = new SashForm(parent, SWT.HORIZONTAL);

		// list of persons
		createListOfPersons(sf);

		// person info
		createPersonInfo(sf);

		parent.pack();
		return parent;
	}

	/**
	 * Constructs a person list table.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the creating elements
	 */
	private void createListOfPersons(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);

		// make lines and header visible
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				TableItem currentItem = viewer.getTable().getItem(viewer.getTable().getSelectionIndex());
				personName.setText(currentItem.getText(0));
				personGroup.setText(currentItem.getText(1));
				swtDoneBtn.setSelection(Boolean.parseBoolean(currentItem.getText(2)));
			}
		});

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(personList);

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		actionFileOpen.setViewer(viewer);
		actionFileSave.setViewer(viewer);

	}

	/**
	 * Constructs a "Person info" group with fields and buttons.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the creating elements
	 */
	private void createPersonInfo(Composite parent) {
		GridData gridDataResult = new GridData();
		gridDataResult.horizontalAlignment = GridData.FILL;

		GridData gridDataFill = new GridData();
		gridDataFill.horizontalAlignment = GridData.FILL;
		gridDataFill.horizontalSpan = 2;

		Group personInfoGroup = new Group(parent, SWT.SHADOW_ETCHED_OUT);
		personInfoGroup.setText("Person info");
		personInfoGroup.setLayout(new GridLayout(2, true));
		personInfoGroup.setLayoutData(gridDataResult);

		Label labelPersonName = new Label(personInfoGroup, SWT.NONE);
		labelPersonName.setText("Name:");

		personName = new Text(personInfoGroup, SWT.BORDER | SWT.RIGHT);

		Label labelGroupName = new Label(personInfoGroup, SWT.NONE);
		labelGroupName.setText("Group #:");

		personGroup = new Text(personInfoGroup, SWT.BORDER | SWT.RIGHT);
		personGroup.addListener(SWT.Verify, event -> checkDigitalInput(event));

		Label labelSwtDone = new Label(personInfoGroup, SWT.NONE);
		labelSwtDone.setText("SWT task done");

		swtDoneBtn = new Button(personInfoGroup, SWT.CHECK | SWT.RIGHT_TO_LEFT);
		swtDoneBtn.setLayoutData(gridDataResult);

		// buttons
		Composite personButtons = new Composite(personInfoGroup, SWT.NONE);
		personButtons.setLayout(new GridLayout(4, true));
		personButtons.setLayoutData(gridDataFill);

		// button 'New'
		Button newBtn = new Button(personButtons, SWT.PUSH);
		newBtn.setText("New");
		newBtn.addListener(SWT.Selection,
				event -> addPerson(personName.getText(), personGroup.getText(), swtDoneBtn.getSelection()));

		// button 'Save'
		Button saveBtn = new Button(personButtons, SWT.PUSH);
		saveBtn.setText("Save");
		saveBtn.addListener(SWT.Selection,
				event -> savePerson(personName.getText(), personGroup.getText(), swtDoneBtn.getSelection()));

		// button 'Delete'
		Button deleteBtn = new Button(personButtons, SWT.PUSH);
		deleteBtn.setText("Delete");
		deleteBtn.addListener(SWT.Selection, event -> deletePerson());

		// button 'Cancel'
		Button cancelBtn = new Button(personButtons, SWT.PUSH);
		cancelBtn.setText("Cancel");
		cancelBtn.addListener(SWT.Selection, event -> cancelApp());

		personInfoGroup.pack();

	}

	private void addPerson(String nameString, String groupString, Boolean swtDone) {
		if (nameString.length() == 0) {
			MessageBox dia = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK);
			dia.setText("Empty name");
			dia.setMessage("The name of new person cannot be empty!");
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

		viewer.refresh();

	}

	private void savePerson(String nameString, String groupString, Boolean swtDone) {

		personList = getPersonListFromViewer();

		if (viewer.getTable().getSelectionIndex() == -1) {
			// no item is selected
			return;
		}

		MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Do you really want to save changes of the current item?");
		messageBox.setText("Save item");
		int response = messageBox.open();
		if (response == SWT.NO) {
			return;
		}

		if (nameString.length() == 0) {
			MessageBox dia = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK);
			dia.setText("Empty name");
			dia.setMessage("The name of the person cannot be empty!");
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

		viewer.refresh();

	}

	private void deletePerson() {

		personList = getPersonListFromViewer();

		if (viewer.getTable().getSelectionIndex() == -1) {
			// no item is selected
			return;
		}

		MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(),
				SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Do you really want to delete current item?");
		messageBox.setText("Delete item");
		int response = messageBox.open();
		if (response == SWT.YES) {
			personList.remove(viewer.getTable().getSelectionIndex());
			viewer.refresh();
		}

	}

	private void cancelApp() {

		MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Do you really want to exit?");
		messageBox.setText("Exiting Application");
		int response = messageBox.open();
		if (response == SWT.YES) {
			System.exit(0);
		}

	}

	@SuppressWarnings("unchecked")
	private List<Person> getPersonListFromViewer() {
		return (List<Person>) viewer.getInput();
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
			private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());

			@Override
			public String getText(Object element) {
				Person p = (Person) element;
				return p.isSwtDone().toString();
			}

			@Override
			public Image getImage(Object element) {
				Person p = (Person) element;
				if (p.isSwtDone()) {
					return resourceManager.createImage(CHECKED);
				}
				return resourceManager.createImage(UNCHECKED);
			}

			@Override
			public void dispose() {
				super.dispose();
				resourceManager.dispose();
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
	@Override
	public boolean close(){
		viewer.removeSelectionChangedListener(null);
		return super.close();
	}

	@Override
	protected MenuManager createMenuManager() {

		createActions();
		return createAndFillMenu();

	}

	private void createActions() {
		actionFileOpen = new ActionFileOpen();
		actionFileSave = new ActionFileSave();
		actionEditCopy = new ActionEditCopy();
		actionEditPaste = new ActionEditPaste();
		actionEditDelete = new ActionEditDelete();
		actionHelpAbout = new ActionHelpAbout();
	}

	private MenuManager createAndFillMenu() {
		MenuManager barMenuManager = new MenuManager();

		MenuManager fileMenuManager = new MenuManager("&File");
		MenuManager editMenuManager = new MenuManager("&Edit");
		MenuManager helpMenuManager = new MenuManager("&Help");

		barMenuManager.add(fileMenuManager);
		barMenuManager.add(editMenuManager);
		barMenuManager.add(helpMenuManager);

		fileMenuManager.add(actionFileOpen);
		fileMenuManager.add(actionFileSave);

		editMenuManager.add(actionEditCopy);
		editMenuManager.add(actionEditPaste);
		editMenuManager.add(actionEditDelete);

		helpMenuManager.add(actionHelpAbout);
		return barMenuManager;
	}

	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager tool_bar_manager = new ToolBarManager(style);
		tool_bar_manager.add(actionFileOpen);
		tool_bar_manager.add(actionFileSave);
		tool_bar_manager.add(actionEditCopy);
		tool_bar_manager.add(actionEditPaste);
		tool_bar_manager.add(actionEditDelete);
		return tool_bar_manager;
	}

	private static ImageDescriptor getImageDescriptor(String file) {
		return ImageDescriptor.createFromFile(null, "resources/" + file);
	}

	public static void main(String[] args) {

		HelloJFace awin = new HelloJFace();
		awin.setBlockOnOpen(true);
		awin.open();

		Display.getCurrent().dispose();
	}

}
