package mundoj.contacts.domain.search;

import java.util.List;

import mundoj.commons.client.ServiceCallback;
import mundoj.contacts.domain.Contact;
import mundoj.contacts.domain.IContact;

import com.google.gwt.user.client.Timer;

public class SearchContactsServiceTimerImpl implements SearchContactsService {
	private Timer currentTimer;

	@Override
	public void searchContacts(final String keyword, 
		final ServiceCallback<List<? extends IContact>> serviceCallback) {
		
		cancelCurrentCallback();
		
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
		if (currentTimer!=null)
			currentTimer.cancel();
	}
}