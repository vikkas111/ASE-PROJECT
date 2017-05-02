package com.example.criminaldatabase.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.criminaldatabase.model.Police;
import com.example.criminaldatabase.model.UploadCriminal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CriminalDatabase";

    // Police table name
    private static final String TABLE_POLICE = "police";

    // Police table name
    private static final String TABLE_UPLOAD_CRIMINAL = "uploadcriminal";

    // police Table Columns names
    private static final String KEY_ID_POLICE = "id";
    private static final String KEY_FULL_NAME_POLICE = "name";
    private static final String KEY_EMAIL_ID_POLICE = "email_id";
    private static final String KEY_MOBILE_POLICE = "mobile";
    private static final String KEY_ADDRESS_POLICE = "address";
    private static final String KEY_USERNAME_POLICE = "username";
    private static final String KEY_PASSWORD_POLICE = "password";


    // UploadCriminal Table Columns names

    private static final String KEY_CRIMINAL_NAME_POLICE = "name";
    private static final String KEY_CRIME_REC_NO = "crime_rec_no";
    private static final String KEY_CRIME_DESCRIPTION = "crime_description";
    private static final String KEY_CRIME_IMAGE = "crime_image";
    private static final String KEY_CRIME_LOCATION = "crime_location";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POLICE_TABLE = "CREATE TABLE " + TABLE_POLICE + "("
                + KEY_ID_POLICE + " INTEGER PRIMARY KEY,"
                + KEY_FULL_NAME_POLICE + " TEXT,"
                + KEY_EMAIL_ID_POLICE + " TEXT,"
                + KEY_MOBILE_POLICE + " TEXT,"
                + KEY_ADDRESS_POLICE + " TEXT,"
                + KEY_USERNAME_POLICE + " TEXT,"
                + KEY_PASSWORD_POLICE + " TEXT"
                + ")";
        db.execSQL(CREATE_POLICE_TABLE);

        String CREATE_UPLOAD_CRIMINAL_TABLE = "CREATE TABLE " + TABLE_UPLOAD_CRIMINAL + "("
                + KEY_ID_POLICE + " INTEGER PRIMARY KEY,"
                + KEY_CRIMINAL_NAME_POLICE + " TEXT,"
                + KEY_EMAIL_ID_POLICE + " TEXT,"
                + KEY_MOBILE_POLICE + " TEXT,"
                + KEY_ADDRESS_POLICE + " TEXT,"
                + KEY_CRIME_REC_NO + " TEXT,"
                + KEY_CRIME_DESCRIPTION + " TEXT,"
                + KEY_CRIME_IMAGE + " TEXT,"
                + KEY_CRIME_LOCATION + " TEXT"
                + ")";
        db.execSQL(CREATE_UPLOAD_CRIMINAL_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POLICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPLOAD_CRIMINAL);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addPolice(Police police) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FULL_NAME_POLICE, police.get_fullname());
        values.put(KEY_EMAIL_ID_POLICE, police.get_email_id());
        values.put(KEY_ADDRESS_POLICE, police.get_address());
        values.put(KEY_MOBILE_POLICE, police.get_mobile());
        values.put(KEY_USERNAME_POLICE, police.get_username());
        values.put(KEY_PASSWORD_POLICE, police.get_password());


        // Inserting Row
        db.insert(TABLE_POLICE, null, values);
        db.close(); // Closing database connection
    }

    // Getting All police
    public List<Police> getAllPolice() {
        List<Police> policeList = new ArrayList<Police>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_POLICE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Police police = new Police();
                police.set_id(Integer.parseInt(cursor.getString(0)));
                police.set_fullname(cursor.getString(1));
                police.set_email_id(cursor.getString(2));
                police.set_mobile(cursor.getString(3));
                police.set_address(cursor.getString(4));
                police.set_username(cursor.getString(5));
                police.set_password(cursor.getString(6));
                // Adding contact to list
                policeList.add(police);
            } while (cursor.moveToNext());
        }

        // return contact list
        return policeList;
    }

    // Adding new contact
    public void addUpload(UploadCriminal uploadcriminal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CRIMINAL_NAME_POLICE, uploadcriminal.get_criminalname());
        values.put(KEY_EMAIL_ID_POLICE, uploadcriminal.get_email_id());
        values.put(KEY_ADDRESS_POLICE, uploadcriminal.get_address());
        values.put(KEY_MOBILE_POLICE, uploadcriminal.get_mobile());
        values.put(KEY_CRIME_REC_NO, uploadcriminal.get_criminal_rec_no());
        values.put(KEY_CRIME_DESCRIPTION, uploadcriminal.get_crime_description());
        values.put(KEY_CRIME_IMAGE, uploadcriminal.get_image());
        values.put(KEY_CRIME_LOCATION, uploadcriminal.get_crime_location());


        // Inserting Row
        db.insert(TABLE_UPLOAD_CRIMINAL, null, values);
        db.close(); // Closing database connection
    }
    // Getting All police
    public List<UploadCriminal> getAllUPLOADED_CRIMINALS() {
        List<UploadCriminal> uploadedCriminalsList = new ArrayList<UploadCriminal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_UPLOAD_CRIMINAL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UploadCriminal uploadedcriminal = new UploadCriminal();
                uploadedcriminal.set_id(Integer.parseInt(cursor.getString(0)));
                uploadedcriminal.set_criminalname(cursor.getString(1));
                uploadedcriminal.set_email_id(cursor.getString(2));
                uploadedcriminal.set_mobile(cursor.getString(3));
                uploadedcriminal.set_address(cursor.getString(4));
                uploadedcriminal.set_criminal_rec_no(cursor.getString(5));
                uploadedcriminal.set_crime_description(cursor.getString(6));
                uploadedcriminal.set_image(cursor.getString(7));
                uploadedcriminal.set_crime_location(cursor.getString(8));
                // Adding contact to list
                uploadedCriminalsList.add(uploadedcriminal);
            } while (cursor.moveToNext());
        }

        // return contact list
        return uploadedCriminalsList;
    }
}
