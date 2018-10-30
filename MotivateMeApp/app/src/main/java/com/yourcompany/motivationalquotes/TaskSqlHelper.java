package com.yourcompany.motivationalquotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ps1 on 3/1/16.
 */
public class TaskSqlHelper extends SQLiteOpenHelper {
    public static final String TABLE_MOTIVATE = "motivatetracker";
    public static final String COLUMN_ID = "_id";
    public static final String KEY_TASKNAME = "taskname";
    public static final String KEY_TASKMOTIVE = "taskmotive";
    public static final String KEY_TODAYSTATUS = "todaystatus";
    public static final String KEY_MOTIVATEDPERC = "motivatedperc";
    public static final String KEY_STATUSDATE = "statusdate";
    public static final String KEY_STARTDATE = "startdate";
    public static final String KEY_DAILYSTATUS = "dailystatus";
    public static final String KEY_TASKTRAITS = "tasktraits";
    public static final String KEY_SUBTASK_PRESENT = "subtaskpresent";
    public static final String KEY_SUBTASK_1 = "subtask1";
    public static final String KEY_SUBTASK_2 = "subtask2";
    public static final String KEY_SUBTASK_3 = "subtask3";
    public static final String KEY_SUBTASK_4 = "subtask4";
    public static final String KEY_SUBTASK_5 = "subtask5";
    public static final String KEY_SUBTASK_6 = "subtask6";
    public static final String KEY_SUBTASK_7 = "subtask7";
    public static final String KEY_REMINDERTIME = "remindertime";
    public static final String KEY_TIMELOGGED = "timelogged";

    public static final int MOTIVATEDPERC= 50;
    public static final int DAYSLEFT = 15;
    public static final String ITEMBOUGHT = "";

    private static final String DATABASE_NAME = "motivtracker.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase dbase;


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MOTIVATE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + KEY_TASKNAME
            + " text not null, " + KEY_TASKMOTIVE
            + " text, " + KEY_TODAYSTATUS + " ïnteger, "
            +  KEY_MOTIVATEDPERC + " integer, "
	    + KEY_STATUSDATE + " integer, " + KEY_STARTDATE + " integer, " + KEY_DAILYSTATUS + " integer, " + KEY_TASKTRAITS+ " integer, " +
          KEY_SUBTASK_PRESENT + " integer, " + KEY_SUBTASK_1 + " text, " + KEY_SUBTASK_2 + " text, " +KEY_SUBTASK_3 + " text, " +
            KEY_SUBTASK_4 + " text, " + KEY_SUBTASK_5 + " text, " + KEY_SUBTASK_6 + " text, " + KEY_SUBTASK_7 + " text, " +
    KEY_REMINDERTIME + " integer, " + KEY_TIMELOGGED + " integer );";

    public TaskSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TaskSqlHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOTIVATE);
        onCreate(db);
    }



}   
