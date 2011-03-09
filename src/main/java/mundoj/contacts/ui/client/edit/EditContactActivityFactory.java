package mundoj.contacts.ui.client.edit;

import mundoj.commons.client.AbstractActivityFactory;
import mundoj.commons.client.ServiceCallback;
import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.edit.EditContactService;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

@SuppressWarnings("all")
public class EditContactActivityFactory extends AbstractActivityFactory<EditContactPlace> {
	private final EditContactView editor;
	private final EditContactService service;

	@Inject
	protected EditContactActivityFactory(EventBus eventBus, 
			EditContactService service, EditContactView editor) {
		super(eventBus);
		this.service = service;
		this.editor = editor;
		registerSaveContactEvent(eventBus);
	}

	private void registerSaveContactEvent(EventBus eventBus) {
		eventBus.addHandler(SaveContactEvent.TYPE, new SaveContactEvent.Handler() {
			@Override
			public void onEvent(SaveContactEvent e) {
				service.save(e.contact, new ServiceCallback<IContact>() {
					@Override
					public void execute(IContact c) {
						editor.onContactSaved(c);
					}
				});
			}
		});
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
			editor.startLoading("Carregando dados do usuário");
			editContact(place.contactId);
		}

		private void editContact(int id) {
			service.edit(id, new ServiceCallback<IContact>() {
				@Override
				public void execute(IContact contact) {
					editor.bind(contact);
				}
			});
		}
		
		public String mayStop() {
			return editor.hasChanges() ? "Descartar Alterações?" : null;
		}
	}
}