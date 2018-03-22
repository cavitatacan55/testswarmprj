package com.ca.myworks.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.ca.myworks.mongodb.util.MongoClientProvider;
import com.ca.myworks.mongodb.map.ContactMapper;
import com.ca.myworks.mongodb.model.Contact;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Stateless
public class ContactDao {

	public final String COLLECTION_NAME = "Contact";
	private ContactMapper mapper = new ContactMapper();
	
	@EJB
	MongoClientProvider mongoClientProvider;
	
	

	public List<Contact> getAll() {
		
		List<Contact> list = new ArrayList<Contact>();
		
    	MongoCollection<Document> collection = mongoClientProvider.getMongoClient().getDatabase(mongoClientProvider.DATABASE).getCollection(COLLECTION_NAME);
		
		FindIterable<Document> documents = collection.find();
		for (Document document : documents) {
            list.add(mapper.fromDocument(document));
        }
        return list;
	}
	
	
    public void save(Contact entry) {
    	
    	MongoCollection<Document> collection = mongoClientProvider.getMongoClient().getDatabase(mongoClientProvider.DATABASE).getCollection(COLLECTION_NAME);
    	
    	collection.insertOne(mapper.toDocument(entry));
    }
       
    
    public void delete(ObjectId id) {
    	
    	MongoCollection<Document> collection = mongoClientProvider.getMongoClient().getDatabase(mongoClientProvider.DATABASE).getCollection(COLLECTION_NAME);
        Bson filter = Filters.eq("_id", id);
        DeleteResult rslt = collection.deleteOne(filter);
        System.out.println("Deleted Record count" + rslt);
       }
    
    public void update(Contact entry) {
    	
    	MongoCollection<Document> collection = mongoClientProvider.getMongoClient().getDatabase(mongoClientProvider.DATABASE).getCollection(COLLECTION_NAME);
    	Document doc = mapper.toDocument(entry);
    	doc.remove("_id"); 
    	Bson filter = Filters.eq("_id", new ObjectId(entry.get_id()));
    	UpdateResult rslt = collection.replaceOne(filter, doc);
    	System.out.println("Updated Record count" + rslt);
    }
    
    public Contact findById(ObjectId id) {	
    	BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", id);
		MongoCollection<Document> collection = mongoClientProvider.getMongoClient().getDatabase(mongoClientProvider.DATABASE).getCollection(COLLECTION_NAME);
		FindIterable<Document> cursor = collection.find(searchQuery);
		return mapper.fromDocument(cursor.first());
    }
    
}
