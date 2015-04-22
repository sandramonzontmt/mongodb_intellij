package com.DB;

import java.net.UnknownHostException;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * Created by sandra.monzon on 21/04/2015.
 */
public class mConnection {
    private boolean SECURE_MODE;

    public mConnection(boolean secure_mode) {
        SECURE_MODE = secure_mode;
    }

    public MongoClient connection() {
        MongoClient mongo = null;
        try{
            if (SECURE_MODE){
                // for secure mode, need authentication
                mongo = new MongoClient();
                DB db = mongo.getDB("database name");
                boolean auth = db.authenticate("username", "password".toCharArray());
            }
            else{
                mongo = new MongoClient("localhost", 27017);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return mongo;
    }
}
