package mundoj.contacts.ui.client.edit;

import mundoj.contacts.domain.IContact;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SaveContactEvent extends GwtEvent<SaveContactEvent.Handler> {
	public interface Handler extends EventHandler {
		void onEvent(SaveContactEvent e);
	}
	public static final Type<Handler> TYPE = new Type<Handler>();
	
	public final IContact contact;

	public SaveContactEvent(IContact contact) {
		this.contact = contact;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onEvent(this);
	}
}