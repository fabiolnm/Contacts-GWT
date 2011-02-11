package mundoj.contacts.ui.client.edit;

import mundoj.contacts.domain.Contact;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EditContactActivity extends AbstractActivity {
	private final EditContactView editor;
	private final EditContactPlace place;

	public EditContactActivity(EditContactView editor, EditContactPlace place) {
		this.editor = editor;
		this.place = place;
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(editor);
		editContact();
	}

	private void editContact() {
		editor.startLoading("Carregando dados do usuário");
		// emula carregamento dos dados de um usuário, para edição
		new Timer() {
			public void run() {	
				editor.bind(Contact.get(place.contactId));	
			}
		}.schedule(1000);
	}

	public String mayStop() {
		return editor.hasChanges() ? "Descartar Alterações?" : null;
	}
}