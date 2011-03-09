package mundoj.contacts.domain.search;

import java.util.List;

import mundoj.commons.client.ServiceCallback;
import mundoj.contacts.domain.IContact;

public interface SearchContactsService {
	void searchContacts(String keyword, ServiceCallback<List<? extends IContact>> serviceCallback);

	void cancelCurrentCallback();
}