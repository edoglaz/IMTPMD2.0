package com.example.edje.studieoverzichttweepuntnul;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;
import com.example.edje.studieoverzichttweepuntnul.Model.CourseModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
    String studiejaar3;
    String studiejaar4;
    String keuzevakken;
    Toolbar toolbar;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId()==R.id.my_list_view) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);

        }

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {


        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch(item.getItemId()) {
            case R.id.cijferaanpassen:
                Intent intent = new Intent(CourseListActivity.this, CijferAanpassen.class);
                startActivity(intent);
                return true;
            case R.id.notitietoevoegen:

                return true;
            case R.id.notitiebekijken:

            default:
                return super.onContextItemSelected(item);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        mListView = (ListView) findViewById(R.id.my_list_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        registerForContextMenu(mListView);

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

        studiejaar3 = getResources().getStringArray(R.array.studiejaren)[2];
        if (toolbar.getTitle().equals(studiejaar3))
        {

            ShowDataStudieJaar3();

        }

        studiejaar4 = getResources().getStringArray(R.array.studiejaren)[3];
        if (toolbar.getTitle().equals(studiejaar4))
        {

            ShowDataStudieJaar4();

        }


        keuzevakken = getResources().getStringArray(R.array.studiejaren)[4];
        if (toolbar.getTitle().equals(keuzevakken))
        {

            ShowDataKeuzevakken();

        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //krijg de opgeslagen data van de positie
                CourseModel data = (CourseModel) parent.getItemAtPosition(position);

                //haal de data eruit
                String name = data.naam;
                int ects = data.aantalECTS;
                double grade = data.cijfer;
                int period = data.periode;
                //stuur data door van het aangeklikte item
                Intent i = new Intent(getApplicationContext(), CijferAanpassen.class);
                i.putExtra(TAG_VAK,name);
                i.putExtra(TAG_ECTS,ects);
                i.putExtra(TAG_GRADE,grade);
                i.putExtra(TAG_PERIOD,period);
                startActivity(i);
            }
        });



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
                HashSet<String> hashSet = new HashSet<String>();


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

    public void ShowDataStudieJaar3()
    {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //sortorder op periode
        String stj3 = "3";
        String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
        //String order = PERIOD + ", " ;
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + TAG_STUDIEJAAR + "='" + stj3 + "'", projection,null,null,null,null,order);

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

    public void ShowDataStudieJaar4()
    {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //sortorder op periode
        String stj4 = "4";
        String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
        //String order = PERIOD + ", " ;
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + TAG_STUDIEJAAR + "='" + stj4 + "'", projection,null,null,null,null,order);

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
    public void ShowDataKeuzevakken()
    {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //sortorder op periode
        String stj5 = "5";
        String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
        //String order = PERIOD + ", " ;
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + TAG_STUDIEJAAR + "='" + stj5 + "'", projection,null,null,null,null,order);

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
