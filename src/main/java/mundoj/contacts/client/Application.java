package mundoj.contacts.client;

import mundoj.contacts.domain.Contact;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

public class Application implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Window.alert("Hello World!\n\n" + Contact.getAll().size() + " contatos gerados.");
	}

}
