package ch.hsr.mge.learnit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nico on 21/10/16.
 */

public class CardSetHelper extends DBHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String CARDSET_TABLE_NAME = "cardset";
    public static final String CARDSET_COLUMN_ID = "cardsetid";
    public static final String CARDSET_COLUMN_NAME = "cardsetname";
    //may not be necessary
    public static final String CARDSET_COLUMN_AMOUNTOFCARDS = "amountOfCards";


    public CardSetHelper(Context context) {
        super(context);
    }
    //TODO: write create table query
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

}
