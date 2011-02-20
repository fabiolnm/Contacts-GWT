package mundoj.contacts.domain.search;

import java.util.List;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.ServiceCallback;

import com.google.gwt.user.client.Timer;

public class SearchContactsServiceTimerImpl implements SearchContactsService {
	private Timer currentTimer;

	@Override
	public void searchContacts(final String keyword, 
		final ServiceCallback<List<? extends IContact>> serviceCallback) {
		// timer = emula delay em uma chamada cliente-servidor
		currentTimer = new Timer() {
			@Override
			public void run() {
				serviceCallback.execute(Contact.filter(keyword));
			}
		};
		currentTimer.schedule(3000);
	}

	@Override
	public void cancelCurrentCallback() {
		currentTimer.cancel();
	}
}