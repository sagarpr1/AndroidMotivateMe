/**
 * Created by ps on 6/11/2017.
 */
package com.yourcompany.motivationalquotes;

        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.support.v4.content.WakefulBroadcastReceiver;

        import java.util.Calendar;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast Intent
 * and then starts the IntentService {@code SampleSchedulingService} to do some work.
 */
public class SampleAlarmReceiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;

    private int reminderhr,remindermin;

    @Override
    public void onReceive(Context context, Intent intent) {
        // BEGIN_INCLUDE(alarm_onreceive)
        /*
         * If your receiver intent includes extras that need to be passed along to the
         * service, use setComponent() to indicate that the service should handle the
         * receiver's intent. For example:
         *
         * ComponentName comp = new ComponentName(context.getPackageName(),
         *      MyService.class.getName());
         *
         * // This intent passed in this call will include the wake lock extra as well as
         * // the receiver intent contents.
         * startWakefulService(context, (intent.setComponent(comp)));
         *
         * In this example, we simply create a new intent to deliver to the service.
         * This intent holds an extra identifying the wake lock.
         */



        int numtask =intent.getIntExtra("numtask",0);

        //ComponentName comp = new ComponentName(context.getPackageName(),Reminder);
        Intent service = new Intent(context, SampleSchedulingService.class);
        service.putExtra("numtask",numtask);



        //SampleAlarmReceiver.completeWakefulIntent(intent);
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, service);
        // END_INCLUDE(alarm_onreceive)
    }

    // BEGIN_INCLUDE(set_alarm)
    /**
     * Sets a repeating alarm that runs once a day at approximately 8:30 a.m. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     * @param context
     */
    public void setAlarm(Context context, int numtask ) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SampleAlarmReceiver.class);

        intent.putExtra("numtask", numtask);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);


        //calendar.set(Calendar.HOUR_OF_DAY, hr);
        //calendar.set(Calendar.MINUTE, min);

       // Toast.makeText(context,"Alarm manager set for " + hr + " :" + min, Toast.LENGTH_SHORT).show();



        Calendar calendar = Calendar.getInstance();


        if (calendar.get(Calendar.HOUR_OF_DAY) > 5)
        {
            calendar.setTimeInMillis((System.currentTimeMillis()) +24*60*60*1000);
            //calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+ 1);
        }
        else {

            calendar.setTimeInMillis(System.currentTimeMillis());
        }
        // Set the alarm's trigger time to 8:30 a.m.
      //  calendar.setTimeInMillis(System.currentTimeMillis());
        // Set the alarm's trigger time to 8:30 a.m.


// 5 o clock in morning
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 0);

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);


       // alarmMgr.setAlarmClock();
        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(set_alarm)

    /**
     * Cancels the alarm.
     * @param context
     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SampleAlarmReceiver.class);
 //       intent.putExtra("ReminderValue", taskname);
        //intent.putExtra("taskid", taskid);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(cancel_alarm)


}
