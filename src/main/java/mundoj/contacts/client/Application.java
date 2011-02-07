package mundoj.contacts.client;

import mundoj.contacts.ui.client.edit.EditContactView;
import mundoj.contacts.ui.client.search.SearchContactsActivity;
import mundoj.contacts.ui.client.search.SearchContactsPlace;
import mundoj.contacts.ui.client.search.SearchContactsView;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Application implements EntryPoint {
	private EventBus eventBus = new SimpleEventBus();
	private PlaceController placeController = new PlaceController(eventBus);

	@WithTokenizers(SearchContactsPlace.Tokenizer.class)
	interface ContactsHistoryMapper extends PlaceHistoryMapper { }
	private ContactsHistoryMapper historyMapper = GWT.create(ContactsHistoryMapper.class);

	private PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
	{
		historyHandler.register(placeController, eventBus, new SearchContactsPlace(""));
	}

	private EditContactView editor = new EditContactView();
	private SearchContactsView searchView = new SearchContactsView(placeController, editor);

	private ActivityMapper activityMapper = new ActivityMapper() {
		@Override
		public Activity getActivity(Place place) {
			if (place instanceof SearchContactsPlace)
				return new SearchContactsActivity(searchView, (SearchContactsPlace) place);
			return null;
		}
	};
	private ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
	
	@Override
	public void onModuleLoad() {
		SimplePanel mainPanel = new SimplePanel();
		RootLayoutPanel.get().add(mainPanel);
		activityManager.setDisplay(mainPanel);
		historyHandler.handleCurrentHistory();
	}
}