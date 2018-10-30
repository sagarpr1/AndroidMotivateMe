package com.yourcompany.motivationalquotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimeZone;

public class ReportingActivity extends AppCompatActivity {

    private TaskDataSource datasource;
    private int todaystatus, motivatedperc, taskday, daysleft, prevstatus, prevstatushr;
    private Task task;
    private long taskid;
    private long currdate, startdate, statusdate;
    private SwitchCompat switchbtn;
    private Button submitbtn,revivebtn,deletetaskbtn, doitagainbtn;
    private TextView statusrpttxt;
    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences settings;
    private int pointsearned, potionpurchased, honesty;
    private ImageView imagewhite,imageblack;
    private TextView tv1,tv2;
    private TextView textViewGoal,textViewGoal2,textView19,textView34,textView20,textView23motivperc,textView26,textView35,textView38;
    private int score1,score2, runningcurrday, modday;
    private String subtaskname, taskmotive;
    private int subTaskPresent;
    private CheckBox chktaskcomp, chktasknotcomp, chktv,chkfb,chklowenth, chklosefocus,chktough, chkover, chkenv, chktoomany,chkothers,chklazy;
  //  private ListView listviewdistract;

    String[] distractionoptions ={"TV & Games","Facebook Whatsapp","Friends","Too many overheads","Low enthusiasm", "Too tough task",
    "Negative Environment", "Too many task", "Öthers"};

    private int opttv, optfb, optfrnds,optoverheads,optlowenth,opttough,optenv,optothers,optmanytask,optlazy,distlist;

    private int sharedstatus;

    private int upddifferene , difference;
    private String taskname;

    private static final int MIN_MOTIV_THRES = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        Intent intent = getIntent();
        taskid = intent.getLongExtra("id",0);

        // Restore preferences
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = settings.getString("name", "");
        String first = settings.getString("first", "yes");
        pointsearned = settings.getInt("pointsearned", 1000);
        potionpurchased = settings.getInt("potionpurchased", 0);
        honesty = settings.getInt("honesty",0);

        // preference for distractors
        opttv = settings.getInt("opttv",0);
        optfb = settings.getInt("optfb",0);
        optfrnds = settings.getInt("optfrnds",0);
        optoverheads = settings.getInt("optoverheads",0);
        opttough = settings.getInt("opttough",0);
        optlowenth = settings.getInt("optlowenth",0);
        optenv = settings.getInt("optenv",0);
        optothers = settings.getInt("optothers",0);
        optmanytask = settings.getInt("optmanytask",0);
        optlazy = settings.getInt("optlazy",0);
        distlist = settings.getInt("distlist",0);

        sharedstatus = settings.getInt("sharedstatus",0);

        //view for distract reporting
        //listviewdistract = (ListView) findViewById(R.id.listViewdistract);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item
        //        , distractionoptions);
        //listviewdistract.setAdapter(adapter);

        /*listviewdistract.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        listviewdistract.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                //String msg = "Position:" + position + "id:" + id;
                //toast(msg);

//                Intent intent = new Intent(this, ReportingActivity.class);
                //               startActivity(intent);
                                toast("position is " + position);
                if (position == 0){
                    toast("Position 1 means TV");
                    opttv++;
                }
                else if (position == 1){
                    toast("Position 1 means FB");
                    optfb++;

                }
            }
        });
*/



// buttons
        revivebtn = (Button) findViewById(R.id.revivebtn);
        deletetaskbtn = (Button) findViewById(R.id.deletetaskbtn);

        //Goal textview
        textViewGoal = (TextView) findViewById(R.id.textViewGoal);
        textViewGoal2 = (TextView) findViewById(R.id.textViewGoal2);
        textView19 = (TextView) findViewById(R.id.textView19);
        textView34 = (TextView) findViewById(R.id.textView34);
        textView20 = (TextView) findViewById(R.id.textView20);
        textView23motivperc = (TextView) findViewById(R.id.textView23motivperc);
        textView26 = (TextView) findViewById(R.id.textView26);
        textView35 = (TextView) findViewById(R.id.textView35);
        textView38 = (TextView) findViewById(R.id.textView38);


