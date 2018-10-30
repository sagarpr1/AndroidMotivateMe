package com.yourcompany.motivationalquotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DecimalFormat;

public class DistractorActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences settings;
    private int pointsearned, potionpurchased;
    private int opttv, optfb, optfrnds,optoverheads,optlowenth,opttough,optenv,optothers,optmanytask,optlazy;
    private int totalopt;
    private float tvperc, fbperc, frndsperc, overheadperc, lowentperc, toughperc, othersperc, manytaskperc, lazyperc,envperc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distractor);
        TextView tvdist1perc,tvdist1name, tvdist2perc, tvdist2name, tvdist3perc, tvdist3name, tvdist4perc, tvdist4name, tvdist5perc, tvdist5name;

        tvdist1name = (TextView) findViewById(R.id.tvdist1name);
        tvdist1perc = (TextView) findViewById(R.id.tvdist1perc);

        tvdist2name = (TextView) findViewById(R.id.tvdist2name);
        tvdist2perc = (TextView) findViewById(R.id.tvdist2perc);


        tvdist3name = (TextView) findViewById(R.id.tvdist3name);
        tvdist3perc = (TextView) findViewById(R.id.tvdist3perc);

        tvdist4name = (TextView) findViewById(R.id.tvdist4name);
        tvdist4perc = (TextView) findViewById(R.id.tvdist4perc);

        tvdist5name = (TextView) findViewById(R.id.tvdist5name);
        tvdist5perc = (TextView) findViewById(R.id.tvdist5perc);

        // Restore preferences
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = settings.getString("name", "");
        String first = settings.getString("first", "yes");
        pointsearned = settings.getInt("pointsearned", 1000);
        potionpurchased = settings.getInt("potionpurchased", 0);

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

        // draw whatever is non zero

        GraphView graph = (GraphView) findViewById(R.id.graph);
        // use static labels for horizontal and vertical labels
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"1", "2", "3","4","5"});
//        staticLabelsFormatter.setHorizontalLabels(new String[] {"TV & Games", "Internet, Friends", "Laziness","Lose Focus","Others"});

        //staticLabelsFormatter.setVerticalLabels(new String[] {"low", "middle", "high"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        totalopt = opttv + optfb + optfrnds + optoverheads + opttough + optlowenth + optenv + optothers + optmanytask + optlazy;
        if(totalopt > 0) {
            //find non zero ones
            if (opttv > 0 ){
                tvperc = (float) 100* opttv/totalopt;
                tvdist1perc.setText((new DecimalFormat("##.##").format(tvperc)) + "%");
                tvdist1name.setText("TV & Games");
                //table param: width, height,float perc weight
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,tvperc);
                tvdist1perc.setLayoutParams(lParams);
            }
            else {
                tvperc = (float) 0;
                tvdist1perc.setText(tvperc + "%");
                tvdist1name.setText("TV & Games");
                //table param: width, height,float perc weight
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,1);
                tvdist1perc.setLayoutParams(lParams);
            }

            if (optfb > 0 ){
                fbperc = (float) 100* optfb/totalopt;
                tvdist2perc.setText((new DecimalFormat("##.##").format(fbperc)) + "%");
                tvdist2name.setText("Internet, Friends");
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,fbperc);
                tvdist2perc.setLayoutParams(lParams);
            }
            else {
                fbperc = (float) 0;
                tvdist2perc.setText(fbperc + "%");
                tvdist2name.setText("Internet,Friends");
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,1);
                tvdist2perc.setLayoutParams(lParams);
            }

            if (optothers > 0 ){
                othersperc = (float) 100* optothers/totalopt;
                tvdist3perc.setText((new DecimalFormat("##.##").format(othersperc)) + "%");
                tvdist3name.setText("Others");
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,othersperc);
                tvdist3perc.setLayoutParams(lParams);

            }
            else {
                othersperc = (float) 0;
                tvdist3perc.setText(othersperc + "%");
                tvdist3name.setText("Others");
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,1);
                tvdist3perc.setLayoutParams(lParams);
            }


            if (optlazy > 0 ){
                lazyperc = (float) 100* optlazy/totalopt;
                tvdist4perc.setText((new DecimalFormat("##.##").format(lazyperc)) + "%");
                tvdist4name.setText("Laziness");
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,lazyperc);
                tvdist4perc.setLayoutParams(lParams);

            }
            else {
                lazyperc = (float) 0;
                tvdist4perc.setText(lazyperc + "%");
                tvdist4name.setText("Laziness");
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,1);
                tvdist4perc.setLayoutParams(lParams);

            }

            if (optenv>0) {
                envperc = (float) 100* optenv/totalopt;
                tvdist5perc.setText((new DecimalFormat("##.##").format(envperc)) + "%");
                tvdist5name.setText("Lose Focus");
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,envperc);
                tvdist5perc.setLayoutParams(lParams);

            }
            else {
                envperc = (float) 0;
                tvdist5perc.setText(envperc + "%");
                tvdist5name.setText("Lose Focus");
                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,1);
                tvdist5perc.setLayoutParams(lParams);

            }



            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, tvperc),
                    new DataPoint(1, fbperc),
                    new DataPoint(2, lazyperc),
                    new DataPoint(3, envperc),
                    new DataPoint(4, othersperc)
            });
            graph.addSeries(series);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(100);

// styling
            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                }
            });

            series.setSpacing(50);

// draw values on top
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);

        }
        // If no distractions totalopt==0
        else {

            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, 0),
                    new DataPoint(1, 0),
                    new DataPoint(2, 0),
                    new DataPoint(3, 0),
                    new DataPoint(4, 0)
            });
            graph.addSeries(series);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(100);

// styling
            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                }
            });

            series.setSpacing(50);

// draw values on top
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);

            tvdist1perc.setText("0%");
            tvdist1name.setText("TV & Games");
            tvdist2perc.setText("0%");
            tvdist2name.setText("Internet, Friends");
            tvdist3perc.setText("0%");
            tvdist3name.setText("Others");
            tvdist4perc.setText("0%");
            tvdist4name.setText("Laziness");
            tvdist5perc.setText("0%");
            tvdist5name.setText("Lose Focus");


            //table param: width, height,float perc weight
            TableRow.LayoutParams lParams = new TableRow.LayoutParams(0,50,1);
            tvdist1perc.setLayoutParams(lParams);
            tvdist2perc.setLayoutParams(lParams);
            tvdist3perc.setLayoutParams(lParams);
            tvdist4perc.setLayoutParams(lParams);
            tvdist5perc.setLayoutParams(lParams);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_distractor, menu);
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

    public void resetdist(View v){
        SharedPreferences.Editor editor = settings.edit();
        //Update distractions
        opttv=0;
        optfb=0;
        optfrnds=0;
        optoverheads=0;
        opttough=0;
        optenv=0;
        optlazy=0;
        optothers=0;
        optlowenth=0;
        optmanytask=0;

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

        // Commit the edits!
        editor.commit();
        finish();
        startActivity(getIntent());

    }
}
