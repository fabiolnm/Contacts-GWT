package mundoj.contacts.ui.client.search;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import mundoj.contacts.client.AbstractActivityFactory;

public class SearchContactsActivityFactory extends AbstractActivityFactory<SearchContactsPlace> {
	private final SearchContactsView view;

	@Inject
	protected SearchContactsActivityFactory(EventBus eventBus, SearchContactsView view) {
		super(eventBus);
		this.view = view;
	}

	@Override
	protected Activity create(EventBus eventBus, SearchContactsPlace place) {
		return new SearchContactsActivity(view, place);
	}
}