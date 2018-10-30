package com.yourcompany.motivationalquotes;

/**
 * Created by ps1 on 4/27/16.
 */
import android.app.Activity;

import java.util.TimeZone;

public class Task extends Activity {
    private long ID;
    private String TASKNAME;
    private String TASKMOTIVE;
    private int MOTIVATEDPERC;
    private long STATUSDATE;
    private long STARTDATE;
    private int TODAYSTATUS;
    private long DAILYSTATUS;
    private long TASKTRAITS;
    private int SUBTASKPRESENT;
    private String SUBTASK_1;
    private String SUBTASK_2;
    private String SUBTASK_3;
    private String SUBTASK_4;
    private String SUBTASK_5;
    private String SUBTASK_6;
    private String SUBTASK_7;
    private int REMINDERTIME;
    private long TIMELOGGED;



    public Task() {
        ID = 0;
        TASKNAME = "";
        TASKMOTIVE = "";
        MOTIVATEDPERC = 50;
        STATUSDATE = 0;
        TODAYSTATUS = 0;
        //STARTDATE = System.currentTimeMillis();
        //Calendar calendar = Calendar.getInstance();
        //currdate = System.currentTimeMillis();
        //STARTDATE = calendar.getTimeInMillis();
        TimeZone tz = TimeZone.getDefault();

        STARTDATE = System.currentTimeMillis();
        //currdate = calendar.getTimeInMillis() ;
        int timeoff = tz.getOffset(STARTDATE);
        STARTDATE = STARTDATE + timeoff;


        DAILYSTATUS =0;
	TASKTRAITS = 0;

        SUBTASKPRESENT = 0;
        SUBTASK_1 ="";
        SUBTASK_2 ="";
        SUBTASK_3 ="";
        SUBTASK_4 ="";
        SUBTASK_5 ="";
        SUBTASK_6 ="";
        SUBTASK_7 ="";

        REMINDERTIME = -1;
        TIMELOGGED = 0;
    }

    public Task(String TaskName,String TaskMotive, int MotivatedPerc, long StatusDate, long StartDate, int TodayStatus, long DailyStatus,
                long TaskTraits, int SubTaskPresent, String SubTask1, String SubTask2, String SubTask3, String SubTask4, String SubTask5,
                String SubTask6,String SubTask7, int ReminderTime, long TimeLogged) {
        TASKNAME = TaskName;
        TASKMOTIVE = TaskMotive;
        MOTIVATEDPERC = MotivatedPerc;
        STATUSDATE = StatusDate;
        STARTDATE = StartDate;
        TODAYSTATUS = TodayStatus;
        DAILYSTATUS = DailyStatus;
	TASKTRAITS = TaskTraits;
        ID=0;
        SUBTASKPRESENT= SubTaskPresent;
        SUBTASK_1 = SubTask1;
        SUBTASK_2 = SubTask2;
        SUBTASK_3 = SubTask3;
        SUBTASK_4 = SubTask4;
        SUBTASK_5 = SubTask5;
        SUBTASK_6 = SubTask6;
        SUBTASK_7 = SubTask7;
        REMINDERTIME = ReminderTime;
        TIMELOGGED = TimeLogged;

    }


    public long getID() {
        return ID;
    }
    public String getTASKNAME() {
        return TASKNAME;
    }
    public String getTASKMOTIVE() {
        return TASKMOTIVE;
    }
    public int getMOTIVATEDPERC() {
        return MOTIVATEDPERC;
    }
    public long getSTATUSDATE() {
        return STATUSDATE;
    }
    
    public long getSTARTDATE() {

        return STARTDATE;
    }
    public int getTODAYSTATUS() {
        return TODAYSTATUS;
    }

    public long getDAILYSTATUS() {

        return DAILYSTATUS;
    }

    public long getTASKTRAITS() {

        return TASKTRAITS;
    }

    public int getSUBTASKPRESENT() {

        return SUBTASKPRESENT;
    }

    public String getSUBTASK_1() {

        return SUBTASK_1;
    }

    public String getSUBTASK_2() {

        return SUBTASK_2;
    }
    public String getSUBTASK_3() {

        return SUBTASK_3;
    }
    public String getSUBTASK_4() {

        return SUBTASK_4;
    }
    public String getSUBTASK_5() {

        return SUBTASK_5;
    }
    public String getSUBTASK_6() {

        return SUBTASK_6;
    }
    public String getSUBTASK_7() {

        return SUBTASK_7;
    }

    public int getREMINDERTIME() {
        return REMINDERTIME;
    }

    public long getTIMELOGGED() {
        return TIMELOGGED;
    }

    public void setID(long id) {
        ID = id;
    }

    public void setTASKNAME(String TaskName) {
        TASKNAME = TaskName;
    }

    public void setTASKMOTIVE(String TaskMotive) {
        TASKMOTIVE = TaskMotive;
    }

    public void setMOTIVATEDPERC(int MotivatedPerc) {
        MOTIVATEDPERC = MotivatedPerc;
    }

    public void setSTATUSDATE(long StatusDate){
        STATUSDATE = StatusDate;

        // Code here for caluclation of number of dayss left

    }

    public void setSTARTDATE(long StartDate){
        STARTDATE = StartDate;

    }

    public void setTODAYSTATUS(int TodayStatus) {
        TODAYSTATUS = TodayStatus;
    }

    public void setDAILYSTATUS(long DailyStatus){
        DAILYSTATUS = DailyStatus;

    }

    public void setTASKTRAITS(long TaskTraits){
        TASKTRAITS = TaskTraits;

    }

    public void setSUBTASKPRESENT(int SubTaskPresent){
        SUBTASKPRESENT = SubTaskPresent;

    }

    public void setSUBTASK_1(String SubTask1){
        SUBTASK_1 = SubTask1;

    }

    public void setSUBTASK_2(String SubTask2){
        SUBTASK_2 = SubTask2;

    }
    public void setSUBTASK_3(String SubTask3){
        SUBTASK_3 = SubTask3;

    }
    public void setSUBTASK_4(String SubTask4){
        SUBTASK_4 = SubTask4;

    }
    public void setSUBTASK_5(String SubTask5){
        SUBTASK_5 = SubTask5;

    }
    public void setSUBTASK_6(String SubTask6){
        SUBTASK_6 = SubTask6;

    }
    public void setSUBTASK_7(String SubTask7){
        SUBTASK_7 = SubTask7;

    }

    public void set_REMINDERTIME(int ReminderTime) {
        REMINDERTIME = ReminderTime;
    }

    public void set_TIMELOGGED(long TimeLogged) {
        TIMELOGGED = TimeLogged;
    }


}

