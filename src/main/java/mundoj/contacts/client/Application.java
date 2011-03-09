package mundoj.contacts.client;

import javax.inject.Named;

import mundoj.commons.client.ApplicationModule;


import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Application implements EntryPoint {
	@GinModules({
		ApplicationModule.class, ContactsModule.class
	})
	public interface Injector extends Ginjector {
		ActivityManager activityManager();

		@Named(ContactsModule.HISTORY_HANDLER)
		PlaceHistoryHandler historyHandler();
	}
	private Injector injector = GWT.create(Injector.class);
	
	@Override
	public void onModuleLoad() {
		SimplePanel mainPanel = new SimplePanel();
		RootLayoutPanel.get().add(mainPanel);
		injector.activityManager().setDisplay(mainPanel);
		injector.historyHandler().handleCurrentHistory();
	}
}