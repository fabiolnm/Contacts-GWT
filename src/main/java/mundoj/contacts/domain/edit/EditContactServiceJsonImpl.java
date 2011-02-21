package mundoj.contacts.domain.edit;

import com.google.gwt.user.client.Window;

import mundoj.contacts.client.JsonRequest;
import mundoj.contacts.domain.ContactJso;
import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.ServiceCallback;

public class EditContactServiceJsonImpl implements EditContactService {
	private JsonRequest currentRequest;
	
	@Override
	public void edit(int id, final ServiceCallback<IContact> serviceCallback) {
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
		currentRequest.get("/json/contact/edit/" + id);
	}

	@Override
	public void cancelCurrentCallback() {
		if (currentRequest!=null)
			currentRequest.cancel();
	}
}