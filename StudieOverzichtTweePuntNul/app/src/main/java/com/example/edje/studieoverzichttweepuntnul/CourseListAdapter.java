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
import android.widget.TextView;

import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;
import com.example.edje.studieoverzichttweepuntnul.Model.CourseModel;

import java.util.List;


import static java.security.AccessController.getContext;

public class CourseListAdapter extends ArrayAdapter<CourseModel> {
    String TAG_GRADE = "grade";

    public CourseListAdapter(Context context, int resource, List<CourseModel> objects){
        super(context, resource, objects);
    }
    String[] projection = {DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.ECTS,DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD};
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

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
        //double selection = DatabaseInfo.CourseColumn.GRADE;


        CourseModel cm = getItem(position);
        String grade1 = cm.getGrade();
        double grade2 = Double.parseDouble(grade1);
        if(grade2 >= 5.5)
        {
            convertView.setBackgroundColor(Color.parseColor("#3CFD50"));
        }
        else{
            convertView.setBackgroundColor(Color.parseColor("#FD3C3C"));
        }
        vh.NAME.setText("Vak:"+(CharSequence) cm.getName());
        vh.ECTS.setText("Aantal Ects:"+(CharSequence) cm.getEcts());
        vh.GRADE.setText("Cijfer:"+(CharSequence) cm.getGrade());
        vh.PERIOD.setText("Periode:"+(CharSequence) cm.getPeriod());
        vh.CODE.setText("Course code:"+(CharSequence) cm.getCode());
        //vh.STUDIEJAAR.setText("Studiejaar:"+(CharSequence) cm.getStudiejaar());

        return convertView;
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