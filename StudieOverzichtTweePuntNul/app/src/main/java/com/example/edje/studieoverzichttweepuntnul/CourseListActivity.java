package com.example.edje.studieoverzichttweepuntnul;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;
import com.example.edje.studieoverzichttweepuntnul.Model.CourseModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.CODE;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.ECTS;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.GRADE;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.NAME;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.PERIOD;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.STUDIEJAAR;

public class CourseListActivity extends AppCompatActivity {
    private ListView mListView;
    private CourseListAdapter mAdapter;
    private List<CourseModel> courseModels = new ArrayList<>();
    String[] projection = {DatabaseInfo.CourseColumn.NAME,DatabaseInfo.CourseColumn.CODE, DatabaseInfo.CourseColumn.ECTS,DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD, STUDIEJAAR};

    String TAG_VAK = "name";
    String TAG_ECTS = "ects";
    String TAG_GRADE = "grade";
    String TAG_PERIOD = "period";
    String TAG_CODE = "code";
    String TAG_STUDIEJAAR = "studiejaar";
    String studiejaar1;
    String studiejaar2;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        mListView = (ListView) findViewById(R.id.my_list_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            toolbar.setTitle(bundle.getString("StudieJaar"));

        }


        studiejaar1 = getResources().getStringArray(R.array.studiejaren)[0];
        if (toolbar.getTitle().equals(studiejaar1))
        {

                ShowDataStudieJaar1();

        }

        studiejaar2 = getResources().getStringArray(R.array.studiejaren)[1];
        if (toolbar.getTitle().equals(studiejaar2))
        {

            ShowDataStudieJaar2();

        }


    }

    public void ShowDataStudieJaar1()
        {

            DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
            //sortorder op periode
            String stj1 = "1";
            String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
            //String order = PERIOD + ", " ;
            Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + TAG_STUDIEJAAR + "='" + stj1 + "'", projection,null,null,null,null,order);

            //Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, projection, null, null, null, null, order);
            //skip lege elementen die misschien eerst staan.
            rs.moveToFirst();
            if (rs.getCount() == 0) {
                Toast.makeText(this,
                        "geen database beschikbaar",
                        Toast.LENGTH_LONG).show();
            } else {
                //gooi  het in een loop en lees ze stuk voor stk uit
                for (int a = 0; a < rs.getCount(); a++) {

                    String code = (String) rs.getString(rs.getColumnIndex(TAG_CODE));
                    String naam = (String) rs.getString(rs.getColumnIndex(TAG_VAK));
                    int ects = (Integer) rs.getInt(rs.getColumnIndex(TAG_ECTS));
                    double grade = (Double) rs.getDouble(rs.getColumnIndex(TAG_GRADE));
                    int period = (Integer) rs.getInt(rs.getColumnIndex(TAG_PERIOD));
                    int studiejaar = (Integer) rs.getInt(rs.getColumnIndex(TAG_STUDIEJAAR));

                    //add opgehaalde data in de model

                    courseModels.add(new CourseModel(code, naam, ects, grade, period, studiejaar));

                    //ga naar de volgende in de rij.
                    rs.moveToNext();

                }
                //zet listview items etc nadat het geladen is uit de e database
                mListView = (ListView) findViewById(R.id.my_list_view);
                mAdapter = new CourseListAdapter(CourseListActivity.this, 0, courseModels);
                mListView.setAdapter(mAdapter);

            }

    }

    public void ShowDataStudieJaar2()
    {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //sortorder op periode
        String stj2 = "2";
        String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
        //String order = PERIOD + ", " ;
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + TAG_STUDIEJAAR + "='" + stj2 + "'", projection,null,null,null,null,order);

        //Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, projection, null, null, null, null, order);
        //skip lege elementen die misschien eerst staan.
        rs.moveToFirst();
        if (rs.getCount() == 0) {
            Toast.makeText(this,
                    "geen database beschikbaar",
                    Toast.LENGTH_LONG).show();
        } else {
            //gooi  het in een loop en lees ze stuk voor stk uit
            for (int a = 0; a < rs.getCount(); a++) {

                String code = (String) rs.getString(rs.getColumnIndex(TAG_CODE));
                String naam = (String) rs.getString(rs.getColumnIndex(TAG_VAK));
                int ects = (Integer) rs.getInt(rs.getColumnIndex(TAG_ECTS));
                double grade = (Double) rs.getDouble(rs.getColumnIndex(TAG_GRADE));
                int period = (Integer) rs.getInt(rs.getColumnIndex(TAG_PERIOD));
                int studiejaar = (Integer) rs.getInt(rs.getColumnIndex(TAG_STUDIEJAAR));

                //add opgehaalde data in de model

                courseModels.add(new CourseModel(code, naam, ects, grade, period, studiejaar));

                //ga naar de volgende in de rij.
                rs.moveToNext();

            }
            //zet listview items etc nadat het geladen is uit de e database
            mListView = (ListView) findViewById(R.id.my_list_view);
            mAdapter = new CourseListAdapter(CourseListActivity.this, 0, courseModels);
            mListView.setAdapter(mAdapter);

        }

    }
}
