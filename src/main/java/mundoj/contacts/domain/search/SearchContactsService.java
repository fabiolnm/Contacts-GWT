package mundoj.contacts.domain.search;

import java.util.List;

import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.ServiceCallback;

public interface SearchContactsService {
	void searchContacts(String keyword, ServiceCallback<List<? extends IContact>> serviceCallback);

	void cancelCurrentCallback();
}