        chktv = (CheckBox) findViewById(R.id.tvdistcheckBox);
        chkfb = (CheckBox) findViewById(R.id.fbdistchkbox);
        chklazy = (CheckBox) findViewById(R.id.lazychkbox);
        chkothers = (CheckBox) findViewById(R.id.otherchkbox);
        chkenv = (CheckBox) findViewById(R.id.envchkbox);
        chklosefocus = (CheckBox) findViewById(R.id.focuschkbox);

        doitagainbtn = (Button) findViewById(R.id.button4);
        //switchbtn = (SwitchCompat) findViewById(R.id.switch1);
        chktaskcomp = (CheckBox) findViewById(R.id.taskcomp);
        chktasknotcomp = (CheckBox) findViewById(R.id.tasknotcomp);

        submitbtn = (Button) findViewById(R.id.taskupdatebtn);
        statusrpttxt = (TextView) findViewById(R.id.statusrpttxtid);


        // By default distractions are not there
        chktv.setVisibility(View.GONE);
        chkfb.setVisibility(View.GONE);
        chklazy.setVisibility(View.GONE);
        chkothers.setVisibility(View.GONE);
        chkenv.setVisibility(View.GONE);
        chklosefocus.setVisibility(View.GONE);


        // By default no goal message
        textViewGoal.setVisibility(View.GONE);
        textViewGoal2.setVisibility(View.GONE);

        // Open db conncetions
        datasource = new TaskDataSource(this);
        datasource.open();

