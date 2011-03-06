package mundoj.contacts.domain.edit;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.domain.ServiceCallback;

import com.google.gwt.user.client.Timer;

public class EditContactServiceTimerImpl implements EditContactService<Contact> {
	private Timer currentTimer;
	
	@Override
	public void edit(final int id, final ServiceCallback<Contact> serviceCallback) {
		emulateServerCallAndReturn(Contact.get(id), serviceCallback);
	}

	@Override
	public void save(Contact c, ServiceCallback<Contact> serviceCallback) {
		emulateServerCallAndReturn(c, serviceCallback);
	}

	@Override
	public void cancelCurrentCallback() {
		if (currentTimer!=null)
			currentTimer.cancel();
	}

	private void emulateServerCallAndReturn(final Contact c,
			final ServiceCallback<Contact> serviceCallback) {
		cancelCurrentCallback();

		// emula carregamento dos dados de um usuário, para edição
		currentTimer= new Timer() {
			public void run() {	
				serviceCallback.execute(c);	
			}
		};
		currentTimer.schedule(1000);
	}
}