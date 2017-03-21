package com.eclipsercp.swtjface.view;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.eclipsercp.swtjface.controller.PersonController;
import com.eclipsercp.swtjface.model.Person;

public class PersonTableSelectionListener implements ISelectionChangedListener {

	private TableViewer viewer;
	private Text personName;
	private Text personGroup;
	private Button swtDoneBtn;
	private ToolBarManager tbm;

	public TableViewer getViewer() {
		return viewer;
	}

	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}

	public Text getPersonName() {
		return personName;
	}

	public void setPersonName(Text personName) {
		this.personName = personName;
	}

	public Text getPersonGroup() {
		return personGroup;
	}

	public void setPersonGroup(Text personGroup) {
		this.personGroup = personGroup;
	}

	public Button getSwtDoneBtn() {
		return swtDoneBtn;
	}

	public void setSwtDoneBtn(Button swtDoneBtn) {
		this.swtDoneBtn = swtDoneBtn;
	}

	public ToolBarManager getToolBarManager() {
		return tbm;
	}

	public void setToolBarManager(ToolBarManager tbm) {
		this.tbm = tbm;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection sel = event.getSelection();
		if (sel.isEmpty() || !(sel instanceof IStructuredSelection)) {
			// disable some actions
			disableActions();
			return;
		}
		Person selectedPerson = (Person) ((IStructuredSelection) sel).getFirstElement();
		personName.setText(selectedPerson.getName());
		personGroup.setText(selectedPerson.getGroup().toString());
		swtDoneBtn.setSelection(selectedPerson.isSwtDone());

		// enable all actions
		enableActions();

	}

	private void disableActions() {
		for (IContributionItem tbItem : tbm.getItems()) {
			if ((tbItem instanceof ActionContributionItem)) {
				IAction act = ((ActionContributionItem) tbItem).getAction();
				if (act.getText().startsWith("&Copy") || act.getText().startsWith("&Paste")
						|| act.getText().startsWith("&Delete")) {
					act.setEnabled(false);
				}
			}
		}
	}

	private void enableActions() {
		for (IContributionItem tbItem : tbm.getItems()) {
			if ((tbItem instanceof ActionContributionItem)) {
				IAction act = ((ActionContributionItem) tbItem).getAction();
				if (act.getText().startsWith("&Paste")){
					if (PersonController.getInstance().getCopiedPerson() != null){
						act.setEnabled(true);
					}
				} else{
					act.setEnabled(true);
				}
			}
		}
	}

}