        task = datasource.getTask(taskid);
        if (task != null) {
            taskname = task.getTASKNAME();
            textView35.setText("Task:" + taskname);
            taskmotive = task.getTASKMOTIVE();
            textView38.setText(taskmotive);

            //long taskid = task.getID();
//	    int daysleft = task.getDAYSLEFT();

            // find goals to see current day, use subtask options
            score1 = mod((int) task.getDAILYSTATUS(), 1000);
            score2 = (int) task.getDAILYSTATUS()/1000;
            subTaskPresent = task.getSUBTASKPRESENT();




            //Calendar calendar = Calendar.getInstance(TimeZone.getDefault());




            TimeZone tz = TimeZone.getDefault();

            currdate = System.currentTimeMillis();
            //currdate = calendar.getTimeInMillis() ;
            int timeoff = tz.getOffset(currdate);

            currdate = currdate + timeoff;
//            toast ("Öffset is  " + timeoff);

            startdate = task.getSTARTDATE();
            statusdate = task.getSTATUSDATE();

            // if currdate is less than startdateT means task is renewed. assumption no problem with clocks.
            taskday =  (int) ((int)(currdate/(24*60*60*1000)) - (int)(startdate/(24 * 60 * 60 * 1000)));
           // int taskday1 = (int) ((currdate- startdate)/(24 * 60 * 60 * 1000));


            //toast ("taskday is " + taskday);
            //toast ("taskday1 is " + taskday1);

            daysleft = (int) (15 - taskday);

            upddifferene = (int) ((int)(currdate/(24*60*60*1000)) - (int)(statusdate/(24 * 60 * 60 * 1000)));
            //int upddifferene1 = (int) ((currdate - statusdate)/(24 * 60 * 60 * 1000));

            //toast ("Updat diff is " + upddifferene);
            //toast ("Updat diff  1 is " + upddifferene1);

            // if status updated for today reduced days left
            if (upddifferene == 0 ) {
                if (daysleft>0) {
                    daysleft--;
                }
            }

            score1 = mod((int) task.getDAILYSTATUS(),1000);
            score2 = (int) task.getDAILYSTATUS()/1000;

            runningcurrday = score1 + score2;

            // if task is running and status updated today
            if (runningcurrday > 0) {
                if (upddifferene==0) {
                    runningcurrday--;
                }
            }


            modday = mod (runningcurrday,7);

            if (subTaskPresent == 1) {
                if (modday == 0) {
                    subtaskname =  task.getSUBTASK_1();

                }
                else if (modday == 1){
                    subtaskname =  task.getSUBTASK_2();
                }
                else if (modday == 2){
                    subtaskname =  task.getSUBTASK_3();
                }
                else if (modday == 3){
                    subtaskname =  task.getSUBTASK_4();
                }
                else if (modday == 4){
                    subtaskname =  task.getSUBTASK_5();
                }
                else if (modday == 5){
                    subtaskname =  task.getSUBTASK_6();
                }
                else if (modday == 6){
                    subtaskname =  task.getSUBTASK_7();
                }
                else {
                    subtaskname = "Today's Sub Task: None"  ;

                }

                if ((subtaskname != null) || (subtaskname != " ")) {
                    textView38.setText("Today's Sub Task: "+ subtaskname);
                }
                else {
                    //tasksubName.setVisibility(View.GONE);
                    textView38.setText("Today's Sub Task: Rest ");
                }
            }
            // if no subtask set visibility to gone
            else {
                textView38.setVisibility(View.GONE);
            }


            //set goal score in views
            textView19.setText("Score:" + score1);
            textView34.setText("Days Left:" + daysleft);
            textView20.setText("Score:" + score2);


            if (statusdate == 0) { // first time
                prevstatushr = 0;
                prevstatus = 0;
            } else {
                prevstatushr = (int) (((currdate - statusdate) / (60 * 60 * 1000)));
               // prevstatus = (int) (((currdate - statusdate) / (24 * 60 * 60 * 1000)));
                prevstatus = upddifferene;
            }



            //switchbtn.setShowText(true);
            //switchbtn.setSplitTrack(true);
            //switchbtn.setTextOn("Done");
            //switchbtn.setTextOff("Missed");


            doitagainbtn.setVisibility(View.GONE);

// Status update of task
            // if task is finished before today
            if ((daysleft <= 0) && (upddifferene >0)) {
                chktaskcomp.setVisibility(View.GONE);
                chktasknotcomp.setVisibility(View.GONE);
                submitbtn.setVisibility(View.GONE);
                textView26.setVisibility(View.GONE);
                //  listviewdistract.setVisibility(View.GONE);
                chktv.setVisibility(View.GONE);
                chkfb.setVisibility(View.GONE);
                chklazy.setVisibility(View.GONE);
                chkothers.setVisibility(View.GONE);
                chkenv.setVisibility(View.GONE);
                chklosefocus.setVisibility(View.GONE);



//                toast("Visible");
                doitagainbtn.setVisibility(View.VISIBLE);

              //  toast("Rescheduled");

  //              submitbtn.setVisibility(View.GONE);
                statusrpttxt.setText("Status for task is updated for the day");
            }

            // If daysleft are between 1 to 1 .
            else if ((daysleft < 0 || daysleft > 15)) {
               // toast("Duration complete. daysleft " + daysleft);
                // if task is successful, allow user to delete
                //switchbtn.setVisibility(View.GONE);

                chktaskcomp.setVisibility(View.GONE);
                chktasknotcomp.setVisibility(View.GONE);
                submitbtn.setVisibility(View.GONE);
                textView26.setVisibility(View.GONE);
              //  listviewdistract.setVisibility(View.GONE);
                chktv.setVisibility(View.GONE);
                chkfb.setVisibility(View.GONE);
                chklazy.setVisibility(View.GONE);
                chkothers.setVisibility(View.GONE);
                chkenv.setVisibility(View.GONE);
                chklosefocus.setVisibility(View.GONE);



               // toast("Visible");
                doitagainbtn.setVisibility(View.VISIBLE);


                statusrpttxt.setText("Task is over. You can restart this to continue");

            }

            else {
              //  toast("Running task");

                // Only if motivation > MIN_MOTIV_THRES
                if (task.getMOTIVATEDPERC() > MIN_MOTIV_THRES) {
                    // If status not updated today
                    //statusdate == 0 means first time, prevstatus >0 means from 2nd onwards
                    if (statusdate == 0 || prevstatus > 0) {

                        chktaskcomp.setVisibility(View.VISIBLE);
                        chktasknotcomp.setVisibility(View.VISIBLE);



                    } else { // if status is already updated for the day
                        // disable status update switch if user updated it today (updated by submit button fn)
                        textView26.setVisibility(View.GONE);
            //            listviewdistract.setVisibility(View.GONE);

                        //switchbtn.setVisibility(View.GONE);
                        chktaskcomp.setVisibility(View.GONE);
                        chktasknotcomp.setVisibility(View.GONE);
                        chktv.setVisibility(View.GONE);
                        chkfb.setVisibility(View.GONE);
                        chklazy.setVisibility(View.GONE);
                        chkothers.setVisibility(View.GONE);
                        chkenv.setVisibility(View.GONE);
                        chklosefocus.setVisibility(View.GONE);



                        submitbtn.setVisibility(View.GONE);
                        statusrpttxt.setText("Status for task is updated for the day");
                    }
                    //       toast("Duration left"+ daysleft);
                }
                // if motivation < MIN_MOTIV_THRES
                else {
                    textView26.setVisibility(View.GONE);
         //           listviewdistract.setVisibility(View.GONE);

                    //switchbtn.setVisibility(View.GONE);
                    chktaskcomp.setVisibility(View.GONE);
                    chktasknotcomp.setVisibility(View.GONE);

                    chktv.setVisibility(View.GONE);
                    chkfb.setVisibility(View.GONE);
                    chklazy.setVisibility(View.GONE);
                    chkothers.setVisibility(View.GONE);
                    chkenv.setVisibility(View.GONE);
                    chklosefocus.setVisibility(View.GONE);



                    submitbtn.setVisibility(View.GONE);
                    //statusrpttxt.setText("Motivation low. Revive to continue.Remember you want to defeat: "+ task.getTASKMOTIVE() );
                    String taskmot = task.getTASKMOTIVE();
                    if ((taskmot != null) || (taskmot !="") ) {
                        statusrpttxt.setText("Motivation low. Revive to continue");
                    }
                    else {
                        statusrpttxt.setText("Motivation low. Revive to continue.");

                    }

                }
            }


            updateConfid();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reporting, menu);
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

