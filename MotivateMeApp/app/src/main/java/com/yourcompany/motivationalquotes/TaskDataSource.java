package com.yourcompany.motivationalquotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import android.database.SQLException;


/**
 * Created by ps1 on 3/1/16.
 */
public class TaskDataSource  {

    private SQLiteDatabase database;
    private TaskSqlHelper dbHelper;


    private String[] allColumns = {TaskSqlHelper.COLUMN_ID,TaskSqlHelper.KEY_TASKNAME,TaskSqlHelper.KEY_TASKMOTIVE,TaskSqlHelper.KEY_TODAYSTATUS,
         TaskSqlHelper.KEY_MOTIVATEDPERC, TaskSqlHelper.KEY_STATUSDATE, TaskSqlHelper.KEY_STARTDATE,TaskSqlHelper.KEY_DAILYSTATUS,TaskSqlHelper.KEY_TASKTRAITS,
         TaskSqlHelper.KEY_SUBTASK_PRESENT, TaskSqlHelper.KEY_SUBTASK_1, TaskSqlHelper.KEY_SUBTASK_2,TaskSqlHelper.KEY_SUBTASK_3,TaskSqlHelper.KEY_SUBTASK_4,
            TaskSqlHelper.KEY_SUBTASK_5,TaskSqlHelper.KEY_SUBTASK_6,TaskSqlHelper.KEY_SUBTASK_7,TaskSqlHelper.KEY_REMINDERTIME,TaskSqlHelper.KEY_TIMELOGGED};


    
public TaskDataSource(Context context) {
                dbHelper = new TaskSqlHelper(context);
        }

        public void open() throws SQLException {
                database = dbHelper.getWritableDatabase();
        }

        public void close() {
                dbHelper.close();
        }

