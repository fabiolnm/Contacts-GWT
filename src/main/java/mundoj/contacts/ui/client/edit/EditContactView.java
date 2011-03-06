package mundoj.contacts.ui.client.edit;

import mundoj.contacts.domain.IContact;
import mundoj.contacts.ui.client.LoadingSplash;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class EditContactView extends LazyPanel implements Editor<IContact> {
	interface ContactEditorUiBinder extends UiBinder<Widget, EditContactView> {	}
	private final ContactEditorUiBinder uiBinder = GWT.create(ContactEditorUiBinder.class);

	static interface Driver extends SimpleBeanEditorDriver<IContact, EditContactView> { }
	private final Driver driver = GWT.create(Driver.class);

	@UiField TextBox name, type, number;
	@UiField ValueBoxEditorDecorator<String> nameEditor, typeEditor, numberEditor;
	@UiField Button button;

	private final EventBus eventBus;

	@Inject
	public EditContactView(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	protected Widget createWidget() {
		Widget w = uiBinder.createAndBindUi(this);;
		driver.initialize(this);
		return w;
	}

	public void bind(IContact c) {
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

		IContact contact = driver.flush();
		GWT.log(contact.getNumber());
		if (!driver.hasErrors())
			eventBus.fireEvent(new SaveContactEvent(contact));
	}
	
	public void onContactSaved(IContact c) {
		bind(c);
		
		DialogBox d = new DialogBox(true);
		d.setAnimationEnabled(true);
		d.setAutoHideOnHistoryEventsEnabled(true);
		d.setGlassEnabled(true);
		d.setHTML("Mensagem");
		d.setWidget(new HTML("Operação realizada com sucesso!"));
		d.center();
		d.show();
	}

	public void startLoading(String loadingMessage) {
		setVisible(false);
		LoadingSplash.display(loadingMessage);
	}

	public boolean hasChanges() {
		return false;
	}
}