    public void toast (String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    } // end toast


    // do it again
    // continue with prev motivation, start from tomorrow if done today
    public void doitagain (View v) {
        //motivatedperc = 50;
        // if updated today set startdate to nextday
        if (upddifferene == 0) {
            startdate = ((int)(currdate /24*60*60*1000 ) + 1 ) * 24*60*60*1000;
        }
        else {
            startdate = currdate;
        }

        //task.setMOTIVATEDPERC(motivatedperc);
        task.setSTARTDATE(startdate);
        //task.setSTATUSDATE(0);
        //task traits limited to 15 so reset, value over 15 days
        task.setTASKTRAITS(0);

        datasource.updateTask(taskid, task);
        Intent intent = new Intent(this, MainActivity.class);
        datasource.close();
        startActivity(intent);


    }

    public void updatetask(View v){

        long dailystatus , tasktraits;
        int currtaskpersistence, currtaskhonesty, currtaskdiscipline, currtasksincerety;

        //Load tasktraits
        tasktraits = task.getTASKTRAITS();

        //Seperate each trait
        currtasksincerety = (int) (mod((int) tasktraits,100));
        currtaskdiscipline = (int)  tasktraits/100;
//        currtaskhonesty = (int) (tasktraits/100);
        //currtaskhonesty = (int) (mod(((int) tasktraits),1000))/100;
        //currtaskpersistence =(int) (tasktraits/1000);

       // toast("Loaded tasktraits is  " + tasktraits);
       // toast("Loaded  sincerety is " + currtasksincerety + "disc " + currtaskdiscipline);




        if (todaystatus == 1) {
            if(motivatedperc <= 90) {
            motivatedperc = task.getMOTIVATEDPERC() + 10;
            }
            pointsearned= pointsearned + 10;
            score1++;
            textViewGoal.setVisibility(View.INVISIBLE);
            textViewGoal2.setVisibility(View.VISIBLE);

        }
        else {
            if(motivatedperc >= 10) {
                motivatedperc = task.getMOTIVATEDPERC() - 10;
            }

            if (pointsearned >= 10) {
                pointsearned = pointsearned - 10;
            }
            //honestly said that work not done so honest
            //currtaskhonesty = 1;
            honesty = 1;
            score2++;
            textViewGoal.setVisibility(View.VISIBLE);
            textViewGoal2.setVisibility(View.INVISIBLE);

            //code to check if this checkbox is checked!

            if(chktv.isChecked()){
                opttv++;
            }

            if(chkfb.isChecked()){
                optfb++;
            }

            if(chklazy.isChecked()){
                optlazy++;
            }
            if(chkothers.isChecked()){
                optothers++;
            }

            if(chklosefocus.isChecked()){
                optenv++;
            }

            if (!(chktv.isChecked()) && !(chkfb.isChecked()) && !(chklazy.isChecked()) && !(chkothers.isChecked()) && !(chklosefocus.isChecked())){
                distlist = -1;
            }

        }
        // decrease number of days left
        daysleft--;


     //   dailystatus = task.getDAILYSTATUS() + (1<<taskday) * todaystatus;
	//int test = 1<<taskday;
	//toast("test is "+ test);

      //  toast ("Dailystatus is " + dailystatus);
      //  toast ("shift is " + (1<<taskday) + "day is " + taskday);
      //  toast ("tasktraits is " + tasktraits);

      //  String dailystatusbin = "Dailystatus:" + (int)(dailystatus<<1);

        //dailystatus is goal score; score1 + 100*score2


        //Update dailstatus score

        if ((score1>999)|| (score2>999)){
            if (score1>score2) {
                score1 = score1 - score2;
                score2 = 0;
            }
            else {
                score1 = 0;
                score2 = score2-score1;
            }

        }
        dailystatus = (long)(score1 + 1000* score2);

        // This code is executed when switch is active (i.e. days >=0)
        // If reporting happened yesterday also
       // toast ("Prevstatus is " + prevstatus + "prevstatushr is " + prevstatushr);
        if(prevstatus == 1) {

            currtasksincerety++;
            //pointsearned =pointsearned + 10;

            if(currtasksincerety>=15) {
                currtasksincerety=15;

            }

            //if reporting yesterday at similar time
            if ((prevstatushr > 22) && (prevstatushr < 26) ) {
                //toast("Bravo you are time disciplined" + prevstatushr);
                currtaskdiscipline++;

                if(currtaskdiscipline>=15) {
                    currtaskdiscipline=15;

                }
                pointsearned = pointsearned + 10;
            }
            //if reporting at diff time
            else {
                //toast("Good you are tracking your task regularly" + prevstatushr);
                //currtaskdiscipline--;
                if (currtaskdiscipline<0) {
                    currtaskdiscipline = 0;
                }

            }
        }
        //if reporting before yesterday
        else {
            // This should not be the first time of update
            if (statusdate>0) {
                //if (prevstatus > 3) {
               //     toast("Your sincerety for this task is low" + "Days left :" + daysleft + "Prev:" + prevstatus);
                    if (pointsearned >=10) {
                        pointsearned = pointsearned - 10;
                    }
                 //   currtaskdiscipline--;
                 //   currtasksincerety--;


                    if(currtaskdiscipline<=0) {
                        currtaskdiscipline=0;
                    }

                    if(currtasksincerety<=0) {
                        currtasksincerety=0;
                    }
                //}
                //else {
             //       toast("More sincerety in this task will help"+ "Days left :" + daysleft + "Prev:" + prevstatus);
                //    currtaskdiscipline--;

                  //  if(currtaskdiscipline<=0) {
                   //     currtaskdiscipline=0;
                   // }

        //        }
            }

        }

       // toast("tasktraits" + currtasksincerety + "disc " + currtaskdiscipline + "focus " + currtaskhonesty);

        /*if (taskday>0){
            currtaskdiscipline = (currtaskdiscipline * 9) / taskday;
            currtasksincerety = (currtasksincerety * 9) / taskday;
        }
        */

       // toast("Final sincerety is " + currtasksincerety + "disc is " + currtaskdiscipline);
        tasktraits = (1 * currtasksincerety + 100*currtaskdiscipline  ) ;
      //  toast("tasktraits is now " + tasktraits);
        //toast("Today Status is "+ todaystatus + "and motivation now is " + motivatedperc);
        task.setMOTIVATEDPERC(motivatedperc);
        task.setTODAYSTATUS(todaystatus);
        task.setSTATUSDATE(currdate);
        task.setDAILYSTATUS(dailystatus);
        task.setTASKTRAITS(tasktraits);

        datasource.updateTask(taskid, task);

        //remove goal message
        //textViewGoal.setVisibility(View.GONE);
        //textViewGoal2.setVisibility(View.GONE);




        // remove submit and list buttons
        //switchbtn.setVisibility(View.GONE);
        chktaskcomp.setVisibility(View.GONE);
        chktasknotcomp.setVisibility(View.GONE);

        submitbtn.setVisibility(View.GONE);
        textView26.setVisibility(View.GONE);
        chktv.setVisibility(View.GONE);
        chkfb.setVisibility(View.GONE);
        chklazy.setVisibility(View.GONE);
        chkothers.setVisibility(View.GONE);
        chkenv.setVisibility(View.GONE);
        chklosefocus.setVisibility(View.GONE);

        //      listviewdistract.setVisibility(View.GONE);

        statusrpttxt.setText("Status for task is updated for the day");
        //toast("Updated");
        //Intent intent = new Intent(this, TaskStatus.class);
        //startActivity(intent);

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("pointsearned", pointsearned);

        editor.putInt("honesty", honesty);

        //Update distractions
        // preference for distractors
        editor.putInt("opttv", opttv);
        editor.putInt("optfb", optfb);
        editor.putInt("optfrnds", optfrnds);
        editor.putInt("optoverheads",optoverheads);
        editor.putInt("opttough",opttough);
        editor.putInt("optlowenth",optlowenth);
        editor.putInt("optenv",optenv);
        editor.putInt("optothers",optothers);
        editor.putInt("optmanytask",optmanytask);
        editor.putInt("optlazy",optlazy);
        editor.putInt("distlist", distlist);

        // Commit the edits!
        editor.commit();

	updateConfid();
    }

