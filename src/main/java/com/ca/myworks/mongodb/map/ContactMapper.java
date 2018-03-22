package com.ca.myworks.mongodb.map;

import org.bson.Document;


import com.ca.myworks.mongodb.model.Contact;


public class ContactMapper {

	private static final String ATTR_ID = "_id";
	private static final String ATTR_NAME = "name";
	private static final String ATTR_EMAIL = "email";
	private static final String ATTR_PHONE = "phone";

	public Contact fromDocument(Document document) {
		Contact entry = new Contact();
		entry.set_id(document.getObjectId(ATTR_ID).toHexString());
		entry.setName(document.getString(ATTR_NAME));
		entry.setEmail(document.getString(ATTR_EMAIL));
		entry.setPhone(document.getString(ATTR_PHONE));
		
		return entry;
	}

	public Document toDocument(Contact entry) {
		
		Document document = new Document();
		if (entry.get_id() != null) {
			document.append(ATTR_ID, entry.get_id());
		}
		return document.append(ATTR_NAME, entry.getName()).
				        append(ATTR_EMAIL, entry.getEmail()).
				        append(ATTR_PHONE, entry.getPhone());
	}
}
