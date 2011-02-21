package mundoj.contacts.client;

import mundoj.contacts.domain.edit.EditContactService;
import mundoj.contacts.domain.edit.EditContactServiceJsonImpl;
import mundoj.contacts.domain.search.SearchContactsService;
import mundoj.contacts.domain.search.SearchContactsServiceJsonImpl;

import com.google.gwt.inject.client.AbstractGinModule;

public class ServiceModule extends AbstractGinModule {
	@Override
	protected void configure() {
		// bind(SearchContactsService.class).to(SearchContactsServiceTimerImpl.class);
		bind(SearchContactsService.class).to(SearchContactsServiceJsonImpl.class);
		
		// bind(EditContactService.class).to(EditContactServiceTimerImpl.class);
		bind(EditContactService.class).to(EditContactServiceJsonImpl.class);
	}
}