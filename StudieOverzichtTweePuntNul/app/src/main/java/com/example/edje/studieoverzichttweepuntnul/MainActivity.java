package com.example.edje.studieoverzichttweepuntnul;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Edo on 14-6-2018.
 */
public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    RequestQueue requestQueue;
    //hier definieer ik de URL waar ik mijn JSON file heb opgeslagen
    private String urlstring = "http://edoglpx292.292.axc.nl/subject_lijst.json";

    // Hier definieer ik labels waarmee ik informatie naar andere klasses kan sturen
    String LABEL_VAK = "name";
    String LABEL_CODE = "code";
    String LABEL_ECTS = "ects";
    String LABEL_GRADE = "grade";
    String LABEL_PERIOD = "period";
    String LABEL_STUDIEJAAR = "studiejaar";
    Toolbar toolbar;
    ListView listView;
    ActionBar actionBar;
    String grafisch;
    //private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        listView = (ListView)findViewById(R.id.listView);


        //Hier wordt de listview gevult met strings uit de stringarray die ik heb gemaakt.
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.studiejaren));
        grafisch = getResources().getStringArray(R.array.studiejaren)[5];
        // Hieronder is de listview onclicklistener te vinden
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                // ik stel een intent in die naar de CourseListActivity gaat
                Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
                // de intent geeft "Studiejaar" mee aan de CourseListActivity klasse door de positie van het geklikte item om te zetten in een string
                intent.putExtra("StudieJaar",listView.getItemAtPosition(i).toString());
                startActivity(intent);

                //maar als er op het grafisch overzicht is geklikt moet er naar een andere klasse genavigeerd worden. Dit doet ik doormiddel van een if statement
                if(grafisch.equals(listView.getItemAtPosition(i).toString())){
                    Intent intent2 = new Intent(MainActivity.this, PieChartActivity.class);
                    intent2.putExtra("grafisch",listView.getItemAtPosition(i).toString());
                    startActivity(intent2);
                }


            }
        });

        listView.setAdapter(mAdapter);


        //Ik laat de functie CheckDatabase elke keer opstarten zodra deze activity is gestart. Hierdoor controleer ik de database en of er een internetconnectie is
        CheckDatabase();




    }
    //met de functie hieronder kijk ik of er internet beschikbaar is
    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;


    }
    //Hieronder staat de funtie CheckDatabase die checkt of er data opgehaald kan worden
    public void CheckDatabase() {
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        String[] projection = {DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.ECTS, DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD};
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, projection, null, null, null, null, null);
        //ga direct naar het eerste niet lege element
        rs.moveToFirst();

        //als de database leeg is en er internet is dan moet er data opgehaald worden
        if (rs.getCount() == 0 && isInternetAvailable()) {

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Er wordt data opgehaald.....");
            pDialog.setCancelable(false);
            //Hieronder roep ik de functie maakDatabase aan die daadwerkelijk de database maakt en vult
            maakDatabase();
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        //Als de database leeg is én er is geen internet dan kan er geen database gemaakt worden. Als de database al vol is en er is geen internet is er natuurlijk niks aan de hand.
        if (rs.getCount() == 0 && !isInternetAvailable()) {
            Toast.makeText(this,
                    "Het maken van de database is niet mogelijk, omdat er geen internet connectie is :(",
                    Toast.LENGTH_LONG).show();
        }
    }

// Hieronder maak ik de functie maakDatabase die de database maakt en vult via de URL waar ik mijn JSON bestand heb opgeslagen.
    public void maakDatabase() {


        //Maak een nieuw JsonArrayRequest met daarin de URL
        JsonArrayRequest req = new JsonArrayRequest(urlstring,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        try {
                            // Loop door elk json object
                            //ontleed elk json antwoord

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject methode = (JSONObject) response
                                        .get(i);

                                //ontvang jsonobjecten en maak er strings van
                                String vak = methode.getString(LABEL_VAK);

                                String holdects = methode.getString(LABEL_ECTS);
                                String holdperiod = methode.getString(LABEL_PERIOD);
                                String holdstudiejaar = methode.getString(LABEL_STUDIEJAAR);
                                String code = methode.getString(LABEL_CODE);
                                // sommige strings moeten eerst omgezet worden naar een integer of double
                               int studiejaar = Integer.parseInt(holdstudiejaar);
                                int ects = Integer.parseInt(holdects);
                                //cijfer wordt standaard 0. niet ingevuld.
                                double grade = 0.0;
                                int period = Integer.parseInt(holdperiod);

                                // roep de databasehelper aan
                                DatabaseHelper dbHelper = DatabaseHelper.getHelper(getApplicationContext());

                                //vul de database
                                ContentValues values = new ContentValues();
                                values.put(DatabaseInfo.CourseColumn.NAME, vak);
                                values.put(DatabaseInfo.CourseColumn.ECTS, ects);
                                values.put(DatabaseInfo.CourseColumn.GRADE, grade);
                                values.put(DatabaseInfo.CourseColumn.PERIOD, period);
                                values.put(DatabaseInfo.CourseColumn.CODE, code);
                                values.put(DatabaseInfo.CourseColumn.STUDIEJAAR, studiejaar);
                                dbHelper.insert(DatabaseInfo.CourseTables.COURSE, null, values);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // als er een error is of de JSON file is niet goed dan krijg je een bericht/
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        JSONcontroller.getInstance().addToRequestQueue(req);

    }




}
