package com.example.xinthe.phonebook;

/**
 * Created by xinthe on 8/10/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by xinthe on 8/8/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    final static String TABLE_PHONEBOOK="contacts";
    final static String PB_ID="_id";
    final static String PB_FNAME="fname";
    final static String PB_LNAME="lname";
    final static String PB_NUMBER="phone";
    final static String PB_NICKNAME="nickname";
    public DBHelper(Context context) {
        super(context,"phonebook", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_PHONEBOOK+"("+PB_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+PB_FNAME+ " TEXT,"+PB_LNAME+ " TEXT," +PB_NUMBER+" TEXT,"+PB_NICKNAME+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertPhoneBook(HolderPhoneBook holderPhoneBook){
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PB_FNAME, holderPhoneBook.getFname());
            values.put(PB_LNAME, holderPhoneBook.getLname());
            values.put(PB_NUMBER, holderPhoneBook.getPhoneNumber());
            values.put(PB_NICKNAME, holderPhoneBook.getNickname());
            long id = db.insert(TABLE_PHONEBOOK, null, values);
            if(id==-1)
                return false;
            else
                return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<HolderPhoneBook> getPhoneBookList(){
        try {
            ArrayList<HolderPhoneBook> data = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_PHONEBOOK, null, null, null, null, null, null);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(PB_ID));
                String fname = cursor.getString(cursor.getColumnIndex(PB_FNAME));
                String lname = cursor.getString(cursor.getColumnIndex(PB_LNAME));
                String number = cursor.getString(cursor.getColumnIndex(PB_NUMBER));
                String nickname = cursor.getString(cursor.getColumnIndex(PB_NICKNAME));
                HolderPhoneBook hbd = new HolderPhoneBook(id, fname, lname, number, nickname);
                data.add(hbd);
                cursor.moveToNext();
            }
            return data;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public HolderPhoneBook getPhoneBookList(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PHONEBOOK,null,PB_ID+"=?",new String[]{" "+id},null,null,null);
        cursor.moveToFirst();
        String fname=cursor.getString(cursor.getColumnIndex(PB_FNAME));
        String lname=cursor.getString(cursor.getColumnIndex(PB_LNAME));
        String phnno=cursor.getString(cursor.getColumnIndex(PB_NUMBER));
        String nickname=cursor.getString(cursor.getColumnIndex(PB_NICKNAME));
        HolderPhoneBook hpb=new HolderPhoneBook(id,fname,lname,phnno,nickname);
        return hpb;

    }

    public long updatePhoneBook(HolderPhoneBook holderPhoneBook){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PB_FNAME,holderPhoneBook.getFname());
        values.put(PB_LNAME,holderPhoneBook.getLname());
        values.put(PB_NUMBER,holderPhoneBook.getPhoneNumber());
        values.put(PB_NICKNAME,holderPhoneBook.getNickname());
        long id = db.update(TABLE_PHONEBOOK,values,PB_ID+"="+holderPhoneBook.getId(),null);
        return id;
    }
    public Boolean deletePhoneBookContact(int id){
        SQLiteDatabase db = getWritableDatabase();
        long success = db.delete(TABLE_PHONEBOOK,PB_ID+"=?",new String[]{""+id});
        if(success != -1)
            return true;
        else return false;
    }
}
