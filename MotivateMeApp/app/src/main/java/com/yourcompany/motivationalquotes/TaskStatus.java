package com.yourcompany.motivationalquotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class TaskStatus extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
        private SharedPreferences settings;
	private TaskDataSource datasource;

    private   TaskSqlHelper db;
    private ArrayList<HashMap<String, String>> list;

    private  List<Task> values;
    private String taskname,itembought, startdate;
    private int daysleft,todaystatus,status;
    private long tasktraits;
    private int currtaskpersistence , currtaskhonesty , currtaskdiscipline , currtasksincerety ;
    private int avgtaskpersistence , avgtaskhonesty , avgtaskdiscipline , avgtasksincerety ;
    //private  int multitasker;
    private int latesttaskmotiv,losefocus,focussed,totalopentask, newmotiv;
    private int focus,sincerety,timedisc,multitasker,risktaking,determined,prioritize,honesty,opened;
    private String dailyavgmotiv;

    private long currdate,openedday;
    private long currdateDay;
    private int score1,score2;
    private long dailystatus;
    private long maxpoints; //max points possible: sum of days

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_status);


	// Restore preferences
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = settings.getString("name", "");
        String first = settings.getString("first","yes");
        int pointsearned = settings.getInt("pointsearned", 1000);
        int potionpurchased = settings.getInt("potionpurchased",0);
        totalopentask = settings.getInt("opentask", 0);
	losefocus = settings.getInt("losefocus", 0);
	sincerety = settings.getInt("sincerety",0);
	timedisc = settings.getInt("timedisc",0);
	multitasker = settings.getInt("multitasker",0);
	risktaking = settings.getInt("risktaking",0);
	determined = settings.getInt("determined",0);
	prioritize = settings.getInt("prioritize",0);
	honesty = settings.getInt("honesty",0);
	opened = settings.getInt("opened", 0);
	openedday = settings.getLong("openedday", 0);
        dailyavgmotiv = settings.getString("dailyavgmotiv","");


        maxpoints = settings.getLong("maxpoints", 0);


        // currdate = System.currentTimeMillis();
