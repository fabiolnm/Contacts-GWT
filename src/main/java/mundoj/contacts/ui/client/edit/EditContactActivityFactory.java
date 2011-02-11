package mundoj.contacts.ui.client.edit;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import mundoj.contacts.client.AbstractActivityFactory;
import mundoj.contacts.domain.Contact;

public class EditContactActivityFactory extends AbstractActivityFactory<EditContactPlace> {
	private final EditContactView editor;

	@Inject
	protected EditContactActivityFactory(EventBus eventBus, EditContactView editor) {
		super(eventBus);
		this.editor = editor;
	}

	@Override
	protected Activity create(EventBus eventBus, EditContactPlace place) {
		return new EditContactActivity(place);
	}
	
	public class EditContactActivity extends AbstractActivity {
		private final EditContactPlace place;

		public EditContactActivity(EditContactPlace place) {
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
}