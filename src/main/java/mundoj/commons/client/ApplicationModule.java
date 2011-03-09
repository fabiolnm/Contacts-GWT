package mundoj.commons.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ApplicationModule extends AbstractGinModule {
	@Override
	protected void configure() {  }

	@Provides
	@Singleton
	public EventBus eventBus() {
		return new SimpleEventBus();
	}
	
	@Provides 
	@Singleton
	public PlaceController placeController(EventBus eventBus) {
		return new PlaceController(eventBus);
	}

	@Provides 
	@Singleton
	public ActivityManager activityManager(final EventBus eventBus) {
		return new ActivityManager(new ActivityMapper() {
			@Override
			public final Activity getActivity(Place place) {
				ActivityRequestEvent event = new ActivityRequestEvent(place);
				eventBus.fireEvent(event);
				return event.getActivity();
			}
		}, eventBus);
	}
}