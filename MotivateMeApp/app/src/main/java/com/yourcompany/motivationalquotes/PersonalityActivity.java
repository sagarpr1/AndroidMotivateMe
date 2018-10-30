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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimeZone;

public class PersonalityActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences settings;
    private int latesttaskmotiv,losefocus,focussed,totalopentask;
    private int focus,sincerety,timedisc,multitasker,risktaking,determined,prioritize,honesty,opened, sharedstatus,distlist,personaopened;
    private long openedday,maxpoints;

    private long personaopenedDay;
    private Resources res;

    private CheckBox notifenabledChkBox;

    private int notifenabledSaved;
    SampleAlarmReceiver alarm = new SampleAlarmReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality);
        // Restore preferences
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = settings.getString("name", "");
        String first = settings.getString("first", "yes");
        int pointsearned = settings.getInt("pointsearned", 200);
        int potionpurchased = settings.getInt("potionpurchased", 0);
        totalopentask = settings.getInt("opentask", totalopentask);
        focus = settings.getInt("losefocus", 0);
        sincerety = settings.getInt("sincerety", 0);
        timedisc = settings.getInt("timedisc", 0);
        multitasker = settings.getInt("multitasker", 0);
        risktaking = settings.getInt("risktaking", 0);
        determined = settings.getInt("determined", 0);
        prioritize = settings.getInt("prioritize", 0);
        honesty = settings.getInt("honesty", 0);
        opened = settings.getInt("opened", 1);
        openedday = settings.getLong("openedday", 0);
        maxpoints = settings.getLong("maxpoints", 1);
        sharedstatus = settings.getInt("sharedstatus", 0);
        distlist = settings.getInt("distlist", 0);

        personaopenedDay = settings.getLong("personaopenedDay", 0);
        personaopened = settings.getInt("personaopened", 0);
        notifenabledSaved = settings.getInt("notifenabledSaved", 1);

        notifenabledChkBox = (CheckBox) findViewById(R.id.notifenableid);

        // By default checked, so call alarm
        if (notifenabledSaved == 1) {
            notifenabledChkBox.setChecked(true);
            //call this first day only
           // if(openedday == 0) {
                alarm.setAlarm(this, totalopentask);
            //}
        }
        else {
            notifenabledChkBox.setChecked(false);
            alarm.cancelAlarm(this);
        }

        TextView tv11 = (TextView) findViewById(R.id.textView11);
        TextView tv12 = (TextView) findViewById(R.id.textView12);
        TextView tv13 = (TextView) findViewById(R.id.textView13);
        TextView tv14 = (TextView) findViewById(R.id.textView14);
        TextView tv15 = (TextView) findViewById(R.id.textView15);
        TextView tv16 = (TextView) findViewById(R.id.textView16);
        TextView tv17 = (TextView) findViewById(R.id.textView17);
        TextView tv23 = (TextView) findViewById(R.id.textView23);
        TextView tv11n = (TextView) findViewById(R.id.textView11n);
        TextView tv12n = (TextView) findViewById(R.id.textView12n);
        TextView tv13n = (TextView) findViewById(R.id.textView13n);
        TextView tv14n = (TextView) findViewById(R.id.textView14n);
        TextView tv15n = (TextView) findViewById(R.id.textView15n);
        TextView tv16n = (TextView) findViewById(R.id.textView16n);
        TextView tv17n = (TextView) findViewById(R.id.textView17n);
        //RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);

        //rb.setMax(10);
        //rb.setRating((float)sincerety);

        //String msg1 = "pointsearned=" + pointsearned + "focus =" + focus + "sincerety=" + sincerety + "timedisc=" + timedisc ;
        String msg1 = "App used: " + opened + " days";
        tv11.setText(msg1);

        String msg2 = "Points: " + pointsearned;

        TimeZone tz = TimeZone.getDefault();

        long currdate = System.currentTimeMillis();
        //currdate = calendar.getTimeInMillis() ;
        int timeoff = tz.getOffset(currdate);

        currdate = currdate + timeoff;

        long currdateDay = currdate / (24 * 60 * 60 * 1000);

        long daydiff = currdateDay - personaopened;
        //long daydiff = currdateDay - openedday;

        if (daydiff >= 7) {
            personaopenedDay = currdateDay;
            personaopened++;
//            pointsearned = pointsearned +10;
            if (personaopened > 8) {
                personaopened = 1;
            }
            //    personaopened++;
        }


        int diff = opened/7 ;
        int diff1 = mod(diff,8) + 1 ;
        personaopened = diff1;

       // if (personaopened>8) {
       //     personaopened = 1;
       // }



        //String msg2 = "multitasker=" + multitasker + "risktaking=" + risktaking + "determined="+ determined + "prioritize=" + prioritize +
        //        "honesty=" + honesty + "öpened="+ opened + "öpened on" + openedday;
        tv12.setText(msg2);

        int persistence, timely;
        String msg3, msg3n;

