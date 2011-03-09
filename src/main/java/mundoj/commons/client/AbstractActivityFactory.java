package mundoj.commons.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;

public abstract class AbstractActivityFactory<P extends Place> {
	protected abstract Activity create(EventBus eventBus, P place);

	protected AbstractActivityFactory(final EventBus eventBus) {
		eventBus.addHandler(ActivityRequestEvent.TYPE, new ActivityRequestEvent.Handler() {
			@Override
			@SuppressWarnings("unchecked")
			protected Activity createActivity(Place place) {
				try {
					return create(eventBus, (P) place);
				} catch (ClassCastException e) {
					return null;
				}
			}
		});
	}
}