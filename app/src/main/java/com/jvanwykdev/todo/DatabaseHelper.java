package com.jvanwykdev.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME =  "mylist.db";
    public static final String TABLE_NAME = "mylist_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM1";


    //----Table 2----Done Fragment---
    public static final String DONE_TABLE_NAME = "donelist_data";
    public static final String DONE_COL1 = "DONEID";
    public static final String DONE_COL2 = "DONEITEM";

    //-------END

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "ITEM1 TEXT)";

        String createDoneTable = "CREATE TABLE " + DONE_TABLE_NAME + " (DONEID INTEGER PRIMARY KEY AUTOINCREMENT, " + "DONEITEM TEXT)";


        sqLiteDatabase.execSQL(createTable);
        sqLiteDatabase.execSQL(createDoneTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addData(String item1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getListContents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public Cursor getListContentsDone()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + DONE_TABLE_NAME, null);
        return data;
    }

    public Cursor getItemID(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        item=item.replaceAll("'","''");
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + item + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getItemIDDone(String item){
        SQLiteDatabase db = this.getWritableDatabase();

        item=item.replaceAll("'","''");
        String query = "SELECT " + DONE_COL1 + " FROM " + DONE_TABLE_NAME + " WHERE " + DONE_COL2 + " = '" + item + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void updateData(String newData, int id, String oldData){
        SQLiteDatabase db = this.getWritableDatabase();

        oldData=oldData.replaceAll("'","''");
        newData=newData.replaceAll("'","''");
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newData + "' WHERE " + COL1 + " = '" + id + "'" + " AND " + COL2 + " = '" + oldData + "'";
        db.execSQL(query);
    }

    public void deleteData(int id, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + data + "'";
        db.execSQL(query);
    }

    public void deleteDataDone(int id, String data){
        SQLiteDatabase db = this.getWritableDatabase();

        data=data.replaceAll("'","''");
        String query = "DELETE FROM " + DONE_TABLE_NAME + " WHERE "
                + DONE_COL1 + " = '" + id + "'" +
                " AND " + DONE_COL2 + " = '" + data + "'";
        db.execSQL(query);
    }

    public boolean moveToDone(int id, String data){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(DONE_COL2, data);

        long result = db.insert(DONE_TABLE_NAME, null, contentValues);


        data=data.replaceAll("'","''");
        //----THE DELETE PART
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + data + "'";
        db.execSQL(query);


        if (result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean moveToTodo(int id, String data){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, data);

        long result = db.insert(TABLE_NAME, null, contentValues);



        //----THE DELETE PART
        String query = "DELETE FROM " + DONE_TABLE_NAME + " WHERE "
                + DONE_COL1 + " = '" + id + "'" +
                " AND " + DONE_COL2 + " = '" + data + "'";
        db.execSQL(query);


        if (result == -1){
            return false;
        }
        else{
            return true;
        }


    }

    public void movedBack(int id, String data){
        addData(data);
        deleteDataDone(id, data);
    }

    @Override
    public synchronized void close() {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            db.close();
            super.close();
        }
    }
}