//        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //currdate = System.currentTimeMillis();
//        currdate = calendar.getTimeInMillis();
        TimeZone tz = TimeZone.getDefault();

        currdate = System.currentTimeMillis();
        //currdate = calendar.getTimeInMillis() ;
        int timeoff = tz.getOffset(currdate);

        currdate = currdate + timeoff;

        currdateDay = currdate/(24*60*60*1000);
        //toast ("Currdateday is " + currdateDay + "öpenedday is "+ openedday);




        //db = new TaskSqlHelper(this);
	datasource = new TaskDataSource(this);
	datasource.open();


        values = datasource.getAllTask();




        //Array<String> alltaskname;
  	   //List<String> alltasksname = new ArrayList<String>()  ;

        //ArrayList<Map<String, String>> listmap = new ArrayList<Map<String, String>>();

        //Task task = new Task();
        //
	 if (openedday != currdateDay) { // update traits only on per day basis: first time
		openedday = currdateDay;
		opened++;	

         if (opened ==3) { //2nd day some use
             alert("Do you like this app?");
         }

		avgtaskdiscipline =0;
		//avgtaskhonesty=0;
		avgtaskpersistence=0;
		avgtasksincerety=0;
         sincerety=0;
         timedisc=0;
        latesttaskmotiv=0;
         maxpoints = 0; //reset maxpoints

		int numelements=0;

         //only for updating unatteded task etc
		for (Task element : values) {
			taskname = element.getTASKNAME();
			long startdate = element.getSTARTDATE();
		    long statusdate = element.getSTATUSDATE();
		    int diff = (int)((int)(currdate/(24*60*60*1000)) - (int)(startdate/(24*60*60*1000)));
            int tasklastupdated = (int) ((int)(currdate/(24*60*60*1000)) - (int)(statusdate/(24*60*60*1000)));
		     // Compute everything only for open tasks : Assume others already accounted for in db
		     //
		    // toast("Diff is "+ diff);


            // if taskupdated today decrease diff
            if (tasklastupdated == 0 ){
                if (diff>0) {
                    diff--;
                }
            }

            // if within 15 days from day1 to 14 , starts from 0
            if ((diff>0) && (diff <15)) {

                //only sum over current open task
                maxpoints = maxpoints + diff ;

                score1 = mod((int) element.getDAILYSTATUS(),1000);
                score2 = (int) element.getDAILYSTATUS()/1000;

                // if not updated yesterday and it is not first day
                if ((tasklastupdated >1) && (diff >0)) {
                    //If status has not been updated even once on 2nd day also
                    if ((diff>0) && (statusdate == 0)) {
                        newmotiv = element.getMOTIVATEDPERC() -10*(diff);
                        score2 = score2 +diff;
                    }
                    else {
                        newmotiv = element.getMOTIVATEDPERC() - 10 * (tasklastupdated - 1);

                        score2 = score2 + (tasklastupdated - 1);
                    }
                    if (newmotiv < 0) {
                        newmotiv = 0;
                    }
        //            updatetask(element,newmotiv,element.getTaskId());
                    //toast("Task is " + element.getTASKNAME() + "motivated = " + newmotiv + "task id is " + element.getID());
                    element.setMOTIVATEDPERC(newmotiv);
                    if ((score1>999) || (score2>999)){
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
                    element.setDAILYSTATUS(dailystatus);
                    //since updating score set statusdate to yesterday
                    element.setSTATUSDATE((currdate - (1000*60*60*24)));

                    datasource.updateTask(element.getID(), element);

                    //toast ("new motivation is " + element.getMOTIVATEDPERC());
                }


                //Load tasktraits
			tasktraits = element.getTASKTRAITS();
		    // toast("Tasktraits is "+ tasktraits);
			numelements++;

			//Seperate each trait
				//Seperate each trait
                if (diff >0) {
                    currtasksincerety = (int) (mod((int) tasktraits, 100));
                    currtaskdiscipline = (int) (tasktraits)/ 100 ;
                }


             //   currtaskhonesty = (int) tasktraits/100;
				//currtaskhonesty = (int) (mod(((int) tasktraits),1000))/100;
				//currtaskpersistence =(int) (tasktraits/1000);


                sincerety = sincerety + currtasksincerety;
                timedisc = timedisc + currtaskdiscipline;






                //first task
                if (latesttaskmotiv==0) {
                    latesttaskmotiv= element.getMOTIVATEDPERC();
                //    toast("latesttasmmotive.............. is"+ latesttaskmotiv);
                }
                //toast("latesttasmmotive is"+ latesttaskmotiv);

		    }
		    }


         if (maxpoints >0) {
             avgtasksincerety = (int)((sincerety*100)/maxpoints);
             avgtaskdiscipline =(int) ((timedisc * 100)/maxpoints);
         }
         else {
             avgtasksincerety = (int)((sincerety*100));
             avgtaskdiscipline =(int) ((timedisc * 100));

         }

        // toast("maxpoints " + maxpoints + "avgtasksinc " + avgtasksincerety + "avgdisc " + avgtaskdiscipline);

		if(numelements>0){
		    //avgtaskdiscipline = avgtaskdiscipline/numelements;
		    //avgtasksincerety = avgtasksincerety/numelements;

            totalopentask = numelements;



            //multiple task with good sincerety
		    if ((numelements > 3) && (avgtasksincerety > 60)) {
			//avgtaskfocus = 10;
			multitasker=1;

		    }

            //Only focus on new task
		    if ((numelements > 3) && (avgtasksincerety <40) && (latesttaskmotiv > 60)) {
			    losefocus=1;
		    }

            //Only 1 task with sincerety>60
		    if ((numelements == 1) && (avgtasksincerety > 60))  {  // only 1 task open
			    prioritize=1;
		    }


		}


         //Call alarmmanager once everyday


   	SharedPreferences.Editor editor = settings.edit();
        editor.putInt("pointsearned", pointsearned);
         editor.putInt("opentask", totalopentask);
	editor.putInt("losefocus", losefocus);
	editor.putInt("sincerety",sincerety);
	editor.putInt("timedisc",timedisc);
	editor.putInt("multitasker",multitasker);
	//editor.putInt("risktaking",risktaking);
	//editor.putInt("determined",determined);
	editor.putInt("proritize",prioritize);
	//editor.putInt("honesty",honesty);
	editor.putInt("opened",opened);
	editor.putLong("openedday", openedday);
         editor.putLong("maxpoints",maxpoints);

            // Commit the edits!
            editor.commit();

	 } // end of traits commit

		ListView listview = (ListView) findViewById(R.id.listview);

		// ListViewAdapters set time difference and update
		ListViewAdapters adapter=new ListViewAdapters(this,R.layout.custom_row_view, values);
		//listview.setAdapter(adapter);

		       // ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,values);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alltasksname);

        //ListView listview = (ListView) findViewById(R.id.listview);



        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                //String msg = "Position:" + position + "id:" + id;
                //toast(msg);

//                Intent intent = new Intent(this, ReportingActivity.class);
 //               startActivity(intent);
                  onListItemClick1((ListView) parent,view,position,id);
            }
        });


    }

