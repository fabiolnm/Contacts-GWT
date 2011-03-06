package mundoj.contacts.domain.edit;

import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.ServiceCallback;

public interface EditContactService<C extends IContact> {
	void edit(int id, ServiceCallback<C> serviceCallback);

	void save(C contact, ServiceCallback<C> serviceCallback);

	void cancelCurrentCallback();
}