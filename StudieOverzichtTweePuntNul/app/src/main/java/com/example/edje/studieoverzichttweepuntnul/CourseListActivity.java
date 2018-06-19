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
import android.widget.PopupMenu;
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

import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.NOTITIE;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.STUDIEJAAR;
/**
 * Created by Edo on 14-6-2018.
 */
public class CourseListActivity extends AppCompatActivity {
    private ListView mListView;
    private CourseListAdapter mAdapter;
    private List<CourseModel> courseModels = new ArrayList<>();
    String[] projection = {DatabaseInfo.CourseColumn.NAME,DatabaseInfo.CourseColumn.CODE, DatabaseInfo.CourseColumn.ECTS,DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD, STUDIEJAAR, NOTITIE};

    String LABEL_VAK = "name";
    String LABEL_CODE = "code";
    String LABEL_ECTS = "ects";
    String LABEL_GRADE = "grade";
    String LABEL_PERIOD = "period";
    String LABEL_STUDIEJAAR = "studiejaar";
    String LABEL_NOTITIE = "notitie";
    String studiejaar1;
    String studiejaar2;
    String studiejaar3;
    String studiejaar4;
    String keuzevakken;
    String nothing;
    String titel;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        mListView = (ListView) findViewById(R.id.my_list_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        // haal de bundel uit MainActivity en zet de titel naar het studiejaar dat wordt meegegeven.
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            toolbar.setTitle(bundle.getString("StudieJaar"));


        }

        //String studiejaar1 is het eerste item uit de stringarray
        studiejaar1 = getResources().getStringArray(R.array.studiejaren)[0];
        //als studiejaar1 overeenkomt met de titel laat dan functie ShowDataStudiejaar1 zien. hierdoor wordt alleen de data laten zien van studiejaar 1
        if (toolbar.getTitle().equals(studiejaar1))
        {

                ShowDataStudieJaar1();
                onResume();
        }
        //String studiejaar2 is het tweede item uit de stringarray
        studiejaar2 = getResources().getStringArray(R.array.studiejaren)[1];
        //als studiejaar2 overeenkomt met de titel laat dan functie ShowDataStudiejaar2 zien. hierdoor wordt alleen de data laten zien van studiejaar 2
        if (toolbar.getTitle().equals(studiejaar2))
        {

            ShowDataStudieJaar2();
            onResume();
        }
        //String studiejaar3 is het derde item uit de stringarray
        studiejaar3 = getResources().getStringArray(R.array.studiejaren)[2];
        //als studiejaar3 overeenkomt met de titel laat dan functie ShowDataStudiejaar3 zien. hierdoor wordt alleen de data laten zien van studiejaar 3
        if (toolbar.getTitle().equals(studiejaar3))
        {

            ShowDataStudieJaar3();
            onResume();
        }
        //String studiejaar4 is het vierde item uit de stringarray
        studiejaar4 = getResources().getStringArray(R.array.studiejaren)[3];
        //als studiejaar4 overeenkomt met de titel laat dan functie ShowDataStudiejaar4 zien. hierdoor wordt alleen de data laten zien van studiejaar 4
        if (toolbar.getTitle().equals(studiejaar4))
        {

            ShowDataStudieJaar4();
            onResume();

        }

        //String keuzevakken is het vijfde item uit de stringarray
        keuzevakken = getResources().getStringArray(R.array.studiejaren)[4];
        //als keuzevakken overeenkomt met de titel laat dan functie ShowDataKeuzevakken zien. hierdoor wordt alleen de data laten zien van de keuzevakken
        if (toolbar.getTitle().equals(keuzevakken))
        {

            ShowDataKeuzevakken();
            onResume();

        }
        //hieronder is de onclicklistener van de listview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                CourseModel data = (CourseModel) parent.getItemAtPosition(position);

                //Hieronder wordt de data uit het coursemodel gehaald van het item waar op geklikt is
                String name = data.naam;
                int ects = data.aantalECTS;
                double grade = data.cijfer;
                int period = data.periode;
                String notitie = data.notitie;
                String code = data.vakCode;

