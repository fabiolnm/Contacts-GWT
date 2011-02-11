package mundoj.contacts.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.place.shared.Place;

public class ActivityRequestEvent extends GwtEvent<ActivityRequestEvent.Handler> {
	public abstract static class Handler implements EventHandler {
		private void onEvent(ActivityRequestEvent e) {
			if (e.isLive()) {
				e.activity = createActivity(e.place); 
				if (e.activity!=null)
					e.kill();
			}
		}
		
		protected abstract Activity createActivity(Place place);
	}
	
	public final Place place;
	private Activity activity;

	public ActivityRequestEvent(Place place) {
		this.place = place;
	}

	public Activity getActivity() {
		return activity;
	}
	
	public static final Type<Handler> TYPE = new Type<Handler>();

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onEvent(this);
	}
	
	@Override
	protected void revive() {
		if (activity!=null)
			throw new IllegalStateException("Activity already resolved, cannot revive this event!");
		super.revive();
	}
}