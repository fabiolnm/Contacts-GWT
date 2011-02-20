package mundoj.contacts.domain.search;

import java.util.List;

import com.google.gwt.user.client.Window;

import mundoj.contacts.client.JsonRequest;
import mundoj.contacts.domain.ContactJso;
import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.ServiceCallback;

public class SearchContactsServiceJsonImpl implements SearchContactsService {
	private JsonRequest currentRequest;
	
	@Override
	public void searchContacts(String keyword, 
	final ServiceCallback<List<? extends IContact>> serviceCallback) {
		cancelCurrentCallback();
		currentRequest = new JsonRequest() {
			@Override
			public void callback(String json) {
				List<ContactJso> contacts = asList(json);
				serviceCallback.execute(contacts);
			}
			@Override
			public void callback(Throwable e) {
				Window.alert(e.getMessage());
			}
		};
		currentRequest.with("keyword", keyword).get("/contact/list");
	}
	
	@Override
	public void cancelCurrentCallback() {
		if (currentRequest!=null)
			currentRequest.cancel();
	}
}