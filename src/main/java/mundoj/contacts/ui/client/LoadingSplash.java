package mundoj.contacts.ui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class LoadingSplash extends DialogBox {
	public interface Images extends ClientBundle {
		ImageResource loading();
	}
	private static final Images img = GWT.create(Images.class);
	private static LoadingSplash instance = new LoadingSplash();
	
	private LoadingSplash() {
		super(false, true);
		setGlassEnabled(true);
		setAnimationEnabled(true);
		Image i = new Image(img.loading());
		HorizontalPanel p = new HorizontalPanel();
		p.add(i);
		p.setWidth("100%");
		p.setCellHorizontalAlignment(i, HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(p);
	}

	public static void display(String title) {
		if (!instance.isShowing()) {
			instance.setHTML(title);
			instance.center();
			instance.show();
		}
	}

	public static void close() {
		instance.hide();
	}
}