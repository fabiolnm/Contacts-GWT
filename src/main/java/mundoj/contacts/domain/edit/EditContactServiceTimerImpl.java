package mundoj.contacts.domain.edit;

import com.google.gwt.user.client.Timer;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.domain.IContact;
import mundoj.contacts.domain.ServiceCallback;

public class EditContactServiceTimerImpl implements EditContactService {
	private Timer currentTimer;
	
	@Override
	public void edit(final int id, final ServiceCallback<IContact> serviceCallback) {
		cancelCurrentCallback();
		
		// emula carregamento dos dados de um usuário, para edição
		currentTimer= new Timer() {
			public void run() {	
				serviceCallback.execute(Contact.get(id));	
			}
		};
		currentTimer.schedule(1000);
	}

	@Override
	public void cancelCurrentCallback() {
		if (currentTimer!=null)
			currentTimer.cancel();
	}
}