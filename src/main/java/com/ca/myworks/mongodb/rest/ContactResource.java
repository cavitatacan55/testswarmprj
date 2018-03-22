package com.ca.myworks.mongodb.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

import com.ca.myworks.mongodb.dao.ContactDao;
import com.ca.myworks.mongodb.model.Contact;

@RequestScoped
@Path("/contacts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

	@EJB
	ContactDao dao;

	@GET()
	@Path("/getAll")
	public List<Contact> getAll() {
		return dao.getAll();

	}

	@GET
	@Path("/{id}")
	public Contact get(@PathParam("id") String id) {

		return dao.findById(new ObjectId(id));
	}

	@POST
	@Path("/add")
	public void add(Contact c) {

		dao.save(c);
	}

	@DELETE
	@Path("/delete/{id}")
	public void delete(@PathParam("id") String id) {

		dao.delete(new ObjectId(id));

	}

	@PUT
	@Path("/update")
	public void update(Contact c) {

		dao.update(c);
	}

}
