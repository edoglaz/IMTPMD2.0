package com.example.edje.studieoverzichttweepuntnul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.edje.studieoverzichttweepuntnul.Model.CourseModel;

import java.util.List;

import static java.security.AccessController.getContext;

public class CourseListAdapter extends ArrayAdapter<CourseModel> {

    public CourseListAdapter(Context context, int resource, List<CourseModel> objects){
        super(context, resource, objects);
    }

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

        CourseModel cm = getItem(position);
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