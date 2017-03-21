package com.eclipsercp.swtjface.view;

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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.eclipsercp.swtjface.controller.PersonController;
import com.eclipsercp.swtjface.model.Person;
import com.eclipsercp.swtjface.services.ImageDescriptorService;
import com.eclipsercp.swtjface.view.actions.EditCopyAction;
import com.eclipsercp.swtjface.view.actions.EditDeleteAction;
import com.eclipsercp.swtjface.view.actions.EditPasteAction;
import com.eclipsercp.swtjface.view.actions.FileOpenAction;
import com.eclipsercp.swtjface.view.actions.FileSaveAction;
import com.eclipsercp.swtjface.view.actions.HelpAboutAction;
import com.eclipsercp.swtjface.view.listeners.AddPersonSelectionAdapter;
import com.eclipsercp.swtjface.view.listeners.CancelAppSelectionAdapter;
import com.eclipsercp.swtjface.view.listeners.DeletePersonSelectionAdapter;
import com.eclipsercp.swtjface.view.listeners.PersonGroupVerifyListener;
import com.eclipsercp.swtjface.view.listeners.PersonTableSelectionListener;
import com.eclipsercp.swtjface.view.listeners.SavePersonSelectionAdapter;

public class HelloJFace extends ApplicationWindow {

	// UI fields
	private TableViewer viewer;
	private Text personName;
	private Text personGroup;
	private Button swtDoneBtn;
	private Button newBtn;
	private Button saveBtn;
	private Button deleteBtn;
	private Button cancelBtn;

	// actions
	private FileOpenAction actionFileOpen;
	private FileSaveAction actionFileSave;
	private EditCopyAction actionEditCopy;
	private EditPasteAction actionEditPaste;
	private EditDeleteAction actionEditDelete;
	private HelpAboutAction actionHelpAbout;

	// listeners
	private PersonTableSelectionListener personTableSelectionListener = new PersonTableSelectionListener();
	private PersonGroupVerifyListener personGroupVerifyListener = new PersonGroupVerifyListener();
	private AddPersonSelectionAdapter addPersonSelectionAdapter = new AddPersonSelectionAdapter();
	private SavePersonSelectionAdapter savePersonSelectionAdapter = new SavePersonSelectionAdapter();
	private DeletePersonSelectionAdapter deletePersonSelectionAdapter = new DeletePersonSelectionAdapter();
	private CancelAppSelectionAdapter cancelAppSelectionAdapter = new CancelAppSelectionAdapter();

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

		// list of persons table
		createTableViewer(sf);

		// "Person info" group
		createGroupOfFieldsAndButtons(sf);

