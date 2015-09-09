package com.nghiatt.calculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nghiatt.calculator.model.HistoryItem;

import java.util.ArrayList;

/**
 * Created by ngh on 9/6/2015.
 */
public class HistoryDatabase extends SQLiteOpenHelper {
    public static final String TABLE_NAME="HistoryTable";
    public static final String DATABASE_NAME="Calculator";

    public HistoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                        + "date text primary key, "
                        + "expression text, "
                        + "result text, "
                        + "typeCalcu integer"
                        + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long insert(HistoryItem historyItem){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("date",historyItem.date);
        contentValues.put("expression", historyItem.expression);
        contentValues.put("result", historyItem.result);
        contentValues.put("typeCalcu", historyItem.typeCalcu);

        return db.insert(TABLE_NAME,null,contentValues);
    }

    public ArrayList<HistoryItem> getAllHistory(){
        ArrayList<HistoryItem> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,"date desc");//.rawQuery("select * from "+TABLE_NAME,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            HistoryItem historyItem=new HistoryItem();
            historyItem.date=cursor.getString(cursor.getColumnIndex("date"));
            historyItem.expression=cursor.getString(cursor.getColumnIndex("expression"));
            historyItem.result=cursor.getString(cursor.getColumnIndex("result"));
            historyItem.typeCalcu=cursor.getInt(cursor.getColumnIndex("typeCalcu"));
            list.add(historyItem);
            cursor.moveToNext();
        }
        return list;
    }

    public void deleteAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);

    }
}
