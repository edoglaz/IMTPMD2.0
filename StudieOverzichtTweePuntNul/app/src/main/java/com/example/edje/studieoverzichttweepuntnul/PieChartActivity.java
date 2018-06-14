package com.example.edje.studieoverzichttweepuntnul;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;
import com.example.edje.studieoverzichttweepuntnul.Model.CourseModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Map;
/**
 * Created by Edo on 14-6-2018.
 */


public class PieChartActivity extends AppCompatActivity {
    ContentValues values;
    ArrayList<CourseModel> subjects;
    DatabaseHelper dbHelper;
    private PieChart mChart;
    public static final int MAX_ECTS = 240;
    public static int currentEcts = 0;

    int studiepunten;
    int nogbehalen;

    String TAG_VAK = "name";
    String TAG_ECTS = "ects";
    String TAG_GRADE = "grade";
    String TAG_PERIOD = "period";
    String[] projection = {DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.ECTS, DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setDescription(" Overzicht van alle behaalde studiepunten ");
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setData(0);


    }
//De code hieronder zorgt ervoor dat als er op de backbutton van de telefoon wordt geklikt de app je gelijk meeneemt naar de mainactivity. Er zat namelijk een bug in dat hij een lege "Grafisch Overzicht" activity liet zien.
    public void onBackPressed() {
        Intent startMain = new Intent(PieChartActivity.this, MainActivity.class);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }




    private void setData(int aantal) {
        //where clausule
        String selection = DatabaseInfo.CourseColumn.GRADE + ">= 5.5";
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, projection, selection, null, null, null, null);
        //skip lege elementen die misschien eerst staan.
        rs.moveToFirst();
        if (rs.getCount() == 0) {
            //doe niks als er geen database is
        } else {
            //gooi  het in een loop en lees ze stuk voor stk uit
            for (int a = 0; a < rs.getCount(); a++) {
                String vak = (String) rs.getString(rs.getColumnIndex(TAG_VAK));
                int ects = (Integer) rs.getInt(rs.getColumnIndex(TAG_ECTS));
                double grade = (Double) rs.getDouble(rs.getColumnIndex(TAG_GRADE));
                int period = (Integer) rs.getInt(rs.getColumnIndex(TAG_PERIOD));
                //ga naar de volgende in de rij.
                rs.moveToNext();
                this.studiepunten += ects;
            }


        }
        //studiepunten = aantal;
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(studiepunten, 0));
        xValues.add("Behaalde ECTS");

        yValues.add(new Entry(240 - studiepunten, 1));
        xValues.add("Resterende ECTS");

        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        if (studiepunten < 60) {
            colors.add(Color.rgb(255, 0, 0));
        } else if (studiepunten < 120) {
            colors.add(Color.rgb(255, 100, 0));
        } else if (studiepunten < 180) {
            colors.add(Color.rgb(253, 254, 0));
        } else {
            colors.add(Color.rgb(0, 255, 0));
        }
        colors.add(Color.rgb(200, 200, 200));

        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data); // bind dataset aan chart.
        mChart.invalidate();  // Aanroepen van een redraw
        Log.d("aantal =", "" + studiepunten);
    }
}



