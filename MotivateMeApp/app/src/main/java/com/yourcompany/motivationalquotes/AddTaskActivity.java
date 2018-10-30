package com.yourcompany.motivationalquotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.TimeZone;

public class AddTaskActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences settings;
    private String firsttask;


    private   TaskSqlHelper db;
    private TaskDataSource datasource;
    private TextView textMotive, tglSubTaskLayout;
    private EditText editTask,editTaskMotiv, editSubTask1, editSubTask2, editSubTask3, editSubTask4, editSubTask5, editSubTask6, editSubTask7;


    private TimePicker editTime;
    private RelativeLayout subTaskRelativeLayout;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

  //  SampleAlarmReceiver alarm = new SampleAlarmReceiver();


    private CheckBox SubTaskPresentChkBox;

    private CheckBox notifReminder;

    String[] taskoptions ={"Gym every day","8 glss water every day","1200 calories every day","Wake up at 5","Play 1 hour everyday"};


    String[] motiveoptions ={"Your laziness","Competitor","..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


      //  subTaskRelativeLayout = (RelativeLayout) findViewById(R.id.subtsklayout);
     //   subTaskRelativeLayout.setVisibility(View.GONE);

        tglSubTaskLayout = (TextView) findViewById(R.id.subTaskHdr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, taskoptions);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,
//               android.R.layout.select_dialog_item,language);

        editTask = (EditText)findViewById(R.id.editTextTask);
        editTask.setHint("e.g. Gym every day,build my company, ...");
   //     editTask.setThreshold(0);//
    //    editTask.setAdapter(adapter);




        TextView textTask = (TextView)findViewById(R.id.textView3);

        ArrayAdapter<String> adaptermotiv = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, motiveoptions);

        textMotive = (TextView)findViewById(R.id.textView24);
        editTaskMotiv = (EditText)findViewById(R.id.editTextMotiv);
        editTaskMotiv.setHint("e.g. Be slim in 15 days");
        editTaskMotiv.setImeOptions(EditorInfo.IME_ACTION_DONE);

//       editTaskMotiv.setThreshold(0);//
 //       editTaskMotiv.setAdapter(adaptermotiv);
//        Sub Task names : By default they will be blank and SubTaskPresent =0;

     /*   SubTaskPresentChkBox = (CheckBox) findViewById(R.id.subTaskPresentChk);
        editSubTask1 = (EditText) findViewById(R.id.editTask1);
        editSubTask2 = (EditText) findViewById(R.id.editTask2);
        editSubTask3 = (EditText) findViewById(R.id.editTask3);
        editSubTask4 = (EditText) findViewById(R.id.editTask4);
        editSubTask5 = (EditText) findViewById(R.id.editTask5);
        editSubTask6 = (EditText) findViewById(R.id.editTask6);
        editSubTask7 = (EditText) findViewById(R.id.editTask7);
*/



        Button btnAddTask = (Button)findViewById(R.id.buttonAddTask);
        Button btnHome = (Button)findViewById(R.id.buttonHome);
        TextView textTaskAdded = (TextView)findViewById(R.id.textView7);

        textTask.setVisibility(View.VISIBLE);
        editTask.setVisibility(View.VISIBLE);

        textMotive.setVisibility(View.VISIBLE);
        editTaskMotiv.setVisibility(View.VISIBLE);

        btnAddTask.setVisibility(View.VISIBLE);
        btnHome.setVisibility(View.VISIBLE);
        textTaskAdded.setVisibility(View.GONE);
	    db = new TaskSqlHelper(this);  // my question bank class
        //List<Task> taskList = db.getAllTask();  // this will fetch all quetonall questions
        //Task currentTask = taskList.get(1); // the current question
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = settings.getString("name", "");
        firsttask = settings.getString("firsttask","yes");

        long valueStartDate1 = System.currentTimeMillis()/(24*60*60*1000);
        //toast("Date" + valueStartDate1);
    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
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


   public void addtask(View v) {


       //open datasource
       datasource = new TaskDataSource(this);
       datasource.open();

       //   TextView textTask = (TextView)findViewById(R.id.textView3);
       //EditText editTask = (EditText)findViewById(R.id.editTextTask);
    //   Button btnAddTask = (Button)findViewById(R.id.buttonAddTask);
    //   Button btnChkTask = (Button)findViewById(R.id.buttonHome);
       TextView textTaskAdded = (TextView)findViewById(R.id.textView7);

       String valueTask = editTask.getText().toString();
       String valueMotiv = editTaskMotiv.getText().toString();
       int valueSubTaskPresent;
       String valueSubTask1, valueSubTask2, valueSubTask3, valueSubTask4, valueSubTask5, valueSubTask6, valueSubTask7;

       valueSubTaskPresent = 0;
       valueSubTask1 = "";
       valueSubTask2="";
       valueSubTask3="";
       valueSubTask4="";
       valueSubTask5="";
       valueSubTask6="";
       valueSubTask7="";
  /*
       if (SubTaskPresentChkBox.isChecked()) {
           valueSubTaskPresent = 1;

       }
       else {
           valueSubTaskPresent = 0;
       }

       valueSubTask1 = editSubTask1.getText().toString();
       valueSubTask2 = editSubTask2.getText().toString();
       valueSubTask3 = editSubTask3.getText().toString();
       valueSubTask4 = editSubTask4.getText().toString();
       valueSubTask5 = editSubTask5.getText().toString();
       valueSubTask6 = editSubTask6.getText().toString();
       valueSubTask7 = editSubTask7.getText().toString();
*/

       //toast(valueTask);
       int valueMotivPerc = 50;
       int valueDaysLeft = 15;
       int valueTodayStatus = 0;
       String valueItemBought = "";

       //Calendar StartDate = Calendar.getInstance();
       long valueStartDate = System.currentTimeMillis();
      // Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
       //currdate = System.currentTimeMillis();
       TimeZone tz = TimeZone.getDefault();

       //currdate = System.currentTimeMillis();
       //currdate = calendar.getTimeInMillis() ;
       int timeoff = tz.getOffset(valueStartDate);
       valueStartDate = valueStartDate + timeoff;

       //long valueStartDate = calendar.getTimeInMillis();

       long valueStatusDate = 0;
       long valueDailyStatus = 0;
       long valueTaskTraits = 0;


       Task newTask = new Task(valueTask,valueMotiv,valueMotivPerc,valueStatusDate,valueStartDate,valueTodayStatus,valueDailyStatus, valueTaskTraits,
               valueSubTaskPresent, valueSubTask1, valueSubTask2, valueSubTask3, valueSubTask4, valueSubTask5, valueSubTask6, valueSubTask7,0,0 );

       //String Taskname = newTask.getTASKNAME();
       //long StrtDate = newTask.getSTARTDATE();
       //toast(Taskname);
       //toast("Start " + StrtDate);


     //db = new TaskSqlHelper(this);
       Task newtaskcreated = datasource.createTask(newTask);
       //long taskcreatedid = newtaskcreated.getID();
       // db.addTask(newTask);
       toast("Task added successfully");



       textTaskAdded.setVisibility(View.GONE);
   }


    public void gotohome(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }


    public void tglSubTask (View v) {
        if (subTaskRelativeLayout.getVisibility() == View.VISIBLE) {
            subTaskRelativeLayout.setVisibility(View.GONE);
        }
        else {
            subTaskRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    public void toast (String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    } // end toast
}
