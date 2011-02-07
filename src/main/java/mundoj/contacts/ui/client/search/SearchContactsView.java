package mundoj.contacts.ui.client.search;

import java.util.Collection;

import mundoj.contacts.domain.Contact;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class SearchContactsView extends LazyPanel {
	interface ContactSearchUiBinder extends	UiBinder<Widget, SearchContactsView> { }
	private static ContactSearchUiBinder uiBinder = GWT.create(ContactSearchUiBinder.class);

	@Override
	protected Widget createWidget() {
		initContactsTable();
		return uiBinder.createAndBindUi(this);
	}

	@UiField TextBox keyword;
	@UiField Button button;
	@UiField(provided = true) CellTable<Contact> contactsTable;
	@UiField(provided = true) SimplePager pager;

	private ListDataProvider<Contact> data;

	@UiHandler("button")
	void onClick(ClickEvent e) {
		update(Contact.filter(keyword.getValue()));
	}
	
	public void update(Collection<Contact> contacts) {
		setVisible(true);
		contactsTable.setRowCount(contacts.size());
		data.getList().clear();
		data.getList().addAll(contacts);
	}

	private CellTable<Contact> initContactsTable() {
		contactsTable = new CellTable<Contact>();
		HighlightCell highlightKeyword = new HighlightCell();
		contactsTable.addColumn(new Column<Contact, String>(highlightKeyword) {
			public @Override
			String getValue(Contact c) {
				return c.getName();
			}
		}, "Nome");
		contactsTable.addColumn(new Column<Contact, String>(highlightKeyword) {
			public @Override
			String getValue(Contact c) {
				return c.getType();
			}
		}, "Tipo");
		contactsTable.addColumn(new Column<Contact, String>(highlightKeyword) {
			public @Override
			String getValue(Contact c) {
				return c.getNumber();
			}
		}, "Número");

		data = new ListDataProvider<Contact>();
		data.addDataDisplay(contactsTable);

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(contactsTable);

		return contactsTable;
	}

	class HighlightCell extends AbstractCell<String> {
		private static final String replaceString = "<span style='color:red;font-weight:bold;'>$1</span>";

		@Override
		public void render(Context context, String value, SafeHtmlBuilder sb) {
			if (value != null) {
				String key = keyword.getValue();
				if (key == null || key.isEmpty())
					sb.appendEscaped(value);
				else {
					RegExp searchRegExp = RegExp.compile("(" + key + ")", "ig");
					String escapedHtml = SafeHtmlUtils.htmlEscape(value);
					value = searchRegExp.replace(escapedHtml, replaceString);
					sb.append(SafeHtmlUtils.fromTrustedString(value));
				}
			}
		}
	}
}