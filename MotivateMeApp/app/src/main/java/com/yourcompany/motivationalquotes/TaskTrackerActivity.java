package com.yourcompany.motivationalquotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimeZone;

public class TaskTrackerActivity extends AppCompatActivity {

    private long valueStartTime, valuePauseTime,valueStopTime,timediff, totalTime,totalTaskTime;
    private TextView timerTextView, taskTimeView,avgtimer;

    private int timediffhr, timediffmin, timediffsec;

    private int currState;

    private TaskDataSource datasource;
    private Task task,taskold;
    private long taskid;

    private Button startbtn,pausebtn,stopbtn,savebtn,discardbtn;
    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences settings;
    private long savedStartTimer;
    private int currTimerState;
    private long timertaskid;
    private int totaldays;
    private int score1,score2;
    private int avgtime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_tracker);
        Intent intent = getIntent();
        taskid = intent.getLongExtra("id",0);

        timerTextView = (TextView) findViewById(R.id.timerText);
        taskTimeView = (TextView) findViewById(R.id.taskTimeid);
        avgtimer = (TextView) findViewById(R.id.avgtimerid);

        startbtn = (Button) findViewById(R.id.starttracking);
        pausebtn = (Button) findViewById(R.id.pausetracking);
        stopbtn = (Button) findViewById(R.id.stoptracking);
        savebtn = (Button) findViewById(R.id.savetimerid);
        discardbtn = (Button) findViewById(R.id.discardtimerid);

        totalTime = 0;
        currState = 0;

        valueStartTime=0;
        valuePauseTime=0;
        valueStopTime=0;
        timerTextView.setText("Start this goal");

        discardbtn.setVisibility(View.INVISIBLE);
        stopbtn.setVisibility(View.INVISIBLE);
        pausebtn.setVisibility(View.INVISIBLE);
        savebtn.setVisibility(View.INVISIBLE);

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        savedStartTimer = settings.getLong("savedStartTimer", 0);
        currTimerState = settings.getInt("currTimerState",0);
        timertaskid = settings.getLong("timertaskid",0);




        // Open db conncetions
        datasource = new TaskDataSource(this);
        datasource.open();

        task = datasource.getTask(taskid);
        if (task != null) {
            totalTaskTime = task.getTIMELOGGED();
            //totaldays = task.getDAILYSTATUS();
            score1 = mod((int) task.getDAILYSTATUS(), 1000);
            score2 = (int) task.getDAILYSTATUS() / 1000;
            totaldays = score1 + score2;

            timediff = (totalTaskTime) / 1000;

            timediffsec = mod((int) timediff, 60);
            timediffhr = (int) timediff / (60 * 60);
            timediffmin = mod((((int) timediff - timediffsec) / 60), 60);

            taskTimeView.setText("Total Time recorded:" + timediffhr + "hr:" + timediffmin + "min:" + timediffsec + "sec");
            if (totaldays > 0) {
                avgtime = (int) totalTaskTime / totaldays;

                timediff = (avgtime) / 1000;

                timediffsec = mod((int) timediff, 60);
                timediffhr = (int) timediff / (60 * 60);
                timediffmin = mod((((int) timediff - timediffsec) / 60), 60);
                avgtimer.setText("Average Time spent/day:" + timediffhr + "hr:" + timediffmin + "min:" + timediffsec + "sec");

                avgtimer.setVisibility(View.VISIBLE);
            } else {

                avgtimer.setVisibility(View.INVISIBLE);
            }
        }

        // currstate is 1 means prev running
        //deleting this code for now