                //hieronder wordt data van het aangeklikte item doorgestuurd aan de volgende klasse
                Intent i = new Intent(getApplicationContext(), CijferAanpassen.class);
                i.putExtra(LABEL_VAK,name);
                i.putExtra(LABEL_CODE,code);
                i.putExtra(LABEL_ECTS,ects);
                i.putExtra(LABEL_GRADE,grade);
                i.putExtra(LABEL_PERIOD,period);
                i.putExtra(LABEL_NOTITIE,notitie);
                i.putExtra("titel",getTitle().toString());
                startActivity(i);







            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        // fetch updated data
        mAdapter.updateList(courseModels);
        mAdapter.notifyDataSetChanged();
    }

    public void ShowDataStudieJaar1()
        {

            DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
            //Door een string te maken van 1 en deze te laten vergelijken met het LABEL_STUDIEJAAR worden er alleen gegevens uit de database gehaald waar het studie jaar 1 is.
            String stj1 = "1";
            //Sorteer de gegevens op periode
            String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
            // haal gegevens uit de database waar het label overeenkomt met de string sjt1
            Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + LABEL_STUDIEJAAR + "='" + stj1 + "'", projection,null,null,null,null,order);


            rs.moveToFirst();
            // kijk of de database leeg is
            if (rs.getCount() == 0) {
                Toast.makeText(this,
                        "geen database beschikbaar",
                        Toast.LENGTH_LONG).show();
            } else {
                //als de database niet leeg is, haal dan de volgende gegevens eruit
                for (int a = 0; a < rs.getCount(); a++) {

                    String code = (String) rs.getString(rs.getColumnIndex(LABEL_CODE));
                    String naam = (String) rs.getString(rs.getColumnIndex(LABEL_VAK));
                    String notitie = (String) rs.getString(rs.getColumnIndex(LABEL_NOTITIE));
                    int ects = (Integer) rs.getInt(rs.getColumnIndex(LABEL_ECTS));
                    double grade = (Double) rs.getDouble(rs.getColumnIndex(LABEL_GRADE));
                    int period = (Integer) rs.getInt(rs.getColumnIndex(LABEL_PERIOD));
                    int studiejaar = (Integer) rs.getInt(rs.getColumnIndex(LABEL_STUDIEJAAR));


                    //voeg de opgehaalde data toe aan het courseModel

                    courseModels.add(new CourseModel(code, naam, ects, grade, period, studiejaar, notitie));
                rs.moveToNext();



                }



                // zet de listview en vul hem met de gegevens
                mListView = (ListView) findViewById(R.id.my_list_view);

                mAdapter = new CourseListAdapter(CourseListActivity.this, 0, courseModels);

                mListView.setAdapter(mAdapter);

            }

    }

    public void ShowDataStudieJaar2()
    {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //Door een string te maken van 2 en deze te laten vergelijken met het LABEL_STUDIEJAAR worden er alleen gegevens uit de database gehaald waar het studie jaar 2 is.
        String stj2 = "2";
        //Sorteer de gegevens op periode
        String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
        // haal gegevens uit de database waar het label overeenkomt met de string sjt1
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + LABEL_STUDIEJAAR + "='" + stj2 + "'", projection,null,null,null,null,order);


        rs.moveToFirst();
        // kijk of de database leeg is
        if (rs.getCount() == 0) {
            Toast.makeText(this,
                    "geen database beschikbaar",
                    Toast.LENGTH_LONG).show();
        } else {
            //als de database niet leeg is, haal dan de volgende gegevens eruit
            for (int a = 0; a < rs.getCount(); a++) {

                String code = (String) rs.getString(rs.getColumnIndex(LABEL_CODE));
                String naam = (String) rs.getString(rs.getColumnIndex(LABEL_VAK));
                String notitie = (String) rs.getString(rs.getColumnIndex(LABEL_NOTITIE));
                int ects = (Integer) rs.getInt(rs.getColumnIndex(LABEL_ECTS));
                double grade = (Double) rs.getDouble(rs.getColumnIndex(LABEL_GRADE));
                int period = (Integer) rs.getInt(rs.getColumnIndex(LABEL_PERIOD));
                int studiejaar = (Integer) rs.getInt(rs.getColumnIndex(LABEL_STUDIEJAAR));

                //voeg de opgehaalde data toe aan het courseModel

                courseModels.add(new CourseModel(code, naam, ects, grade, period, studiejaar, notitie));


                rs.moveToNext();

            }
            // zet de listview en vul hem met de gegevens
            mListView = (ListView) findViewById(R.id.my_list_view);
            mAdapter = new CourseListAdapter(CourseListActivity.this, 0, courseModels);
            mListView.setAdapter(mAdapter);

        }

    }

    public void ShowDataStudieJaar3()
    {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //Door een string te maken van 3 en deze te laten vergelijken met het LABEL_STUDIEJAAR worden er alleen gegevens uit de database gehaald waar het studie jaar 3 is.
        String stj3 = "3";
        //Sorteer de gegevens op periode
        String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
        // haal gegevens uit de database waar het label overeenkomt met de string sjt1
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + LABEL_STUDIEJAAR + "='" + stj3 + "'", projection,null,null,null,null,order);


        rs.moveToFirst();
        // kijk of de database leeg is
        if (rs.getCount() == 0) {
            Toast.makeText(this,
                    "geen database beschikbaar",
                    Toast.LENGTH_LONG).show();
        } else {
            //als de database niet leeg is, haal dan de volgende gegevens eruit
            for (int a = 0; a < rs.getCount(); a++) {

                String code = (String) rs.getString(rs.getColumnIndex(LABEL_CODE));
                String naam = (String) rs.getString(rs.getColumnIndex(LABEL_VAK));
                String notitie = (String) rs.getString(rs.getColumnIndex(LABEL_NOTITIE));
                int ects = (Integer) rs.getInt(rs.getColumnIndex(LABEL_ECTS));
                double grade = (Double) rs.getDouble(rs.getColumnIndex(LABEL_GRADE));
                int period = (Integer) rs.getInt(rs.getColumnIndex(LABEL_PERIOD));
                int studiejaar = (Integer) rs.getInt(rs.getColumnIndex(LABEL_STUDIEJAAR));

                //voeg de opgehaalde data toe aan het courseModel

                courseModels.add(new CourseModel(code, naam, ects, grade, period, studiejaar, notitie));


                rs.moveToNext();

            }
            // zet de listview en vul hem met de gegevens
            mListView = (ListView) findViewById(R.id.my_list_view);
            mAdapter = new CourseListAdapter(CourseListActivity.this, 0, courseModels);
            mListView.setAdapter(mAdapter);

        }

    }

    public void ShowDataStudieJaar4()
    {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //Door een string te maken van 2 en deze te laten vergelijken met het LABEL_STUDIEJAAR worden er alleen gegevens uit de database gehaald waar het studie jaar 2 is.
        String stj4 = "4";
        //Sorteer de gegevens op periode
        String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
        // haal gegevens uit de database waar het label overeenkomt met de string sjt1
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + LABEL_STUDIEJAAR + "='" + stj4 + "'", projection,null,null,null,null,order);


        rs.moveToFirst();
        // kijk of de database leeg is
        if (rs.getCount() == 0) {
            Toast.makeText(this,
                    "geen database beschikbaar",
                    Toast.LENGTH_LONG).show();
        } else {
            //als de database niet leeg is, haal dan de volgende gegevens eruit
            for (int a = 0; a < rs.getCount(); a++) {

                String code = (String) rs.getString(rs.getColumnIndex(LABEL_CODE));
                String naam = (String) rs.getString(rs.getColumnIndex(LABEL_VAK));
                String notitie = (String) rs.getString(rs.getColumnIndex(LABEL_NOTITIE));
                int ects = (Integer) rs.getInt(rs.getColumnIndex(LABEL_ECTS));
                double grade = (Double) rs.getDouble(rs.getColumnIndex(LABEL_GRADE));
                int period = (Integer) rs.getInt(rs.getColumnIndex(LABEL_PERIOD));
                int studiejaar = (Integer) rs.getInt(rs.getColumnIndex(LABEL_STUDIEJAAR));

                //voeg de opgehaalde data toe aan het courseModel

                courseModels.add(new CourseModel(code, naam, ects, grade, period, studiejaar, notitie));


                rs.moveToNext();

            }
            // zet de listview en vul hem met de gegevens
            mListView = (ListView) findViewById(R.id.my_list_view);
            mAdapter = new CourseListAdapter(CourseListActivity.this, 0, courseModels);
            mListView.setAdapter(mAdapter);

        }

    }
    public void ShowDataKeuzevakken()
    {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        //Door een string te maken van 2 en deze te laten vergelijken met het LABEL_STUDIEJAAR worden er alleen gegevens uit de database gehaald waar het studie jaar 2 is.
        String stj5 = "5";
        //Sorteer de gegevens op periode
        String order = DatabaseInfo.CourseColumn.PERIOD + " ASC";
        // haal gegevens uit de database waar het label overeenkomt met de string sjt1
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE + " WHERE " + LABEL_STUDIEJAAR + "='" + stj5 + "'", projection,null,null,null,null,order);


        rs.moveToFirst();
        // kijk of de database leeg is
        if (rs.getCount() == 0) {
            Toast.makeText(this,
                    "geen database beschikbaar",
                    Toast.LENGTH_LONG).show();
        } else {
            //als de database niet leeg is, haal dan de volgende gegevens eruit
            for (int a = 0; a < rs.getCount(); a++) {

                String code = (String) rs.getString(rs.getColumnIndex(LABEL_CODE));
                String naam = (String) rs.getString(rs.getColumnIndex(LABEL_VAK));
                String notitie = (String) rs.getString(rs.getColumnIndex(LABEL_NOTITIE));
                int ects = (Integer) rs.getInt(rs.getColumnIndex(LABEL_ECTS));
                double grade = (Double) rs.getDouble(rs.getColumnIndex(LABEL_GRADE));
                int period = (Integer) rs.getInt(rs.getColumnIndex(LABEL_PERIOD));
                int studiejaar = (Integer) rs.getInt(rs.getColumnIndex(LABEL_STUDIEJAAR));

                //voeg de opgehaalde data toe aan het courseModel

                courseModels.add(new CourseModel(code, naam, ects, grade, period, studiejaar, notitie));


                rs.moveToNext();

            }
            // zet de listview en vul hem met de gegevens
            mListView = (ListView) findViewById(R.id.my_list_view);
            mAdapter = new CourseListAdapter(CourseListActivity.this, 0, courseModels);
            mListView.setAdapter(mAdapter);

        }

    }

}
