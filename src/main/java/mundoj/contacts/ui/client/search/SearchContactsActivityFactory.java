package mundoj.contacts.ui.client.search;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import mundoj.contacts.client.AbstractActivityFactory;
import mundoj.contacts.domain.Contact;

public class SearchContactsActivityFactory extends AbstractActivityFactory<SearchContactsPlace> {
	private final SearchContactsView view;

	@Inject
	protected SearchContactsActivityFactory(EventBus eventBus, SearchContactsView view) {
		super(eventBus);
		this.view = view;
	}

	@Override
	protected Activity create(EventBus eventBus, SearchContactsPlace place) {
		return new SearchContactsActivity(place);
	}
	
	public class SearchContactsActivity extends AbstractActivity {
		private final SearchContactsPlace place;
		private Timer currentTimer;

		public SearchContactsActivity(SearchContactsPlace place) {
			this.place = place;
		}

		@Override
		public void start(final AcceptsOneWidget panel, EventBus eventBus) {
			panel.setWidget(view);
			view.startLoading(place.keyword);
			searchContacts();
		}

		private void searchContacts() {
			// timer = emula delay em uma chamada cliente-servidor
			currentTimer = new Timer() {
				@Override
				public void run() {
					List<Contact> contacts = Contact.filter(place.keyword);
					view.update(contacts);
				}
			};
			currentTimer.schedule(3000);
		}

		@Override
		public String mayStop() {
			return view.isLoading() ? "Deseja interromper a pesquisa em andamento?" : null;
		}

		@Override
		public void onStop() {
			currentTimer.cancel();
		}
	}
}