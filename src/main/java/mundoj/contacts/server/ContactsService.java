package mundoj.contacts.server;

import java.util.List;

import mundoj.contacts.domain.Contact;
import mundoj.contacts.domain.IContact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public @ResponseBody Contact edit(@PathVariable int id) {
		return Contact.get(id);
	}

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public @ResponseBody Contact save(@RequestBody Contact contact) {
		return Contact.save(contact);
	}
}