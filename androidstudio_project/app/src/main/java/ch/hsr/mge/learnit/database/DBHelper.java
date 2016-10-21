package ch.hsr.mge.learnit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_BACK;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_CARDSETID;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_FRONT;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_ID;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_TABLE_NAME;
import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_COLUMN_ID;
import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_COLUMN_NAME;
import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_TABLE_NAME;

/**
 * Created by nico on 21/10/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CARD_TABLE_NAME + "(" +
                CARD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CARD_COLUMN_FRONT + " TEXT, " +
                CARD_COLUMN_BACK + " TEXT, " +
                CARD_COLUMN_CARDSETID + " INTEGER, " +
                "FOREIGN KEY(" + CARD_COLUMN_CARDSETID + ") REFERENCES " + CARDSET_TABLE_NAME + "(" + CARDSET_COLUMN_ID + ")"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CARDSET_TABLE_NAME + "(" +
                CARDSET_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CARDSET_COLUMN_NAME + "TEXT)"
        );
    }
    public boolean insertCardSet(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(CARDSET_COLUMN_NAME, name);
        db.insert(CARDSET_TABLE_NAME, null, values);
        return true;
    }

    public boolean updateCardSet(Integer id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARDSET_TABLE_NAME, name);
        //HACK
        db.update(CARDSET_TABLE_NAME, values, CARDSET_COLUMN_ID + " ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public Cursor getCardSet(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "SELECT * FROM " + CARDSET_TABLE_NAME + " WHERE " +
                CARDSET_COLUMN_NAME + "=?", new String[] { name });
    }

    public Cursor getAllCardSets() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "SELECT " + CARDSET_COLUMN_NAME + " FROM " + CARDSET_TABLE_NAME, null);
    }

    public void deleteCardSet(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CARDSET_TABLE_NAME, CARDSET_COLUMN_NAME + " = ? ", new String[] { name });
    }

    public boolean insertCard(String front, String back, String cardSetName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cardSetCursor = db.rawQuery( "SELECT " + CardSetHelper.CARDSET_COLUMN_ID + " FROM " +
                CardSetHelper.CARDSET_TABLE_NAME + " WHERE " + CardSetHelper.CARDSET_COLUMN_NAME +
                " = ?", new String[] { cardSetName });
        Integer cardSetId;
        cardSetCursor.moveToFirst();
        cardSetId = cardSetCursor.getInt(cardSetCursor.getColumnIndex(CardSetHelper.CARDSET_COLUMN_ID));
        ContentValues values =  new ContentValues();
        values.put(CARD_COLUMN_FRONT, front);
        values.put(CARD_COLUMN_BACK, back);
        values.put(CARD_COLUMN_CARDSETID, cardSetId);
        db.insert(CARD_TABLE_NAME, null, values);
        cardSetCursor.close();
        return true;
    }

    public void deleteCard(Integer cardID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CARD_TABLE_NAME, CARD_COLUMN_ID + " = ?", new String[] { Integer.toString(cardID) });
    }
    //select count(id) from  *
    public int getAmountOfCards() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor numberCursor = db.rawQuery( "SELECT count(" + CARD_COLUMN_ID + ") as amount FROM " + CARD_TABLE_NAME, null);
        Integer amountOfCards = numberCursor.getInt(numberCursor.getColumnIndex("amount"));
        numberCursor.close();
        return amountOfCards;
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}