/*    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("purpose", purpose);
        return item;
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_status, menu);
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



    public void onListItemClick1(ListView listview, View v, int position, long id) {
        // Do something when a list item is clicked
	
		    	int pos=listview.getCount()-position;
             long pos1 = pos ;
	 //Task clickedtask = (Task) listview.getItemAtPosition(position);
		Task clickedtask = (Task)listview.getAdapter().getItem(position);
	 long tid = clickedtask.getID();


	    String msg = "Position"+ pos + "getcount" + listview.getCount() + "tid is "+ tid;
	    //toast(msg);
        //Bundle b = ;pos1
       // Task chosentask = values.get(pos);
       // taskname = chosentask.getTASKNAME();
       // status = chosentask.getMOTIVATEDPERC();
       // todaystatus = chosentask.getTODAYSTATUS();
       // itembought = chosentask.getITEMBOUGHT();
       // daysleft = chosentask.getDAYSLEFT();

        //datasource.close();
        Intent intent = new Intent(this, ReportingActivity.class);
        intent.putExtra("id",tid);
        datasource.close();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);

    }


    public void toast (String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    } // end toast

    public void gotohome(View v) {
        Intent intent = new Intent(this, MainActivity.class);
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

    public void updatetask(Task task, int motivatedperc, long taskid){
    //    toast("Task is " + task.getTASKNAME() + "motivated = " + motivatedperc + "task id is " + taskid);
        task.setMOTIVATEDPERC(motivatedperc);
        datasource.updateTask(taskid, task);

    }


    void alert(String message) {
        boolean val=true;
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        alert1("Please do rate us. Good ratings would motivate us to make this app better ");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        toast("Please do send us your feedback to make this better");
                        // User cancelled the dialog
                    }
                });
        //bld.setCancelable(val);
        bld.create().show();
    }


    void alert1(String message) {
        boolean val=true;
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        rateus();
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

    public void rateus() {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Try Google play
        intent.setData(Uri.parse("market://details?id=com.yourcompany.motivationalquotes"));
        if (!MyStartActivity(intent)) {
            //Market (Google play) app seems not installed, let's try to open a webbrowser
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.yourcompany.motivationalquotes"));
            if (!MyStartActivity(intent)) {
                //Well if this also fails, we have run out of options, inform the user.

            }
        }

    }
    private boolean MyStartActivity(Intent aIntent) {
        try
        {
            startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }



}
