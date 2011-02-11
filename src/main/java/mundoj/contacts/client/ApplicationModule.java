package mundoj.contacts.client;

import mundoj.contacts.ui.client.edit.EditContactActivityFactory;
import mundoj.contacts.ui.client.edit.EditContactPlace;
import mundoj.contacts.ui.client.search.SearchContactsActivityFactory;
import mundoj.contacts.ui.client.search.SearchContactsPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

public class ApplicationModule extends AbstractGinModule {
	public static final String CONTACTS_HISTORY_HANDLER = "contactsHistoryHandler";
	
	@Override
	protected void configure() {  }

	@Provides
	@Singleton
	public EventBus eventBus() {
		return new SimpleEventBus();
	}
	
	@Provides 
	@Singleton
	public PlaceController placeController(EventBus eventBus) {
		return new PlaceController(eventBus);
	}
	
	@Provides 
	@Singleton
	public ActivityManager activityManager(final EventBus eventBus,
			SearchContactsActivityFactory searchContactsFactory,
			EditContactActivityFactory editContactFactory) {
		return new ActivityManager(new ActivityMapper() {
			@Override
			public final Activity getActivity(Place place) {
				ActivityRequestEvent event = new ActivityRequestEvent(place);
				eventBus.fireEvent(event);
				return event.getActivity();
			}
		}, eventBus);
	}

	@WithTokenizers({
		SearchContactsPlace.Tokenizer.class, EditContactPlace.Tokenizer.class
	})
	interface ContactsHistoryMapper extends PlaceHistoryMapper { }
	
	@Provides 
	@Singleton 
	@Named(CONTACTS_HISTORY_HANDLER)
	public PlaceHistoryHandler contactsHistoryHandler(EventBus eventBus, 
			PlaceController placeController, ContactsHistoryMapper contactsMapper) {
		PlaceHistoryHandler handler = new PlaceHistoryHandler(contactsMapper);
		handler.register(placeController, eventBus, new SearchContactsPlace(""));
		return handler;
	}
}