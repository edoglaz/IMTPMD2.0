package com.example.edje.studieoverzichttweepuntnul;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;
import com.example.edje.studieoverzichttweepuntnul.Model.CourseModel;

import java.util.ArrayList;
import java.util.List;


import static java.security.AccessController.getContext;
/**
 * Created by Edo on 14-6-2018.
 */
public class CourseListAdapter extends ArrayAdapter<CourseModel> {
    String TAG_GRADE = "grade";
    private ListView mListView;
    private CourseListAdapter mAdapter;
    private List<CourseModel> courseModels = new ArrayList<>();
    public CourseListAdapter(Context context, int resource, List<CourseModel> objects){
        super(context, resource, objects);
    }
    String[] projection = {DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.ECTS,DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD};
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        // Als de view leeg is vul hem dan met elementen uit de layout: view_content_row
        if (convertView == null ) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.view_content_row, parent, false);
            vh.NAME = (TextView) convertView.findViewById(R.id.subject_name);
            vh.ECTS = (TextView) convertView.findViewById(R.id.subject_ects);
            vh.GRADE = (TextView) convertView.findViewById(R.id.subject_grade);
            vh.PERIOD = (TextView) convertView.findViewById(R.id.subject_period);
            vh.CODE = (TextView) convertView.findViewById(R.id.subject_code);
            //vh.STUDIEJAAR = (TextView) convertView.findViewById(R.id.subject_studiejaar);
            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }



        CourseModel cm = getItem(position);
        // haal het cijfer uit het CourseModel

        String grade1 = cm.getGrade();

        double grade2 = Double.parseDouble(grade1);
        // als het cijfer hoger of gelijk is aan 5.5 dan is het vak behaald en wordt de achtegrond van het listview item lichtgroen.
        if(grade2 >= 5.5)
        {
            convertView.setBackgroundColor(Color.parseColor("#C8E6C9"));
        }
        // als er voor het vak een onvoldoende staat(dus een cijfer van onder de 5.5) dan wordt de achtergrond lichtrood gemaakt.
        else{
            convertView.setBackgroundColor(Color.parseColor("#FFCCBC"));
        }
        // zet de teksten met tekst uit het CourseModel
        vh.NAME.setText(""+(CharSequence) cm.getName());
        vh.ECTS.setText("Aantal Ects:"+(CharSequence) cm.getEcts());
        vh.GRADE.setText("Cijfer:"+(CharSequence) cm.getGrade());
        vh.PERIOD.setText("Periode:"+(CharSequence) cm.getPeriod());
        vh.CODE.setText(""+(CharSequence) cm.getCode());


        return convertView;
    }
    public void updateList(List<CourseModel> courseModels) {
        this.courseModels.clear();
        this.courseModels.addAll(courseModels);
    }
    private static class ViewHolder {
        TextView NAME;
        TextView PERIOD;
        TextView ECTS;
        TextView GRADE;
        TextView CODE;
        TextView STUDIEJAAR;
    }
}