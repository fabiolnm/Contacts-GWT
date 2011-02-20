package mundoj.contacts.domain.search;

import java.util.ArrayList;
import java.util.List;

import mundoj.contacts.domain.ContactJso;
import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.ServiceCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class SearchContactsServiceJsonImpl implements SearchContactsService {
	@Override
	public void searchContacts(String keyword, ServiceCallback<List<IContact>> serviceCallback) {
		String fakeAnswer = "[" +
			"{ 'id': 1, 'name': 'João', 'number': '1234-5678', 'type': 'Residencial' }," +
			"{ 'id': 2, 'name': 'José', 'number': '8765-4321', 'type': 'Celular' }," +
			"{ 'id': 3, 'name': 'Juca', 'number': '1357-2468', 'type': 'Trabalho' }" +
		"]";
		
		ArrayList<IContact> contacts = new ArrayList<IContact>();
		JsArray<ContactJso> jsoContacts = eval(fakeAnswer);
		int n = jsoContacts.length();
		for (int i=0; i<n; i++)
			contacts.add(jsoContacts.get(i)); 
		
		serviceCallback.execute(contacts);
	}

	public static final native<T extends JavaScriptObject> T eval(String json)/*-{
		return eval("(" + json + ")");
	}-*/;
	
	@Override
	public void cancelCurrentCallback() {
	}
}