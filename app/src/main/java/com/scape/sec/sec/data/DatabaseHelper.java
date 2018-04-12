package com.scape.sec.sec.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scape.sec.sec.Model.ExhibitionEntry;
import com.scape.sec.sec.Model.WorkEntry;

/**
 * Created by Me on 24/10/2016.
 */

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "works.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_EXHIBITION_TABLE = "CREATE TABLE " + ExhibitionEntry.TABLE_NAME + " (" +

                ExhibitionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ExhibitionEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ExhibitionEntry.COLUMN_IMAGE_URL + "TEXT NOT NULL" +");";

        final String SQL_CREATE_WORK_TABLE = "CREATE TABLE " + WorkEntry.TABLE_NAME + " (" +

                WorkEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WorkEntry.COLUMN_LOCATION_NAME + " TEXT UNIQUE NOT NULL, " +
                WorkEntry.COLUMN_WORK_NAME + " TEXT NOT NULL, " +
                WorkEntry.COLUMN_IMAGE_URL + "TEXT NOT NULL, " +
                WorkEntry.COLUMN_AUDIO_URL + "TEXT NOT NULL,"+
                WorkEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
                WorkEntry.COLUMN_COORD_LONG + " REAL NOT NULL" + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_WORK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
