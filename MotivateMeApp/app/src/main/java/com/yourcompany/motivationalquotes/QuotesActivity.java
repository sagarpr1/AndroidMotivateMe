package com.yourcompany.motivationalquotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class QuotesActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences settings;
    private int quotesopened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        TextView quotestext = (TextView) findViewById(R.id.quotestext);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        quotesopened = settings.getInt("quotesopened", 0);

        String msg =  "";
        Resources res = getResources();
        String[] quotemsg = res.getStringArray(R.array.quotes);


        int day = mod(quotesopened,95);

        msg = quotemsg[day];
        quotestext.setText(msg);

        quotesopened++;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("quotesopened", quotesopened);

        editor.commit();


 


    }

@Override
public void onResume() {
  super.onResume();
}
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quotes, menu);
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

    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }


    public void gototask (View v) {
        Intent intent = new Intent(this, TaskStatus.class);
//        datasource.close();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);



    }
    public void gotohome(View v) {
        Intent intent = new Intent(this, MainActivity.class);
//        datasource.close();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);



    }
}
