package mundoj.contacts.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class Contact implements Comparable<Contact> {
	public final Integer id;
	private String name, type, number;

	private static int genId;

	Contact(String name, String type, String number) {
		this.name = name;
		this.type = type;
		this.number = number;
		id = ++genId;
	}

	// getters e setters

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	// compareTo, usado para ordenar os contatos em um TreeSet.
	@Override
	public int compareTo(Contact o) {
		int cmp = name.compareTo(o.name);
		if (cmp == 0) {
			cmp = type.compareTo(o.type);
			if (cmp == 0)
				cmp = number.compareTo(o.number);
		}
		return cmp == 0 ? id.compareTo(o.id) : cmp;
	}

	// lista de nomes, usados para gerar uma lista randômica de contatos
	private static final String[] names = { 
		"Antonio", "Bruno", "Carlos", "Dionísio", "Emanuel", "Fábio", 
		"Gabriel", "Haroldo", "Ivander", "João", "Kaká", "Luís", "Marcos",
		"Norberto", "Otávio", "Pedro", "Quincas", "Ricardo", "Samuel",
		"Teotônio", "Ulisses", "Valdecir", "Wagner", "Xavier", "Yago", "Zazá",
		
		"Ana", "Bianca", "Carolina", "Daniela", "Eliane", "Flávia", 
		"Gabriela", "Hérica", "Isabela", "Joana", "Karla", "Liane", 
		"Maria", "Nair", "Oscarla", "Paula", "Quirina", "Roberta", 
		"Sandra", "Tatiane", "Ursula", "Vivian", "Wanessa", "Xaiane", "Yara", "Zilzane" 
	};
	
	private static final String[] types = { 
		"Residencial", "Trabalho", "Celular" 
	};

	private static Random r = new Random();

	private static HashMap<Integer, Contact> generate() {
		HashMap<Integer, Contact> map = new HashMap<Integer, Contact>();
		for (int i = 0; i < 75; i++) {
			String name = names[r.nextInt(names.length)];
			String type = types[r.nextInt(types.length)];
			Contact c = new Contact(name, type, randomNumber());
			map.put(c.id, c);
		}
		return map;
	}

	private static String randomNumber() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++)
			sb.append(r.nextInt(10));
		sb.append("-");
		for (int i = 0; i < 4; i++)
			sb.append(r.nextInt(10));
		return sb.toString();
	}

	public static Contact get(Integer id) {
		return contacts.get(id);
	}

	public static Collection<Contact> getAll() {
		return Collections.unmodifiableSet(new TreeSet<Contact>(contacts.values()));
	}

	public static List<Contact> filter(String value) {
		TreeSet<Contact> res = new TreeSet<Contact>(); // TreeSet para ordenar contatos filtrados

		if (value == null || value.isEmpty())
			res.addAll(contacts.values());
		else {
			value = value.toLowerCase();
			for (Contact c : contacts.values()) {
				if (c.name.toLowerCase().contains(value))
					res.add(c);
				else if (c.type.toLowerCase().contains(value))
					res.add(c);
				else if (c.number.toLowerCase().contains(value))
					res.add(c);
			}
		}
		return new ArrayList<Contact>(res);
	}

	private static final HashMap<Integer, Contact> contacts = generate();
}