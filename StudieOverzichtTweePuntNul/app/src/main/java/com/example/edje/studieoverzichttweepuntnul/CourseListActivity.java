package com.example.edje.studieoverzichttweepuntnul;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        mListView = (ListView) findViewById(R.id.my_list_view);
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, new String[]{"*"}, null, null, null, null, null);

        //rs.moveToFirst();   // Skip : de lege elementen vooraan de rij.
// Maar : de rij kan nog steeds leeg zijn
// Hoe : lossen we dit op ??

// Haalt de name uit de resultset
        //String name = (String) rs.getString(rs.getColumnIndex("name"));
        //String code = (String) rs.getString(rs.getColumnIndex("code"));
        //String ects = (String) rs.getString(rs.getColumnIndex("ects"));
        //String grade = (String) rs.getString(rs.getColumnIndex("grade"));
        //String period = (String) rs.getString(rs.getColumnIndex("period"));

       // int studiepunten = Integer.valueOf(ects.toString());
       // int cijfer = Integer.valueOf(grade.toString());
        //int periode = Integer.valueOf(period.toString());
        ShowData();
        //courseModels.add(new CourseModel(code, name, 3, 55, 1));
        //courseModels.add(new CourseModel(code, name, 3, 55, 1));
        //mAdapter = new CourseListAdapter(CourseListActivity.this, 0, courseModels);
        //mListView.setAdapter(mAdapter);
// Even checken of dit goed binnen komt
       // Toast.makeText(getApplicationContext(), name,
       //         Toast.LENGTH_LONG).show();
    }

    public void ShowData()
    {
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //sortorder op periode
        String order = STUDIEJAAR + ", " + PERIOD;

        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, projection, null, null, null, null, order);
        //skip lege elementen die misschien eerst staan.
        rs.moveToFirst();
        if(rs.getCount() == 0)
        {
            Toast.makeText(this,
                    "geen database beschikbaar",
                    Toast.LENGTH_LONG).show();
        }
        else {
            //gooi  het in een loop en lees ze stuk voor stk uit
            for(int a =0;a <rs.getCount(); a++) {
                String code = (String) rs.getString(rs.getColumnIndex(TAG_CODE));
                String naam = (String) rs.getString(rs.getColumnIndex(TAG_VAK));
                int ects = (Integer) rs.getInt(rs.getColumnIndex(TAG_ECTS));
                double grade = (Double) rs.getDouble(rs.getColumnIndex(TAG_GRADE));
                int period = (Integer) rs.getInt(rs.getColumnIndex(TAG_PERIOD));
                int studiejaar = (Integer) rs.getInt(rs.getColumnIndex(TAG_STUDIEJAAR));

                //add opgehaalde data in de model
                courseModels.add(new CourseModel(code,naam, ects,grade, period,studiejaar));

                //ga naar de volgende in de rij.
                rs.moveToNext();
            }
            //zet listview items etc nadat het geladen is uit de e database
            mListView = (ListView) findViewById(R.id.my_list_view);
            mAdapter = new CourseListAdapter(CourseListActivity.this,0, courseModels);
            mListView.setAdapter(mAdapter);

        }
    }
}