		parent.pack();
		return parent;
	}

	private void createTableViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);

		// make lines and header visible
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.addSelectionChangedListener(personTableSelectionListener);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(PersonController.getInstance().getPersonList());

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		setViewerToActions();

	}

	private void createGroupOfFieldsAndButtons(Composite parent) {
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
		personGroup.addVerifyListener(personGroupVerifyListener);
		// personGroup.addListener(SWT.Verify,
		// new WeakPersonGroupChangeListener(event -> checkDigitalInput(event),
		// personGroup));

		Label labelSwtDone = new Label(personInfoGroup, SWT.NONE);
		labelSwtDone.setText("SWT task done");

		swtDoneBtn = new Button(personInfoGroup, SWT.CHECK | SWT.RIGHT_TO_LEFT);
		swtDoneBtn.setLayoutData(gridDataResult);

		// buttons
		Composite personButtons = new Composite(personInfoGroup, SWT.NONE);
		personButtons.setLayout(new GridLayout(4, true));
		personButtons.setLayoutData(gridDataFill);

		// button 'New'
		newBtn = new Button(personButtons, SWT.PUSH);
		newBtn.setText("New");
		newBtn.addSelectionListener(addPersonSelectionAdapter);

		// button 'Save'
		saveBtn = new Button(personButtons, SWT.PUSH);
		saveBtn.setText("Save");
		saveBtn.addSelectionListener(savePersonSelectionAdapter);

		// button 'Delete'
		deleteBtn = new Button(personButtons, SWT.PUSH);
		deleteBtn.setText("Delete");
		deleteBtn.addSelectionListener(deletePersonSelectionAdapter);

		// button 'Cancel'
		cancelBtn = new Button(personButtons, SWT.PUSH);
		cancelBtn.setText("Cancel");
		cancelBtn.addSelectionListener(cancelAppSelectionAdapter);

		personInfoGroup.pack();

		setListenerFields();

	}

	private void setListenerFields() {
		personTableSelectionListener.setPersonName(personName);
		personTableSelectionListener.setPersonGroup(personGroup);
		personTableSelectionListener.setSwtDoneBtn(swtDoneBtn);

		addPersonSelectionAdapter.setPersonName(personName);
		addPersonSelectionAdapter.setPersonGroup(personGroup);
		addPersonSelectionAdapter.setSwtDoneBtn(swtDoneBtn);

		savePersonSelectionAdapter.setPersonName(personName);
		savePersonSelectionAdapter.setPersonGroup(personGroup);
		savePersonSelectionAdapter.setSwtDoneBtn(swtDoneBtn);

		cancelAppSelectionAdapter.setAppWindow(this);
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

	@Override
	protected MenuManager createMenuManager() {

		createActions();
		return createAndFillMenu();

	}

	private void setViewerToActions() {
		actionFileOpen.setViewer(viewer);
		actionFileSave.setViewer(viewer);
		actionEditCopy.setViewer(viewer);
		personTableSelectionListener.setViewer(viewer);
		PersonController.getInstance().setViewer(viewer);
	}

	private void createActions() {
		actionFileOpen = new FileOpenAction();
		actionFileOpen
				.setImageDescriptor(ImageDescriptorService.getInstance().getImageDescriptor("resources/open.jpg"));

		actionFileSave = new FileSaveAction();
		actionFileSave
				.setImageDescriptor(ImageDescriptorService.getInstance().getImageDescriptor("resources/save.jpg"));

		actionEditCopy = new EditCopyAction();
		actionEditCopy
				.setImageDescriptor(ImageDescriptorService.getInstance().getImageDescriptor("resources/copy.jpg"));
		actionEditCopy.setEnabled(false);

		actionEditPaste = new EditPasteAction();
		actionEditPaste
				.setImageDescriptor(ImageDescriptorService.getInstance().getImageDescriptor("resources/paste.jpg"));
		actionEditPaste.setEnabled(false);

		actionEditDelete = new EditDeleteAction();
		actionEditDelete
				.setImageDescriptor(ImageDescriptorService.getInstance().getImageDescriptor("resources/delete.jpg"));
		actionEditDelete.setEnabled(false);

		actionHelpAbout = new HelpAboutAction();
		actionHelpAbout
				.setImageDescriptor(ImageDescriptorService.getInstance().getImageDescriptor("resources/about.jpg"));
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

		personTableSelectionListener.setToolBarManager(tool_bar_manager);

		return tool_bar_manager;
	}

	private static ImageDescriptor getImageDescriptor(String file) {
		return ImageDescriptor.createFromFile(null, "resources/" + file);
	}

	@Override
	public boolean close() {
		removeListeners();
		return super.close();
	}

	private void removeListeners() {
		viewer.removeSelectionChangedListener(personTableSelectionListener);
		personGroup.removeVerifyListener(personGroupVerifyListener);
		newBtn.removeSelectionListener(addPersonSelectionAdapter);
		saveBtn.removeSelectionListener(savePersonSelectionAdapter);
		deleteBtn.removeSelectionListener(deletePersonSelectionAdapter);
		cancelBtn.removeSelectionListener(cancelAppSelectionAdapter);
	}

	public static void main(String[] args) {

		HelloJFace awin = new HelloJFace();
		awin.setBlockOnOpen(true);
		awin.open();

		Display.getCurrent().dispose();
	}

}
