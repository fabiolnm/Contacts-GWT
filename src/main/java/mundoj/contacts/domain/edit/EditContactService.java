package mundoj.contacts.domain.edit;

import mundoj.commons.client.ServiceCallback;
import mundoj.contacts.domain.IContact;

public interface EditContactService<C extends IContact> {
	void edit(int id, ServiceCallback<C> serviceCallback);

	void save(C contact, ServiceCallback<C> serviceCallback);

	void cancelCurrentCallback();
}