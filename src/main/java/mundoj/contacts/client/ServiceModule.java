package mundoj.contacts.client;

import mundoj.contacts.domain.search.SearchContactsService;
import mundoj.contacts.domain.search.SearchContactsServiceTimerImpl;

import com.google.gwt.inject.client.AbstractGinModule;

public class ServiceModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(SearchContactsService.class).to(SearchContactsServiceTimerImpl.class);
	}
}