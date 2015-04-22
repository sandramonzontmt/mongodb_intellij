package com.DCBOX;


import com.DB.mCRUD;
import com.DB.mConnection;
import com.DB.mImage;
import com.mongodb.*;

/**
 * Java + MongoDB simple db
 *
 */
public class AppMain {

	static boolean secure_mode = false;
	static MongoClient mongo;

	public static void main(String[] args) {

		System.out.println("HELLO WORLD!");
		mConnection c = new mConnection(secure_mode);
		mongo = c.connection();

		System.out.println("\n------------");
		System.out.println("* CRUD OPS *");
		System.out.println("------------");
		make_crud_ops();

		System.out.println("\n-------------");
		System.out.println("* IMAGE OPS *");
		System.out.println("-------------");
		make_image_ops();

		/**** Done ****/
		System.out.println("Done ! ");
	}

	private static void make_crud_ops() {
		mCRUD ops = new mCRUD(mongo);
		ops.create("players");
		ops.create("clients");
		ops.create("contents");
		ops.create("photosNOFARES");
		ops.create("players", "name", "pantalla-01");
		ops.display("players");

		ops.find("players", "name", "pantalla-01");
		ops.update("players", "name", "pantalla-01", "pantalla-01.1");
		ops.display("players");

		ops.delete("player");
		ops.display("player");
		ops.delete("players", "name", "pantalla-01.1");
		ops.display("players");
	}


	private static void make_image_ops() {
		mImage img = new mImage(mongo);

		img.saveImage("mhappyface", "C:\\Users\\sandra.monzon\\Pictures\\happy face.jpg");
		//img.saveImageLocally("new happy face","C:\\Users\\sandra.monzon\\Pictures\\nova.png" );
		img.displayImage("happy face");
		img.displayImage("mhappyface");

		img.getImage("happy face");
		img.deleteImage("happy face");
		img.displayAllImages();
	}
}
