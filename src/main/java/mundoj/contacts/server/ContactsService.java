package mundoj.contacts.server;

import java.util.List;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.domain.IContact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/contact")
public class ContactsService {
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public @ResponseBody List<IContact> search(@RequestParam String keyword) {
		return Contact.filter(keyword);
	}
}