package mundoj.contacts.ui.client.search;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SearchContactsPlace extends Place {
	public final String keyword;

	public SearchContactsPlace(String keyword) {
		this.keyword = keyword;
	}
	
	@Prefix("contacts")
	public static class Tokenizer implements PlaceTokenizer<SearchContactsPlace> {
		@Override
		public SearchContactsPlace getPlace(String token) {
			return new SearchContactsPlace(token);
		}
		@Override
		public String getToken(SearchContactsPlace place) {
			return place.keyword;
		}
	}
}