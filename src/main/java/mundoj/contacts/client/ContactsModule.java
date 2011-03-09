package mundoj.contacts.client;

import mundoj.contacts.domain.edit.EditContactService;
import mundoj.contacts.domain.edit.EditContactServiceJsonImpl;
import mundoj.contacts.domain.search.SearchContactsService;
import mundoj.contacts.domain.search.SearchContactsServiceJsonImpl;
import mundoj.contacts.ui.client.edit.EditContactActivityFactory;
import mundoj.contacts.ui.client.edit.EditContactPlace;
import mundoj.contacts.ui.client.search.SearchContactsActivityFactory;
import mundoj.contacts.ui.client.search.SearchContactsPlace;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

public class ContactsModule extends AbstractGinModule {
	public static final String HISTORY_HANDLER = "contactsHistoryHandler";

	@WithTokenizers({
		SearchContactsPlace.Tokenizer.class, EditContactPlace.Tokenizer.class
	})
	interface ContactsHistoryMapper extends PlaceHistoryMapper { }
	
	@Provides 
	@Singleton 
	@Named(HISTORY_HANDLER)
	public PlaceHistoryHandler contactsHistoryHandler(EventBus eventBus, 
			PlaceController placeController, ContactsHistoryMapper contactsMapper,
			SearchContactsActivityFactory searchContactsFactory,
			EditContactActivityFactory editContactFactory) {
		PlaceHistoryHandler handler = new PlaceHistoryHandler(contactsMapper);
		handler.register(placeController, eventBus, new SearchContactsPlace(""));
		return handler;
	}

	@Override
	protected void configure() {
		// bind(SearchContactsService.class).to(SearchContactsServiceTimerImpl.class);
		bind(SearchContactsService.class).to(SearchContactsServiceJsonImpl.class);
		
		// bind(EditContactService.class).to(EditContactServiceTimerImpl.class);
		bind(EditContactService.class).to(EditContactServiceJsonImpl.class);
	}
}