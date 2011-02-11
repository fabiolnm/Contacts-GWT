package mundoj.contacts.ui.client.edit;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import mundoj.contacts.client.AbstractActivityFactory;

public class EditContactActivityFactory extends AbstractActivityFactory<EditContactPlace> {
	private final EditContactView editor;

	@Inject
	protected EditContactActivityFactory(EventBus eventBus, EditContactView editor) {
		super(eventBus);
		this.editor = editor;
	}

	@Override
	protected Activity create(EventBus eventBus, EditContactPlace place) {
		return new EditContactActivity(editor, place);
	}
}