//        tv11.setVisibility(View.GONE);
//        tv12.setVisibility(View.GONE);
        tv13.setVisibility(View.GONE);
        tv14.setVisibility(View.GONE);
        tv15.setVisibility(View.GONE);
        tv16.setVisibility(View.GONE);
        tv17.setVisibility(View.GONE);
        tv23.setVisibility(View.GONE);
        tv11n.setVisibility(View.GONE);
        tv12n.setVisibility(View.GONE);
        tv13n.setVisibility(View.GONE);
        tv14n.setVisibility(View.GONE);
        tv15n.setVisibility(View.GONE);
        tv16n.setVisibility(View.GONE);
        tv17n.setVisibility(View.GONE);

        if (totalopentask > 0) {

            if (maxpoints > 0) {
                persistence = (int) ((sincerety * 100) / maxpoints);
                timely = (int) ((timedisc * 100) / maxpoints);
            } else {
                persistence = (int) ((sincerety * 100));
                timely = (int) ((timedisc * 100));

            }
        } else {
            persistence = 50;
            timely = 100;
        }

        msg3 ="Something nice to remember this week : \n Much of the stress that people feel doesn\'t come from having too much to do. It comes from not finishing what they've started.- David Allen";

        if (totalopentask>0) {
            // If first day
            if (personaopened <= 1) {
                if (totalopentask > 0) {
                    //if not shared\
                    if (sharedstatus == 0) {
                        msg3 = "Try sharing your goals with your friends/someone whom you can trust.\n\n" +
                                "Using this app you can simply click on the share button (on reporting page of each goal)" +
                                " to share status about that task\n\n" +
                                " Sharing doubles your chances of completing goals.\n\n"   ;
                                                                 //            sharedstatus = -1;
                    } else {
                        msg3 = "Congrats. You have been sharing about your goal with others. \n\n" +
                                "Continue this habit with all your important goals \n\n" +
                                "Remember to share status as well \n\n." +
                                "------------------------\n\n";
              //          sharedstatus = -1;
                    }

                } else {
                    msg3 = "You are currently not having any open task.\n\n" +
                            "Please visit once you have some task" +
                            "------------------------\n\n";
                }
                //tv13.setVisibility(View.VISIBLE);
               // tv13.setText(msg3);


            }
            //2nd week
            else if (personaopened == 2) {


                if (honesty == 1) {
                    String msg5 = "++: Honest to yourself";
                    //distraction not listed
                    if (distlist == -1) {
                        msg3 = "Good that you are honestly noting down done and not done status.\n\n " +
                                "However assigning reason(s) for distraction would be a good way to identify your main distractions \n\n" +
                                "Identifying distractions helps us a lot in overcoming them and achieving success \n\n" +
                                "------------------------\n\n";
                        //tv15.setText(msg5);
                        //tv15.setVisibility(View.VISIBLE);
                        //make distlist and honesty reset
                //        distlist = 1;
                //        honesty = 0;
                    } else {
                        msg3 = "Great, you are honestly noting down done and not done status.\n\n" +
                                "Plus you are also assigning reason(s) for distraction. Check out the distractions section to see your main distraction \\n\\n\" +\n" +
                                "Identifying distractions helps us a lot in overcoming them and achieving success \n\n" +
                                "------------------------\n\n";
                    }
                    // tv15.setText(msg5);
                    // tv15.setVisibility(View.VISIBLE);
                } else {
                    // distlist is 0. next time honesty is 1 it will again chk for distlist
                    //distlist = 0;
                    msg3 = "Being honest towards onself is a good step towards more success. \n\n" +
                            "------------------------\n\n";
                }



            }
            // from 3rd day onwards
            else if (personaopened == 3) {
                if (totalopentask > 0) {

                    if ((persistence > 50)) {
                        msg3 = "Kudos! You are sincere to your task\n\n " +
                                "Sincerety is one of the key ingredients to success\n\n" +
                                "If you are not getting desired success prioritize and focus on important goals first" +
                                "------------------------\n\n";
                        //tv13.setText(msg3);
                        //tv13.setBackgroundColor(0x33cccc);
                        //tv13.setVisibility(View.VISIBLE);


                    } else {
                        //msg3n = "--: Sincere";
                        msg3 = "Try working on your goals more often. \n\n" +
                                "Doing a task regularly, even in small steps,will help you achieve better success\n\n" +
                                "Small steps with small daily task at a time helps you in completing a seemingly big task." +
                                " Research shows that our brains employ all kinds of tricks and shortcuts to get us through" +
                                " the day with the least mental and  physical effort\n\n " +
                                "------------------------\n\n";
                        //tv13.setText(msg3);
                        //tv13n.setBackgroundColor(333);
                        //tv13.setVisibility(View.VISIBLE);


                    }
                }
            } else if (personaopened == 4) {

                if ((totalopentask == 1) && (persistence > 50)) {
                    msg3 = "Great! You are Focussed with only one task open.\n\n " +
                            "------------------------\n\n";
                    //tv14.setText(msg4);
                    //tv14.setVisibility(View.VISIBLE);
                    //                    tv14n.setVisibility(View.GONE);

                }
                else {
                    //msg about focus
                    msg3 ="Focus on main task to achieve more success \n\n " +
                            "------------------------\n\n";
                }

            } else if (personaopened == 5) {


                if (losefocus == 1) {
                    msg3 = "Losing enthusiasm mid way? \n\n" +
                            "Try doing smaller task (breaking up bigger task in sub task).\n\n" +
                            " Our mind tries to get us through the day with the least mental and  physical effort\n" +
                            "\n" +
                            "Breaking into smaller task will help \n\n" +
                            "------------------------\n\n";
                 //   tv15n.setText(msg6);
                 //   tv15n.setVisibility(View.VISIBLE);
                    //losefocus to 0. if not changed it will again show tomorrow
                    //losefocus = 0;
                }
                else {
                    msg3 ="Celebrating success, however small, is a good way to keep yourself motivated" +
                            "------------------------\n\n";;
                }
            } else if (personaopened == 6) {

                if (timely > 50) {
                    msg3 = "Great, you are Time disciplined \n\n" +
                    "Having time discipline would help train your brain to do that work automatically\n\n" +
                            "------------------------\n\n";
                 //   tv16.setText(msg3);
                    //tv16.setVisibility(View.VISIBLE);
                } else {
                    msg3 = "Doing same task around same time will help you develop a habot of doing it. \n\n" +
                            "Having Time discipline will help train your brain do that task automatically\n\n" +
                              "------------------------\n\n";
                 //   tv16n.setText(msg3);
                 //   tv16n.setVisibility(View.VISIBLE);


                }
            } else if (personaopened == 7) {

                if (potionpurchased == 0) {
                    String msg8;

                    msg3 = "Taking smaller risk to improve yourself and having believe in yourself is a good way to motivate yourself\n\n" +
                            "------------------------\n\n";

                    //tv23.setText(msg8);
                    //tv23.setVisibility(View.VISIBLE);

                }
                // if potionpurchased
                else {
                    msg3 ="Wow! you are not averse to take some risk to improve yourself. \n\n " +
                            "A carrot and stick policy is sometimes very useful to improve yourself";
                }
            }
            // If week>8 default message will play
        }
        //if no open task
        else {
            msg3 = " " +
            "Start using this app by remembering the following: \n. \"Be happy with what you have. Be excited about what you want.\"- Alan Cohen";
        }


        msg3 = "Tip this week\n\n\n" + msg3;
        tv13.setVisibility(View.VISIBLE);
        tv13.setText(msg3);



        //setting alarm manager


