package com.DB;

import com.mongodb.*;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.util.Date;

/**
 * Created by sandra.monzon on 21/04/2015.
 */
public class mCRUD {

    private DB db;
   // private DBCollection collection;

    public mCRUD(MongoClient mongo) {
        /**** Get database ****/
        // if database doesn't exists, MongoDB will create it for you
        db = mongo.getDB("mydcbox");
    }

    public void create(String mCollection){
        System.out.println('\n' +  "CREATE collection " + mCollection);
        //check if exists..
        db.getCollection(mCollection);
    }

    public void create(String mCollection, String mField, String mValue) {
        //make array
        System.out.println('\n' + "CREATE document in collection " + mCollection);
        /**** Insert ****/
        // create a document to store key and value
        BasicDBObject document = new BasicDBObject();
        document.put(mField, mValue);
        document.put("size", 30);
        document.put("createdDate", new Date());
        db.getCollection(mCollection).insert(document);
    }

    public void find(String mCollection, String mField, String mValue){
        /**** Find and display ****/
        System.out.println('\n' + "SEARCH document in collection " + mCollection + " where " + mField + " eq " + mValue);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(mField, mValue);

        DBCursor cursor = db.getCollection(mCollection).find(searchQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public void update(String mCollection, String mField, String mValue, String mNewValue){
        System.out.println('\n' + "UPDATE document in collection " + mCollection + " where " + mField + " eq " + mValue + " with " + mNewValue);
        /**** Update ****/
        BasicDBObject query = new BasicDBObject();
        query.put(mField, mValue);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(mField, mNewValue);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        db.getCollection(mCollection).update(query, updateObj);
    }

    public void display(String mCollection){
        System.out.println('\n' + "DISPLAY all documents in collection " + mCollection);

        DBCursor cursor = db.getCollection(mCollection).find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public void display(String mCollection, String mField, String mValue){
        System.out.println('\n' + "DISPLAY document in collection " + mCollection + " where " + mField + " eq " + mValue);
        /**** Find and display ****/
        BasicDBObject searchQuery2 = new BasicDBObject().append(mField, mValue);

        DBCursor cursor2 = db.getCollection(mCollection).find(searchQuery2);
        while (cursor2.hasNext()) {
            System.out.println(cursor2.next());
        }
    }


    public void delete(String mCollection) {
        System.out.println('\n' + "DELETE collection " + mCollection);
        /**** Delete collection ****/
        db.getCollection(mCollection).drop();
    }

    public void delete(String mCollection, String mField, String mValue) {
        System.out.println('\n' + "DELETE documents in collection " + mCollection + " where " + mField + " eq " + mValue);
        /**** Delete document ****/
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(mField, mValue);

        db.getCollection(mCollection).remove(searchQuery);
    }
}