/*        if (currTimerState == -1) {
            // timer id is same as id for this so this timer was running
            if (timertaskid == taskid) {
                toast("Continuing run");
                currState = 1;
                timerTextView.setText("Task Running. " );
                startbtn.setVisibility(View.INVISIBLE);
                stopbtn.setVisibility(View.VISIBLE);
                discardbtn.setVisibility(View.VISIBLE);
                toast("Saved time is " + savedStartTimer);
                valueStartTime = savedStartTimer;

                //Clear the save state
                    SharedPreferences.Editor editor = settings.edit();
                //    toast("Saving valueStartTime as " + valueStartTime + "currState is " + currState);
                //    editor.putLong("savedStartTime", valueStartTime);
                    editor.putInt("currTimerState", 0);
                    editor.putLong("timertaskid", taskid);

                    // Commit the edits!
                    editor.commit();



                task = datasource.getTask(taskid);
                if (task != null) {
                    totalTaskTime = task.getTIMELOGGED();
                    //totaldays = task.getDAILYSTATUS();
                    score1 = mod((int) task.getDAILYSTATUS(), 1000);
                    score2 = (int) task.getDAILYSTATUS() / 1000;
                    totaldays = score1 + score2;

                    timediff = (totalTaskTime) / 1000;

                    timediffsec = mod((int) timediff, 60);
                    timediffhr = (int) timediff / (60 * 60);
                    timediffmin = mod((((int) timediff - timediffsec) / 60), 60);

                    taskTimeView.setText("Total Time recorded:" + timediffhr + "hr:" + timediffmin + "min:" + timediffsec + "sec");
                    if (totaldays > 0) {
                        avgtime = (int) totalTaskTime / totaldays;

                        timediff = (avgtime) / 1000;

                        timediffsec = mod((int) timediff, 60);
                        timediffhr = (int) timediff / (60 * 60);
                        timediffmin = mod((((int) timediff - timediffsec) / 60), 60);
                        avgtimer.setText("Average Time spent/day:" + timediffhr + "hr:" + timediffmin + "min:" + timediffsec + "sec");

                        avgtimer.setVisibility(View.VISIBLE);
                    } else {

                        avgtimer.setVisibility(View.INVISIBLE);
                    }
                }

            }
            //taskid not match means some other task timer running
            else {
                taskold = datasource.getTask(timertaskid);

                toast("Timer for task " + taskold.getTASKNAME() + " running. Please save/discard that before starting this one");
            }
        }
        else if (currState == 0 ) {
            //toast("taskid:"+ taskid);
            task = datasource.getTask(taskid);
            if (task != null) {
                totalTaskTime = task.getTIMELOGGED();
                //totaldays = task.getDAILYSTATUS();
                score1 = mod((int) task.getDAILYSTATUS(), 1000);
                score2 = (int) task.getDAILYSTATUS() / 1000;
                totaldays = score1 + score2;

                timediff = (totalTaskTime) / 1000;

                timediffsec = mod((int) timediff, 60);
                timediffhr = (int) timediff / (60 * 60);
                timediffmin = mod((((int) timediff - timediffsec) / 60), 60);

                taskTimeView.setText("Total Time recorded:" + timediffhr + "hr:" + timediffmin + "min:" + timediffsec + "sec");
                if (totaldays > 0) {
                    avgtime = (int) totalTaskTime / totaldays;

                    timediff = (avgtime) / 1000;

                    timediffsec = mod((int) timediff, 60);
                    timediffhr = (int) timediff / (60 * 60);
                    timediffmin = mod((((int) timediff - timediffsec) / 60), 60);
                    avgtimer.setText("Average Time spent/day:" + timediffhr + "hr:" + timediffmin + "min:" + timediffsec + "sec");

                    avgtimer.setVisibility(View.VISIBLE);
                } else {

                    avgtimer.setVisibility(View.INVISIBLE);
                }
            }
        }
        else {
            toast ("Prev timer state is "+ currTimerState);
        }
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*
    @Override
    public void onPause() {
        super.onPause();
        if ((currState == 1)) {
            saveTimerState();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        savedStartTimer = settings.getLong("savedStartTimer", 0);
        currTimerState = settings.getInt("currTimerState",0);
        timertaskid = settings.getLong("timertaskid",0);

        if (currTimerState == 1) {
            valueStartTime = savedStartTimer;
            toast("Saved Value Start timer is " + valueStartTime);
            currState = 1;
            //Clear the saved state

                SharedPreferences.Editor editor = settings.edit();
                //toast("Saving valueStartTime as " + valueStartTime + "currState is " + currState);
                //editor.putLong("savedStartTime", valueStartTime);
                editor.putInt("currTimerState", 0);
                editor.putLong("timertaskid",taskid);

                // Commit the edits!
                editor.commit();


        }

    }

*/

    public void starttracking(View view){

        // Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //currdate = System.currentTimeMillis();
        TimeZone tz = TimeZone.getDefault();

        //currdate = System.currentTimeMillis();
        //currdate = calendar.getTimeInMillis() ;
        int timeoff = tz.getOffset(valueStartTime);
        //valueStartTime = valueStartTime + timeoff;

        if (currState == 0 ) { // Timer starting
  //          totalTime = 0;
            valueStartTime = System.currentTimeMillis();
            valuePauseTime = 0;
            valueStopTime = 0;

        }
        else if (currState == 1) { //Use again pressed start
//            totalTime = 0;
            valueStartTime = System.currentTimeMillis();
            valuePauseTime = 0;
            valueStopTime = 0;

        }
        else if (currState == 2) {//starting from pause

            valueStartTime = System.currentTimeMillis();

            valuePauseTime = 0;
            valueStopTime = 0;

        }
        else if (currState == 3) {//starting from stop
            totalTime = 0; //timer is already updated in db at stop
            valueStartTime = System.currentTimeMillis();

            valuePauseTime = 0;
            valueStopTime = 0;

        }
        else {//unknown

        }

        currState = 1;//start state
        timerTextView.setText("Task Running. " );
        startbtn.setVisibility(View.INVISIBLE);
        stopbtn.setVisibility(View.VISIBLE);
        discardbtn.setVisibility(View.VISIBLE);

       // toast("Saving valueStartTime as " + valueStartTime + "currState is " + currState);
    }

    public void pausetracking(View view) {
        valuePauseTime = System.currentTimeMillis();
        // Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //currdate = System.currentTimeMillis();
        TimeZone tz = TimeZone.getDefault();

        //currdate = System.currentTimeMillis();
        //currdate = calendar.getTimeInMillis() ;
        int timeoff = tz.getOffset(valueStartTime);
        //valueStartTime = valueStartTime + timeoff;

        if (currState == 1 ) {//coming from start
            totalTime = totalTime + (valuePauseTime- valueStartTime);

        }
        else if (currState == 2) {//coming from pause
        }
        else if (currState==3) {//coming from stop
        }
        else {

        }
        //totalTime = (valuePauseTime - valueStartTime);


        timediff = (totalTime)/1000;

        timediffsec = mod((int)timediff, 60);
        timediffhr = (int)timediff/(60*60);
        timediffmin = mod(((int)timediff - timediffsec)/60,60);


        currState = 2;

        timerTextView.setText("Run Time:" +timediffhr +"hr:"+timediffmin+"min:"+timediffsec+"sec");
        startbtn.setVisibility(View.VISIBLE);
        stopbtn.setVisibility(View.VISIBLE);
        pausebtn.setVisibility(View.INVISIBLE);
        savebtn.setVisibility(View.INVISIBLE);

    }

    public void stoptracking (View view) {
        valueStopTime = System.currentTimeMillis();
        // Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //currdate = System.currentTimeMillis();
        TimeZone tz = TimeZone.getDefault();

        //currdate = System.currentTimeMillis();
        //currdate = calendar.getTimeInMillis() ;
        int timeoff = tz.getOffset(valueStopTime);
        //valueStartTime = valueStartTime + timeoff;

        //toast("total time :" + totalTime);
        if (currState == 1) {//from start

            totalTime = totalTime + (valueStopTime-valueStartTime);
        }
        else if (currState == 2) {//coming from pause
            totalTime = totalTime ;
        }
        else if (currState == 3) {//from stop
        }
        else {

        }

        timediff = totalTime/1000;

        timediffsec = mod((int)timediff, 60);
        timediffhr = (int)timediff/(60*60);
        timediffmin = mod(((int)timediff - timediffsec)/60,60);

        timerTextView.setText("Run Time:" +timediffhr +"hr:"+timediffmin+"min:"+timediffsec+"sec");


        //toast("totalTaskTime:" + totalTaskTime);

        /*
        task.set_TIMELOGGED(totalTaskTime);
        datasource.updateTask(taskid, task);

        task = datasource.getTask(taskid);

        if (task != null) {
            totalTaskTime = task.getTIMELOGGED();

            timediff = (totalTaskTime)/1000;

            timediffsec = mod((int)timediff, 60);
            timediffhr = (int)timediff/(60*60);
            timediffmin = mod((((int) timediff - timediffsec) / 60), 60);

            taskTimeView.setText("Total Time previously recorded:" +timediffhr+"hr:"+timediffmin+"min:"+timediffsec+"sec");
        }
*/
        currState = 3;
        startbtn.setVisibility(View.VISIBLE);
        stopbtn.setVisibility(View.INVISIBLE);
        pausebtn.setVisibility(View.INVISIBLE);
        savebtn.setVisibility(View.VISIBLE);
        discardbtn.setVisibility(View.VISIBLE);

    }


    public void savetime (View view) {
        //update db
        totalTaskTime = totalTaskTime + totalTime;

        task.set_TIMELOGGED(totalTaskTime);
        datasource.updateTask(taskid, task);

        task = datasource.getTask(taskid);

        if (task != null) {
            totalTaskTime = task.getTIMELOGGED();

            timediff = (totalTaskTime)/1000;

            timediffsec = mod((int)timediff, 60);
            timediffhr = (int)timediff/(60*60);
            timediffmin = mod((((int) timediff - timediffsec) / 60), 60);


            taskTimeView.setText("Total Time recorded:" +timediffhr+"hr:"+timediffmin+"min:"+timediffsec+"sec");

            score1 = mod((int) task.getDAILYSTATUS(), 1000);
            score2 = (int) task.getDAILYSTATUS()/1000;
            totaldays = score1 +score2;

            if (totaldays>0) {
                avgtime = (int)totalTaskTime/totaldays;

                timediff = (avgtime)/1000;

                timediffsec = mod((int)timediff, 60);
                timediffhr = (int)timediff/(60*60);
                timediffmin = mod((((int) timediff - timediffsec) / 60), 60);
                avgtimer.setText("Average Time spent/day:" +timediffhr+"hr:"+timediffmin+"min:"+timediffsec+"sec");

                avgtimer.setVisibility(View.VISIBLE);
            }
            else {

                avgtimer.setVisibility(View.INVISIBLE);
            }
        }

        totalTime = 0;

        currState = 0;
        startbtn.setVisibility(View.VISIBLE);
        stopbtn.setVisibility(View.INVISIBLE);
        pausebtn.setVisibility(View.INVISIBLE);
        savebtn.setVisibility(View.INVISIBLE);
        discardbtn.setVisibility(View.INVISIBLE);


    }

    public void discardtime (View view) {
        totalTime = 0;

        valueStartTime = 0;
        valuePauseTime =0;
        valueStopTime=0;
        currState = 0;
        discardbtn.setVisibility(View.INVISIBLE);
        savebtn.setVisibility(View.INVISIBLE);
        startbtn.setVisibility(View.VISIBLE);
        stopbtn.setVisibility(View.INVISIBLE);
        pausebtn.setVisibility(View.INVISIBLE);
    }
    public void gotohome (View view){

       if (currState == 1) {
//           alert("Timer for this task is running. Do you want to :");
//           saveTimerState();

       }
        if (datasource != null) {
            datasource.close();
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);



    }

    void gotomain() {
        if (datasource != null) {
            datasource.close();
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);

    }

    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }


    public void toast (String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    } // end toast



    void alert(String message) {
        boolean val=true;
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        //toast("Please do rate us. Good ratings would motivate us to make this app better ");
                        saveTimerState();
                        gotomain();
                    }
                })
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                           gotomain();

                        // User cancelled the dialog
                    }
                });
        //bld.setCancelable(val);
        bld.create().show();
    }

    void saveTimerState() {

        SharedPreferences.Editor editor = settings.edit();
      //  toast("Saving valueStartTime as " + valueStartTime + "currState is " + currState);
        editor.putLong("savedStartTime", valueStartTime);
        editor.putInt("currTimerState", currState);
        editor.putLong("timertaskid",taskid);

        // Commit the edits!
        editor.commit();

    }
}
