package mundoj.contacts.ui.client.edit;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditContactPlace extends Place {
	public final Integer contactId;

	public EditContactPlace(Integer contactId) {	
		this.contactId = contactId;
	}

	@Prefix("contact")
	public static class Tokenizer implements PlaceTokenizer<EditContactPlace> {
		public EditContactPlace getPlace(String token) {
			return new EditContactPlace(new Integer(token));
		}
		public String getToken(EditContactPlace place) {
			return place.contactId.toString();
		}
	}
}