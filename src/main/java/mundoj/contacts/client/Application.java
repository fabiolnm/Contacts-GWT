package mundoj.contacts.client;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.ui.client.edit.EditContactView;
import mundoj.contacts.ui.client.search.SearchContactsView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Application implements EntryPoint {

	@Override
	public void onModuleLoad() {
		EditContactView editor = new EditContactView();
		SearchContactsView searchView = new SearchContactsView(editor);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(searchView);
		vp.add(editor);
		
		RootPanel.get().add(vp);
		searchView.update(Contact.getAll());
	}

}
