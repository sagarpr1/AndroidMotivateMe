package com.yourcompany.motivationalquotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences settings;
    private int pointsearned, potionpurchased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = settings.getString("name", "");
        String first = settings.getString("first", "yes");
        pointsearned = settings.getInt("pointsearned", 1000);
        potionpurchased = settings.getInt("potionpurchased", 0);
        TextView pointearnedtxt = (TextView) findViewById(R.id.pointearnedid);
        pointearnedtxt.setText("Points earned:" + pointsearned);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
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


    public void addtaskbtn(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);

    }


    public void statusupdate(View view) {
        Intent intent = new Intent(this, TaskStatus.class);
        startActivity(intent);

    }

}
