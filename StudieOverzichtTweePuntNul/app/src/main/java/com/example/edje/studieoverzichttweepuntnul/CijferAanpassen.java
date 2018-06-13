package com.example.edje.studieoverzichttweepuntnul;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;


/**
 * Created by sandor on 22-3-2016.
 */
public class CijferAanpassen extends AppCompatActivity {
    //tags voor de data
    //database data
    String[] projection = {DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.ECTS, DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD};
    String TAG_VAK = "name";
    String TAG_ECTS = "ects";
    String TAG_GRADE = "grade";
    String TAG_PERIOD = "period";
    String TAG_NOTITIE = "notitie";
    //String WHERE = name = "name" ;
    private CourseListAdapter mAdapter;
    Button opslaan;
    Button annuleren;
    //data van item
    private String name;
    private int ect;
    private int period;
    private double grade;
    private String note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cijfer_aanpassen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


       // Button opslaan = (Button) this.findViewById(R.id.opslaan);
        //Button annuleren = (Button) this.findViewById(R.id.opslaan);

        //krijg de data
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            name = bundle.getString(TAG_VAK);
            ect = bundle.getInt(TAG_ECTS);
            period = bundle.getInt(TAG_PERIOD);
            grade = bundle.getDouble(TAG_GRADE);
            note = bundle.getString(TAG_NOTITIE);
        }

        String note1= String.valueOf(note);
        //toolbar.setTitle(name);
        if(note1.equals("null")) {
            TextView notitietekst = (TextView) findViewById(R.id.notitie);
            notitietekst.setHint("Schrijf hier uw notitie:");
        }
        else{
            TextView notitietekst = (TextView) findViewById(R.id.notitie);
            notitietekst.setText(String.valueOf(note));
        }
        TextView vakgrade = (TextView) findViewById(R.id.cijferaanpassen);
        vakgrade.setText(String.valueOf(grade));
/*
        opslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                setResult(RESULT_OK, intent);

                onBackPressed();
            }
        });

        annuleren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                setResult(RESULT_OK, intent);

                onBackPressed();

            }
        });

/*

 */
    }


    public void annuleren(View v)
    {
        //ga terug naar invoer.
        //Intent i = new Intent(getApplicationContext(), CourseListActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(i);

        finish();
    }
    public void opslaan(View v)
    {
        //zorg dat de data opgeslagen wordt
        saveData();
        //Intent i = new Intent(getApplicationContext(), CourseListActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(i);

        finish();
    }
/*

 */

    public void saveData() {
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //store values en schrijf weg ind e database
        EditText cijfer = (EditText) findViewById(R.id.cijferaanpassen);
        EditText notitie = (EditText) findViewById(R.id.notitie);
        cijfer.setSelection(cijfer.getText().length());
        double nieuwecijfer = Double.parseDouble(cijfer.getText().toString());
        String notitie1 = notitie.getText().toString();

        //name hoeft eigenlijk niet meegegeven te worden
        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CourseColumn.NAME, name);
        values.put(DatabaseInfo.CourseColumn.ECTS, ect);
        values.put(DatabaseInfo.CourseColumn.GRADE, nieuwecijfer);
        values.put(DatabaseInfo.CourseColumn.PERIOD, period);
        values.put(DatabaseInfo.CourseColumn.NOTITIE, notitie1);


        //update de tabel waar name gelijk is aan de name in de tabelnaam
        String[] args = {name};

        db.update(DatabaseInfo.CourseTables.COURSE, values, DatabaseInfo.CourseColumn.NAME + "=?", args);


    }
}