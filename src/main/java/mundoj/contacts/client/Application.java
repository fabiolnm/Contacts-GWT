package mundoj.contacts.client;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.ui.client.search.SearchContactsView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

	@Override
	public void onModuleLoad() {
		SearchContactsView view = new SearchContactsView();
		RootPanel.get().add(view);
		view.update(Contact.getAll());
	}

}
