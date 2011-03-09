package mundoj.contacts.domain.edit;

import mundoj.commons.client.JsonRequest;
import mundoj.commons.client.ServiceCallback;
import mundoj.contacts.domain.ContactJso;

import com.google.gwt.user.client.Window;

public class EditContactServiceJsonImpl implements EditContactService<ContactJso> {
	private JsonRequest currentRequest;
	
	@Override
	public void edit(int id, final ServiceCallback<ContactJso> serviceCallback) {
		buildRequest(serviceCallback).get("/json/contact/edit/" + id);
	}

	@Override
	public void save(ContactJso contact, ServiceCallback<ContactJso> serviceCallback) {
		buildRequest(serviceCallback).post("/json/contact/save", contact);
	}

	private JsonRequest buildRequest(final ServiceCallback<ContactJso> serviceCallback) {
		cancelCurrentCallback();
		currentRequest = new JsonRequest() {
			@Override
			public void callback(Throwable e) {
				Window.alert(e.getMessage());
			}
			
			@Override
			public void callback(String json) {
				ContactJso contact = eval(json);
				serviceCallback.execute(contact);
			}
		};
		return currentRequest;
	}

	@Override
	public void cancelCurrentCallback() {
		if (currentRequest!=null)
			currentRequest.cancel();
	}
}