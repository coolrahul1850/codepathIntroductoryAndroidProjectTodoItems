package org.snake.android.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TaskDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/org.snake.android.myapplication/databases/";
    private static final String DATABASE_NAME = "todoitems.db";
    public static final String TABLE_TODOITEMS = "todoitems";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODOITEMNAME = "todoitemname";


    public TaskDBHandler (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOITEMS);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String myPath = DB_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        String query = "CREATE TABLE " + TABLE_TODOITEMS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_TODOITEMNAME + " TEXT " +
                ");";

        db.execSQL(query);
    }

    public void addTodoItem (Tasks todoitems){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODOITEMNAME, todoitems.get_taskname());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TODOITEMS, null, values);
        db.close();

    }

    public void deleteTodoItem (String todoitem){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TODOITEMS + " WHERE " + COLUMN_TODOITEMNAME + "=\"" + todoitem + "\";");

    }

    public String databaseToString()
    {
        String dbString = "";
        SQLiteDatabase db;
        String myPath = DB_PATH + DATABASE_NAME;

       db = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
       db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TODOITEMS + " WHERE 1 ";


        //Cursor is here
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("todoitemname"))!=null)
            {
                dbString += c.getString(c.getColumnIndex("todoitemname"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
