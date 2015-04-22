package com.DB;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sandra.monzon on 22/04/2015.
 */
public class mImage {

    DB db;

    private static byte[] LoadImage(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }

    public mImage(MongoClient mongo){
        db = mongo.getDB("mydcbox");
    }

    public void saveImage(String newFileName, String pathImage){
        System.out.println("\nSAVE IMAGE in path " + pathImage + " with new name " + newFileName );

        //Load our image
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = LoadImage(pathImage);
            GridFS gfsPhotos = new GridFS( db, "photo" );
            //Save image into database
            GridFSInputFile gfsFile = gfsPhotos.createFile( imageBytes );
            gfsFile.save();
            //Find saved image
            GridFSDBFile out = gfsPhotos.findOne( new BasicDBObject( "_id" , gfsFile.getId() ) );
            //Save loaded image from database into new image file
            FileOutputStream outputImage = new FileOutputStream(pathImage);
            out.writeTo( outputImage );
            outputImage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        GridFS gfsPhoto = new GridFS(db, "photo");
        GridFSInputFile gfsFile = gfsPhoto.createFile(pathImage);
        gfsFile.setFilename(newFileName);
        gfsFile.save(); //aqui peta pk es es null*/
    }

    public void saveImageLocally(String fileName, String localPathImage){
        System.out.println("\nSAVE IMAGE LOCALLY in local path " +  localPathImage +  " with name " + fileName);
        GridFS gfsPhoto = new GridFS(db, "photo");
        GridFSDBFile imageForOutput = gfsPhoto.findOne(fileName);

        try {
            imageForOutput.writeTo(localPathImage); //output to new file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getImage(String fileName){
        System.out.println("\nGET IMAGE with name " +  fileName);
        GridFS gfsPhoto = new GridFS(db, "photo");
        GridFSDBFile imgOutput = gfsPhoto.findOne(fileName);
        System.out.println("Specs: " + imgOutput);
    }

    public void displayImage(String fileName){
        System.out.println("\nDISPLAY IMAGE with name " + fileName);
        GridFS gfsPhoto = new GridFS(db, "photo");

        DBCursor cursor = gfsPhoto.getFileList();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public void displayAllImages(){
        System.out.println("\nDISPLAY ALL IMAGES in db");
        GridFS gfsPhoto = new GridFS(db, "photo");

        DBCursor cursor = gfsPhoto.getFileList();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public void deleteImage(String fileName){
        System.out.println("\nDELETE IMAGE with name " + fileName);
        GridFS gfsPhoto = new GridFS(db, "photo");
        gfsPhoto.remove(gfsPhoto.findOne(fileName));
    }

}