    public void gotohome(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        datasource.close();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);


    }

    public void gotostatus(View v) {
        Intent intent = new Intent(this, TaskStatus.class);
        datasource.close();
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

    public void revivebtn(View v) {
        Intent intent = new Intent(this, IapActivity.class);
        intent.putExtra("id", taskid);
        intent.putExtra("motivation", motivatedperc);
        datasource.close();
        startActivity(intent);
    }

    public void deletetask(View v) {

        alert("Are you sure? This will delete the complete task", v);

    }

    public void taskddeletion() {
        datasource.deleteTask(taskid);
        Intent intent = new Intent(this, TaskStatus.class);
        intent.putExtra("id", taskid);
        startActivity(intent);

    }

    void alert(String message, View v) {
        boolean val=true;
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message)
		 .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
                       taskddeletion();
	              }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {

                       // User cancelled the dialog
                   }
               });
        //bld.setCancelable(val);
        bld.create().show();
    }


    void updateConfid () {
	    //  set the image display black and white
           // imagewhite = (ImageView) findViewById(R.id.imageViewRpt);
           // imageblack = (ImageView) findViewById(R.id.imageView2Rpt);

            tv1 = (TextView) findViewById(R.id.textView8);
            tv2 = (TextView) findViewById(R.id.textView9);

            motivatedperc = task.getMOTIVATEDPERC();
            float motiv  =  (float) motivatedperc;
            float demotiv = 100 - motiv;


            TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,200,motiv);
            tv1.setLayoutParams(lParams);
            TableRow.LayoutParams lParams2 = new TableRow.LayoutParams(0,200,demotiv);
            tv2.setLayoutParams(lParams2);
            //imagewhite.setLayoutParams();

            textView23motivperc.setText("Motivation: " + motivatedperc + "%");
            textView19.setText("You:" + score1);
            textView20.setText("Distractions:" + score2);
            if (daysleft <= 0 || daysleft > 15) {
                textView34.setText("Task Over");
            }
            else {
                textView34.setText("Days Left:" + daysleft);

            }

            // revive task visibility

            if (motivatedperc > MIN_MOTIV_THRES) {
                revivebtn.setVisibility(View.GONE);
            }
            else {
                revivebtn.setVisibility(View.VISIBLE);

            }

    }

    public void showscoretgl(View v) {
    TableLayout tbl = (TableLayout) findViewById(R.id.tbl);
        if (tbl.getVisibility() == View.VISIBLE) {
            tbl.setVisibility(View.GONE);
        }
        else {
            tbl.setVisibility(View.VISIBLE);
        }
    }

    public void taskdonechk (View v) {
        todaystatus = 1;
        chktasknotcomp.setChecked(false);
        textView26.setVisibility(View.GONE);
        chktv.setVisibility(View.GONE);
        chkfb.setVisibility(View.GONE);
        chklazy.setVisibility(View.GONE);
        chkothers.setVisibility(View.GONE);
        chkenv.setVisibility(View.GONE);
        chklosefocus.setVisibility(View.GONE);


    }

    public void tasknotdonechk (View v) {
        todaystatus = 0;
        chktaskcomp.setChecked(false);
        textView26.setVisibility(View.VISIBLE);
        chktv.setVisibility(View.VISIBLE);
        chkfb.setVisibility(View.VISIBLE);
        chklazy.setVisibility(View.VISIBLE);
        chkothers.setVisibility(View.VISIBLE);
        chkenv.setVisibility(View.GONE);
        chklosefocus.setVisibility(View.VISIBLE);


    }

    public void shareresult(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody="";
        int totalscore = score1+ score2;

        //sharedstatus = 0;
        // starting on task
        if (totalscore == 0) {
            shareBody = "Goal Tracker: I am starting on a new task: " + taskname + ". Will update my progress next week";
            sharedstatus = 1;
        }
        // task over
        else if (daysleft <= 0) {
              shareBody = "Goal Tracker: My final status for " + taskname + ", score Days done:"+ score1 +" vs Days not done:" + score2;
              sharedstatus = 100;

        }
        else if ((daysleft > 0 ) && (daysleft <15)) {
            shareBody = "Goal Tracker: My status for " + taskname + ", after "+ totalscore + "days is score Days done:"+ score1 +" vs Days not done:" + score2;
            sharedstatus = 1000;
        }
        else {
            shareBody = "Goal Tracker: My status for " + taskname + ", after "+ totalscore + "days is score Mine:"+ score1 +" vs Distractions:" + score2;
            sharedstatus = 10000;
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("sharedstatus", sharedstatus);
        // Commit the edits!
        editor.commit();


        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Status with Goal Tracker App ");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void tracktask(View view) {
        datasource.close();
        Intent intent = new Intent(this, TaskTrackerActivity.class);
        intent.putExtra("id", taskid);
        startActivity(intent);

    }



}
