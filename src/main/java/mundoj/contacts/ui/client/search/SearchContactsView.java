package mundoj.contacts.ui.client.search;

import java.util.Collection;

import mundoj.contacts.domain.IContact;
import mundoj.contacts.ui.client.edit.EditContactPlace;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceController;
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
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

public class SearchContactsView extends LazyPanel {
	interface ContactSearchUiBinder extends	UiBinder<Widget, SearchContactsView> { }
	private static ContactSearchUiBinder uiBinder = GWT.create(ContactSearchUiBinder.class);

	@UiField TextBox keyword;
	@UiField Button button;
	@UiField(provided = true) CellTable<IContact> contactsTable;
	@UiField(provided = true) SimplePager pager;

	private PlaceController placeController;
	
	private ListDataProvider<IContact> data;

	private boolean isLoading;

	@Inject
	public SearchContactsView(PlaceController placeController) {
		this.placeController = placeController;
	}

	@Override
	protected Widget createWidget() {
		initContactsTable();
		return uiBinder.createAndBindUi(this);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		placeController.goTo(new SearchContactsPlace(keyword.getValue()));
	}
	
	public void update(Collection<? extends IContact> contacts) {
		setVisible(true);
		contactsTable.setRowCount(contacts.size());
		data.getList().clear();
		data.getList().addAll(contacts);
		isLoading = false;
	}

	public void startLoading(String keyword) {
		ensureWidget();
		isLoading = true;
		pager.firstPage();
		pager.startLoading();
		contactsTable.setRowCount(1);
		this.keyword.setValue(keyword);
	}

	public boolean isLoading() {
		return isLoading;
	}

	private CellTable<IContact> initContactsTable() {
		contactsTable = new CellTable<IContact>();
		HighlightCell highlightKeyword = new HighlightCell();
		contactsTable.addColumn(new Column<IContact, String>(highlightKeyword) {
			public @Override
			String getValue(IContact c) {
				return c.getName();
			}
		}, "Nome");
		contactsTable.addColumn(new Column<IContact, String>(highlightKeyword) {
			public @Override
			String getValue(IContact c) {
				return c.getType();
			}
		}, "Tipo");
		contactsTable.addColumn(new Column<IContact, String>(highlightKeyword) {
			public @Override
			String getValue(IContact c) {
				return c.getNumber();
			}
		}, "Número");

		data = new ListDataProvider<IContact>();
		data.addDataDisplay(contactsTable);

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(contactsTable);
		
		final SingleSelectionModel<IContact> selectionModel = new SingleSelectionModel<IContact>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				setVisible(false);
				placeController.goTo(new EditContactPlace(selectionModel.getSelectedObject().getId()));
			}
		});
		contactsTable.setSelectionModel(selectionModel);

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