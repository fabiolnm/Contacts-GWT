package mundoj.contacts.ui.client.edit;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.ui.client.LoadingSplash;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditContactView extends LazyPanel implements Editor<Contact> {
	interface ContactEditorUiBinder extends UiBinder<Widget, EditContactView> {	}
	private final ContactEditorUiBinder uiBinder = GWT.create(ContactEditorUiBinder.class);

	static interface Driver extends SimpleBeanEditorDriver<Contact, EditContactView> { }
	private final Driver driver = GWT.create(Driver.class);

	@UiField TextBox name, type, number;
	@UiField ValueBoxEditorDecorator<String> nameEditor, typeEditor, numberEditor;
	@UiField Button button;

	@Override
	protected Widget createWidget() {
		Widget w = uiBinder.createAndBindUi(this);;
		driver.initialize(this);
		return w;
	}

	public void bind(Contact c) {
		LoadingSplash.close();
		setVisible(true);
		driver.edit(c);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		String requiredMsg = "Campo obrigatório";

		if (name.asEditor().getValue() == null)
			nameEditor.asEditor().getDelegate().recordError(requiredMsg, null, null);
		if (type.asEditor().getValue() == null)
			typeEditor.asEditor().getDelegate().recordError(requiredMsg, null, null);
		if (number.asEditor().getValue() == null)
			numberEditor.asEditor().getDelegate().recordError(requiredMsg, null, null);

		driver.flush();

		if (!driver.hasErrors()) {
			DialogBox d = new DialogBox(true);
			d.setAnimationEnabled(true);
			d.setAutoHideOnHistoryEventsEnabled(true);
			d.setGlassEnabled(true);
			d.setHTML("Mensagem");
			d.setWidget(new HTML("Operação realizada com sucesso!"));
			d.center();
			d.show();
		}
	}

	public void startLoading(String loadingMessage) {
		setVisible(false);
		LoadingSplash.display(loadingMessage);
	}

	public boolean hasChanges() {
		return false;
	}
}