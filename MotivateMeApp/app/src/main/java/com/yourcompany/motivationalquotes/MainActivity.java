package com.yourcompany.motivationalquotes;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

public static final String PREFS_NAME = "MyPrefsFile";
private SharedPreferences settings;
private Button nameSubmit, statusbtn, addbtn;
    private EditText textName1;
    private TextView txtViewMain, textUserName;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameSubmit = (Button) findViewById(R.id.button);
        txtViewMain = (TextView) findViewById(R.id.textViewMain);
        statusbtn = (Button) findViewById(R.id.statusbtnid);
        addbtn = (Button) findViewById(R.id.addbtnid);
        textUserName = (TextView) findViewById(R.id.textView);
        textName1 = (EditText)findViewById(R.id.editTextName);

// Restore preferences
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = settings.getString("name", "user");
        String first = settings.getString("first","yes");
        String intromsgdone = settings.getString("intromsgdone", "no");
        int pointsearned = settings.getInt("pointsearned", 1000);
        int potionpurchased = settings.getInt("potionpurchased", 0);

        res = getResources();
        // Toast a message for first time
        if (intromsgdone == "no") {

            String firstmessage = res.getString(R.string.initialmsg1);
            alert1(firstmessage);
            intromsgdone ="yes";
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("intromsgdone", "ÿes");

            // Commit the edits!
            editor.commit();

        }

        name = "user";
        if (name != "") {
            //toast welcome msg
            //
            //toast("Hello " + name);
            String WelcomeMsg = "Goal Tracker";

            textUserName.setText(WelcomeMsg);
            nameSubmit.setVisibility(View.GONE);
            textName1.setVisibility(View.GONE);

            addbtn.setVisibility(View.VISIBLE);
            statusbtn.setVisibility(View.VISIBLE);
            txtViewMain.setVisibility(View.VISIBLE);

            textUserName.setVisibility(View.VISIBLE);

        } else {
            // make name input active
            //
            addbtn.setVisibility(View.GONE);
            statusbtn.setVisibility(View.GONE);
            txtViewMain.setVisibility(View.GONE);

            nameSubmit.setVisibility(View.VISIBLE);
            textName1.setVisibility(View.VISIBLE);
            textUserName.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void submitname(View v) {
//get value from edittext name
        EditText textName = (EditText)findViewById(R.id.editTextName);
        String valueName = textName.getText().toString();
        if (valueName != null) {
            //put in shared pref
            // We need an Editor object to make preference changes.
            // All objects are from android.context.Context
            //settings = getSharedPreferences(PREFS_NAME, 0);
            settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String name = settings.getString("name", "Name");
            toast(name);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("name", valueName);

            // Commit the edits!
            editor.commit();

            String WelcomeMsg = "Welcome " + valueName;

            //nameSubmit = (Button) findViewById(R.id.button);
            //introBtn = (Button) findViewById(R.id.statusbtnid);
            //TextView textUserName = (TextView) findViewById(R.id.textView);
            //textName1 = (EditText)findViewById(R.id.editTextName);
            textUserName.setText(WelcomeMsg);
            nameSubmit.setVisibility(View.GONE);

            textName1.setVisibility(View.GONE);

            textUserName.setVisibility(View.VISIBLE);
            addbtn.setVisibility(View.VISIBLE);
            statusbtn.setVisibility(View.VISIBLE);
            txtViewMain.setVisibility(View.VISIBLE);



            //
        } else
        {
            //toast msg to user about empty name
            toast("Please enter a name");
        }

    }

    public void intro (View v) {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);


    }

    public void addtaskbtn(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);

    }

    public void statusupdate(View view) {
        Intent intent = new Intent(this, TaskStatus.class);
        startActivity(intent);

    }


    public void showtraits(View v) {
        Intent intent = new Intent(this, PersonalityActivity.class);
        startActivity(intent);

    }

    public void showhelp(View v) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);

    }

    public void rateus(View view) {

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


    public void shareclick(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Goal Tracker. Try this app at https://play.google.com/store/apps/details?id=com.yourcompany.motivationalquotes ";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "App to stay motivated ");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void quotes(View v) {
        Intent intent = new Intent(this, QuotesActivity.class);
        startActivity(intent);

    }

    void alert1(String message){
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message)
        .setNeutralButton("Next",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // FIRE ZE MISSILES!
                String msg2 = res.getString(R.string.initialmsg2);
                alert(msg2);
            }
        });
        //bld.setNeutralButton("Next", )
        //  Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
      //  Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

}