//not checking in potionpurchased
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("pointsearned", pointsearned);

        editor.putLong("personaopened", personaopened);
        editor.putInt("honesty", honesty);
        editor.putInt("losefocus", losefocus);
        editor.putInt("multitasker",multitasker);
        editor.putInt("sharedstatus",sharedstatus);
        editor.putInt("distlist",distlist);
        editor.putLong("personaopenedDay", personaopenedDay);
        editor.putInt("personaopened",personaopened);


        editor.commit();








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personality, menu);
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
    public void gotohome(View v) {
        Intent intent = new Intent(this, MainActivity.class);
//        datasource.close();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);



    }
    public void distractview(View v) {
        Intent intent = new Intent(this, DistractorActivity.class);
//        datasource.close();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);



    }

    public void toast (String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    } // end toast

    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }

    public  void notifenable (View view) {


            }

    public void savenotifpref(View view){
        if (notifenabledChkBox.isChecked()) {
            //if (totalopentask>0) {
                alarm.setAlarm(this,totalopentask);

            //}
            notifenabledSaved = 1;
            toast("Notification enabled");
        }
        else {
            alarm.cancelAlarm(this);
            notifenabledSaved = 0;
            toast("Notification disabled");
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("notifenabledSaved", notifenabledSaved);

        editor.commit();

    }

}
