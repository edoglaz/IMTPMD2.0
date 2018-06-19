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
import android.widget.Toast;

import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;


/**
 * Created by Edo on 14-6-2018.
 */
public class CijferAanpassen extends AppCompatActivity {


    String[] projection = {DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.ECTS, DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD};
    String LABEL_VAK = "name";
    String LABEL_CODE = "code";
    String LABEL_ECTS = "ects";
    String LABEL_GRADE = "grade";
    String LABEL_PERIOD = "period";
    String LABEL_STUDIEJAAR = "studiejaar";
    String LABEL_NOTITIE = "notitie";

    private CourseListAdapter mAdapter;
    Button opslaan;
    Button annuleren;

    private String name;
    private int ect;
    private int period;
    private double grade;
    private String code;
    private String note;
    private String titel;
    private Cursor rs=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cijfer_aanpassen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);


        // haal labelgegevens via een bundel uit de vorige klasse
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            name = bundle.getString(LABEL_VAK);
            code = bundle.getString(LABEL_CODE);
            ect = bundle.getInt(LABEL_ECTS);
            period = bundle.getInt(LABEL_PERIOD);
            grade = bundle.getDouble(LABEL_GRADE);
            note = bundle.getString(LABEL_NOTITIE);


        }
        // zet de titel als de code uit de vorige klasse
        toolbar.setTitle(code);
        // haal de notitie uit de vorige klasse
        String note1= String.valueOf(note);
        // als de notitie geen data bevat vul de edittext dan met een standaard tekst
        if(note1.equals("null")) {
            TextView notitietekst = (TextView) findViewById(R.id.notitie);
            notitietekst.setHint("Schrijf hier uw notitie:");
        }
        // als de notitie wel gevuld is laat deze dan zien
        else{
            TextView notitietekst = (TextView) findViewById(R.id.notitie);
            notitietekst.setText(String.valueOf(note));
        }
        TextView vakgrade = (TextView) findViewById(R.id.cijferaanpassen);
        //zet de tekst van het cijfer
        vakgrade.setText(String.valueOf(grade));

    }


    public void annuleren(View v)
    {

        // als er op de knop met Annuleren gedruk wordt wordt deze klasse gesloten en gaat hij terug naar de vorige
        finish();
    }
    public void opslaan(View v)
    {
        //zorg dat de data opgeslagen wordt
        saveData();
        // maak een intent die gaat naar de mainactivity
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        // laat een toast zien met de teskt dat de gegevens zijn opgeslagen

        Toast.makeText(getApplicationContext(), "Je data is opgeslagen!",
                Toast.LENGTH_LONG).show();
        //start de activity
        startActivity(i);

        finish();
    }


    public void saveData() {

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        EditText cijfer = (EditText) findViewById(R.id.cijferaanpassen);
        EditText notitie = (EditText) findViewById(R.id.notitie);
        cijfer.setSelection(cijfer.getText().length());
        double nieuwecijfer = Double.parseDouble(cijfer.getText().toString());
        String notitie1 = notitie.getText().toString();

        //vul de database met de nieuwe gegevens die net zijn ingevoerd
        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CourseColumn.NAME, name);
        values.put(DatabaseInfo.CourseColumn.ECTS, ect);
        values.put(DatabaseInfo.CourseColumn.GRADE, nieuwecijfer);
        values.put(DatabaseInfo.CourseColumn.PERIOD, period);
        values.put(DatabaseInfo.CourseColumn.NOTITIE, notitie1);



        String[] args = {name};
        // update de database waar de CourseColumn naam hetzelfde is als de naam van de klasse waar hij zich nu in bevindt
        db.update(DatabaseInfo.CourseTables.COURSE, values, DatabaseInfo.CourseColumn.NAME + "=?", args);


    }
}