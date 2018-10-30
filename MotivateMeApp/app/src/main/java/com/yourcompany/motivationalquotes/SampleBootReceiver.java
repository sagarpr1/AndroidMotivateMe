/**
 * Created by ps on 6/11/2017.
 */
package com.yourcompany.motivationalquotes;


        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;

        import java.util.List;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class SampleBootReceiver extends BroadcastReceiver {

    private TaskDataSource datasource;

    private TaskSqlHelper db;
    private List<Task> values;
    private int reminderhr, remindermin, reminderTime;

    private String taskname;

    private int numtask;

    SampleAlarmReceiver alarm = new SampleAlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

       // if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //context = ApplicationActivity.class;
            //AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            //Intent intent = new Intent(context, WakefulReceiver.class);
            //PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

          //  Calendar calendar = Calendar.getInstance();
          //  calendar.setTimeInMillis(System.currentTimeMillis());

            //int hrnow = calendar.get(Calendar.HOUR_OF_DAY);




            datasource = new TaskDataSource(context);
            datasource.open();


             numtask = 0;
            values = datasource.getAllTask();

            for (Task element : values) {
                 numtask++;
                }
            }



        datasource.close();
        alarm.setAlarm(context, numtask);
        //Toast.makeText(context, "Boot receiver for Alarm manager set ", Toast.LENGTH_SHORT).show();



    }
}
//END_INCLUDE(autostart)
