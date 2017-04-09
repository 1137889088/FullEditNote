package com.chen.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chen on 2017/4/4.
 */
public class NoteDB extends SQLiteOpenHelper {

    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态
    //notes数据库
    public static final String TABLE_NAME_NOTES = "notes"; //数据库名称
    public static final String COLUM_NAME_NOTES_ID = "_id";
    public static final String COLUM_NAME_NOTES_TITLE = "title";
    public static final String COLUM_NAME_NOTES_CONTENT = "_content";
    public static final String COLUM_NAME_NOTES_DATE = "date";

/*  //media数据库
    public static final String TABLE_NAME_MEDIA = "media"; //数据库名称
    public static final String COLUM_NAME_MEDIA_ID = "_id";
    public static final String COLUM_NAME_MEDIA_TITLE = "title";
    public static final String COLUM_NAME_MEDIA_CONTENT = "_content";
    public static final String COLUM_NAME_MEDIA_DATE = "date";*/

    private static final int version = 1; //数据库版本

    public NoteDB(Context context) {
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context, TABLE_NAME_NOTES, null, version);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_NOTES + "(" +
                COLUM_NAME_NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUM_NAME_NOTES_TITLE + " TEXT  DEFAULT \"\"," +
                COLUM_NAME_NOTES_CONTENT + " TEXT  DEFAULT \"\"," +
                COLUM_NAME_NOTES_DATE + " TEXT DEFAULT \"\"" +
                ")");

        ContentValues cv = new ContentValues();
        cv.put(NoteDB.COLUM_NAME_NOTES_TITLE, "");
        cv.put(NoteDB.COLUM_NAME_NOTES_CONTENT,"");
        //new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
        cv.put(NoteDB.COLUM_NAME_NOTES_DATE, "");
       /* getWritableDatabase().insert(NoteDB.TABLE_NAME_NOTES, "''", cv);*/
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
