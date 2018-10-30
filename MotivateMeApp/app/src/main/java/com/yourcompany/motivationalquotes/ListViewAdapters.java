package com.yourcompany.motivationalquotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.TimeZone;

/**
 * Created by ps on 3/17/2017.
 */
public class ListViewAdapters extends ArrayAdapter<Task> {

    public ListViewAdapters(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListViewAdapters(Context context, int resource, List<Task> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        long CurrDate, StartDate, StatusDate;
        int difference,upddifference, motivnew;
        int score1,score2, runningcurrday, modday, subTaskPresent;
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_row_view, null);
        }

        Task p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.taskname);
            TextView tasksubName = (TextView) v.findViewById(R.id.taskSubName);
            TextView ttid = (TextView) v.findViewById(R.id.taskidView);
            TextView tt2 = (TextView) v.findViewById(R.id.daysleft);
            TextView tt3 = (TextView) v.findViewById(R.id.motivation);
            TextView tt4 = (TextView) v.findViewById(R.id.todaystatus);
            TextView tt5 = (TextView) v.findViewById(R.id.needstatus);
            TextView ttmotiv = (TextView) v.findViewById(R.id.taskMotive);


            StartDate = p.getSTARTDATE();
            //CurrDate = System.currentTimeMillis();
            //Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            //currdate = System.currentTimeMillis();
            //CurrDate = calendar.getTimeInMillis();
            TimeZone tz = TimeZone.getDefault();

            CurrDate = System.currentTimeMillis();
            //currdate = calendar.getTimeInMillis() ;
            int timeoff = tz.getOffset(CurrDate);
            CurrDate = CurrDate + timeoff;

            StatusDate = p.getSTATUSDATE();
            //upddifference = (int)(((CurrDate-StatusDate)/(24*60*60*1000)));
            upddifference = (int)(((int)(CurrDate/(24*60*60*1000))-(int)(StatusDate/(24*60*60*1000))));


            difference = ((int)(15- (((int)(CurrDate/(24*60*60*1000))-(int)(StartDate/(24*60*60*1000)))))); //days left

            if (upddifference == 0 ){
                if (difference > 0){
                    difference--;
                }

            }


            // find goals to see current day, use subtask options
            score1 = mod((int) p.getDAILYSTATUS(),1000);
            score2 = (int) p.getDAILYSTATUS()/1000;
            subTaskPresent = p.getSUBTASKPRESENT();

            runningcurrday = score1 + score2;

            // show current day if status is updated today
            if (runningcurrday > 0) {
                if (upddifference==0) {
                    runningcurrday--;
                }
            }

            modday = mod (runningcurrday,7);




            if (tt1 != null) {
                String missionname = "" + p.getTASKNAME() ;
                tt1.setText(missionname);
            }


            if (tasksubName != null) {
                String subtaskname;
                if (subTaskPresent == 1) {
                  if (modday == 0) {
                      subtaskname =  p.getSUBTASK_1();

                  }
                  else if (modday == 1){
                      subtaskname =  p.getSUBTASK_2();
                  }
                  else if (modday == 2){
                      subtaskname =  p.getSUBTASK_3();
                  }
                  else if (modday == 3){
                      subtaskname =  p.getSUBTASK_4();
                  }
                  else if (modday == 4){
                      subtaskname =  p.getSUBTASK_5();
                  }
                  else if (modday == 5){
                      subtaskname =  p.getSUBTASK_6();
                  }
                  else if (modday == 6){
                      subtaskname =  p.getSUBTASK_7();
                  }
                  else {
                      subtaskname = "Today's Sub Task: None"  ;

                  }

                    if ((subtaskname != null) || (subtaskname != "")) {
                        tasksubName.setText("Today's Sub Task: "+ subtaskname);
                    }
                    else {
                        //tasksubName.setVisibility(View.GONE);
                        tasksubName.setText("Today's Sub Task: Rest ");
                    }
                }
                // if SubTaskPresent = 0 set visibilty to gone
                else {
                    tasksubName.setVisibility(View.GONE);
                }
            }

            if (ttid != null) {
                ttid.setText("Task ID: " + Long.toString(p.getID()));
            }

            if (ttmotiv != null) {
              ttmotiv.setText("Motive: " + p.getTASKMOTIVE()) ;
            }

            if (tt2 != null) {

/*		    int difference= ((int)((StartDate.getTime()/(24*60*60*1000))
-(int)(CurrDate.getTime()/(24*60*60*1000))));
*/
            //    int difference =0;
		         //p.setDAYSLEFT(difference);

                String daysleftstr = "Days Left: " + difference;
                if (difference <= 0) {
                    daysleftstr = "Task Over ";
                }
                else {
                    daysleftstr = "Days Left: " + difference;

                }

                    tt2.setText(daysleftstr);
            }

            if (tt3 != null) {
                tt3.setText("Motivation: " + Integer.toString(p.getMOTIVATEDPERC()) + "%");
            }

            if (tt4 != null) {
                tt4.setText("Today Status: " + Long.toString(p.getTODAYSTATUS()));
            }


            if (tt5 != null) {

                if (difference <= 0){
                    tt5.setVisibility(View.GONE);
                }
                else {
                    tt5.setVisibility(View.VISIBLE);
                    if (upddifference > 0) {
                        tt5.setText("Need to update: ");
                    } else {
                        tt5.setText("Uptodate");
                    }


                }

            }

        }

        return v;
    }

    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }


}
