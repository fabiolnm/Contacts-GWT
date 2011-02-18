package mundoj.contacts.ui.client.search;

import java.util.List;

import mundoj.contacts.client.AbstractActivityFactory;
import mundoj.contacts.domain.Contact;
import mundoj.contacts.domain.ServiceCallback;
import mundoj.contacts.domain.search.SearchContactsService;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class SearchContactsActivityFactory extends AbstractActivityFactory<SearchContactsPlace> {
	private final SearchContactsView view;
	private final SearchContactsService service;

	@Inject
	protected SearchContactsActivityFactory(EventBus eventBus, SearchContactsService service, SearchContactsView view) {
		super(eventBus);
		this.service = service;
		this.view = view;
	}

	@Override
	protected Activity create(EventBus eventBus, SearchContactsPlace place) {
		return new SearchContactsActivity(place);
	}
	
	public class SearchContactsActivity extends AbstractActivity {
		private final SearchContactsPlace place;

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
			service.searchContacts(place.keyword, new ServiceCallback<List<Contact>>() {
				@Override
				public void execute(List<Contact> contacts) {
					view.update(contacts);
				}
			});
		}

		@Override
		public String mayStop() {
			String msg = "Deseja interromper a pesquisa em andamento?";
			return view.isLoading() ? msg : null;
		}

		@Override
		public void onStop() {
			service.cancelCurrentCallback();
		}
	}
}