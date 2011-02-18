package mundoj.contacts.domain.search;

import java.util.List;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.domain.ServiceCallback;

public interface SearchContactsService {
	void searchContacts(String keyword, ServiceCallback<List<Contact>> serviceCallback);

	void cancelCurrentCallback();
}