        public Task createTask(Task newTask) {
		ContentValues values = new ContentValues();
		values.put(TaskSqlHelper.KEY_TASKNAME, newTask.getTASKNAME());
        values.put(TaskSqlHelper.KEY_TASKMOTIVE, newTask.getTASKMOTIVE());
		values.put(TaskSqlHelper.KEY_TODAYSTATUS, newTask.getTODAYSTATUS());
		values.put(TaskSqlHelper.KEY_MOTIVATEDPERC, newTask.getMOTIVATEDPERC());
		values.put(TaskSqlHelper.KEY_STATUSDATE, newTask.getSTATUSDATE());
		values.put(TaskSqlHelper.KEY_STARTDATE, newTask.getSTARTDATE());
		values.put(TaskSqlHelper.KEY_DAILYSTATUS, newTask.getDAILYSTATUS());
		values.put(TaskSqlHelper.KEY_TASKTRAITS, newTask.getTASKTRAITS());
        values.put(TaskSqlHelper.KEY_SUBTASK_PRESENT, newTask.getSUBTASKPRESENT());
        values.put(TaskSqlHelper.KEY_SUBTASK_1, newTask.getSUBTASK_1());
            values.put(TaskSqlHelper.KEY_SUBTASK_2, newTask.getSUBTASK_2());
            values.put(TaskSqlHelper.KEY_SUBTASK_3, newTask.getSUBTASK_3());
            values.put(TaskSqlHelper.KEY_SUBTASK_4, newTask.getSUBTASK_4());
            values.put(TaskSqlHelper.KEY_SUBTASK_5, newTask.getSUBTASK_5());
            values.put(TaskSqlHelper.KEY_SUBTASK_6, newTask.getSUBTASK_6());
            values.put(TaskSqlHelper.KEY_SUBTASK_7, newTask.getSUBTASK_7());
            values.put(TaskSqlHelper.KEY_REMINDERTIME, newTask.getREMINDERTIME());
            values.put(TaskSqlHelper.KEY_TIMELOGGED, newTask.getTIMELOGGED());



                long insertId = database.insert(TaskSqlHelper.TABLE_MOTIVATE, null, values);
                Cursor cursor = database.query(TaskSqlHelper.TABLE_MOTIVATE,
                                allColumns, TaskSqlHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
                cursor.moveToFirst();
                Task newTaskAdded = cursorToTask(cursor);
                cursor.close();
                return newTaskAdded;
        }

        public void deleteTask(long id) {
                //long id = task.getID();
                System.out.println("Comment deleted with id: " + id);
                database.delete(TaskSqlHelper.TABLE_MOTIVATE, TaskSqlHelper.COLUMN_ID
                        + " = " + id, null);
        }

        public List<Task> getAllTask() {
                List<Task> tasks = new ArrayList<Task>();

                Cursor cursor = database.query(TaskSqlHelper.TABLE_MOTIVATE,
                                allColumns, null, null, null, null, null);

               /* cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                        Task task = cursorToTask(cursor);
                        tasks.add(task);
                        cursor.moveToNext();
                }
		*/
		cursor.moveToLast();
		while(!cursor.isBeforeFirst()) {
			Task task = cursorToTask(cursor);
			tasks.add(task);
			cursor.moveToPrevious();
		}
                // make sure to close the cursor
                cursor.close();
                return tasks;
        }

    public Task getTask(long id) {
        Task task = new Task();
	// Filter results WHERE "id" = 'id'
	String selection = TaskSqlHelper.COLUMN_ID + " = ?";
	String[] selectionArgs = { Long.toString(id) };
	

        Cursor cursor = database.query(TaskSqlHelper.TABLE_MOTIVATE, allColumns,
			selection,selectionArgs,
                null , null,null );

               /* cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                        Task task = cursorToTask(cursor);
                        tasks.add(task);
                        cursor.moveToNext();
                }
		*/
        if (cursor != null) {
            cursor.moveToFirst();
            task = cursorToTask(cursor);
        }



        // make sure to close the cursor
        //cursor.close();
        return task;
    }

    public Task updateTask(long id, Task updtask) {
        Task task = new Task();
        // Filter results WHERE "id" = 'id'
        String selection = TaskSqlHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { Long.toString(id) };

        ContentValues values = new ContentValues();
        values.put(TaskSqlHelper.KEY_TASKNAME, updtask.getTASKNAME());
        values.put(TaskSqlHelper.KEY_TASKMOTIVE, updtask.getTASKMOTIVE());
        values.put(TaskSqlHelper.KEY_TODAYSTATUS, updtask.getTODAYSTATUS());
        values.put(TaskSqlHelper.KEY_MOTIVATEDPERC, updtask.getMOTIVATEDPERC());
        values.put(TaskSqlHelper.KEY_STATUSDATE, updtask.getSTATUSDATE());
        values.put(TaskSqlHelper.KEY_STARTDATE, updtask.getSTARTDATE());
        values.put(TaskSqlHelper.KEY_DAILYSTATUS, updtask.getDAILYSTATUS());
        values.put(TaskSqlHelper.KEY_TASKTRAITS, updtask.getTASKTRAITS());
        values.put(TaskSqlHelper.KEY_SUBTASK_PRESENT, updtask.getSUBTASKPRESENT());
        values.put(TaskSqlHelper.KEY_SUBTASK_1, updtask.getSUBTASK_1());
        values.put(TaskSqlHelper.KEY_SUBTASK_2, updtask.getSUBTASK_2());
        values.put(TaskSqlHelper.KEY_SUBTASK_3, updtask.getSUBTASK_3());
        values.put(TaskSqlHelper.KEY_SUBTASK_4, updtask.getSUBTASK_4());
        values.put(TaskSqlHelper.KEY_SUBTASK_5, updtask.getSUBTASK_5());
        values.put(TaskSqlHelper.KEY_SUBTASK_6, updtask.getSUBTASK_6());
        values.put(TaskSqlHelper.KEY_SUBTASK_7, updtask.getSUBTASK_7());
        values.put(TaskSqlHelper.KEY_REMINDERTIME, updtask.getREMINDERTIME());
        values.put(TaskSqlHelper.KEY_TIMELOGGED, updtask.getTIMELOGGED());




       String taskselection = TaskSqlHelper.COLUMN_ID + " = ?";
        String[] taskselectionArgs = { Long.toString(id) };

           database.update(TaskSqlHelper.TABLE_MOTIVATE, values, taskselection, taskselectionArgs);




        // make sure to close the cursor
        //cursor.close();
        return task;
    }

        private Task cursorToTask(Cursor cursor) {
                Task task = new Task();
                task.setID(cursor.getInt(0));
                task.setTASKNAME(cursor.getString(1));
                task.setTASKMOTIVE(cursor.getString(2));
                task.setTODAYSTATUS(cursor.getInt(3));
                task.setMOTIVATEDPERC(cursor.getInt(4));
                task.setSTATUSDATE(cursor.getLong(5));
                task.setSTARTDATE(cursor.getLong(6));
                task.setDAILYSTATUS(cursor.getLong(7));
                task.setTASKTRAITS(cursor.getLong(8));
                task.setSUBTASKPRESENT(cursor.getInt(9));
                task.setSUBTASK_1(cursor.getString(10));
                task.setSUBTASK_2(cursor.getString(11));
                task.setSUBTASK_3(cursor.getString(12));
                task.setSUBTASK_4(cursor.getString(13));
                task.setSUBTASK_5(cursor.getString(14));
                task.setSUBTASK_6(cursor.getString(15));
                task.setSUBTASK_7(cursor.getString(16));
                task.set_REMINDERTIME(cursor.getInt(17));
                task.set_TIMELOGGED(cursor.getLong(18));
                return task;
        }

// Will be used by the ArrayAdapter in the ListView
      //  @Override
      //  public String toString() {
      //          return taskname;
      //  }
	
}


