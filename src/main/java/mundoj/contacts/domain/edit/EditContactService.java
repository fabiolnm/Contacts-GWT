package mundoj.contacts.domain.edit;

import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.ServiceCallback;

public interface EditContactService {
	void edit(int id, ServiceCallback<IContact> serviceCallback);
	
	void cancelCurrentCallback();
}