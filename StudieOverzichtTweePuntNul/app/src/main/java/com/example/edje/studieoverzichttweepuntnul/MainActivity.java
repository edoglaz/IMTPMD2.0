package com.example.edje.studieoverzichttweepuntnul;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    RequestQueue requestQueue;
    private String urlstring = "http://edoglpx292.292.axc.nl/subject_lijst.json";

    String TAG_VAK = "name";
    String TAG_CODE = "code";
    String TAG_ECTS = "ects";
    String TAG_GRADE = "grade";
    String TAG_PERIOD = "period";
    String TAG_STUDIEJAAR = "studiejaar";
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



        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.studiejaren));
        grafisch = getResources().getStringArray(R.array.studiejaren)[4];
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
                intent.putExtra("StudieJaar",listView.getItemAtPosition(i).toString());
                startActivity(intent);


                if(grafisch.equals(listView.getItemAtPosition(i).toString())){
                    Intent intent2 = new Intent(MainActivity.this, PieChartActivity.class);
                    startActivity(intent2);
                }


            }
        });
        listView.setAdapter(mAdapter);


        CheckDatabase();




    }
    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;


    }
    public void CheckDatabase() {        //check de datbase of er wat in staat.
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        String[] projection = {DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.NAME, DatabaseInfo.CourseColumn.ECTS, DatabaseInfo.CourseColumn.GRADE, DatabaseInfo.CourseColumn.PERIOD};
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, projection, null, null, null, null, null);
        //skip lege elementen die misschien eerst staan.
        rs.moveToFirst();

        //als er niks in database staat, en er wel internet is, haal data op
        if (rs.getCount() == 0 && isInternetAvailable()) {
            //als voor het ophalen van de data
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Data ophalen...");
            pDialog.setCancelable(false);
            //nodig voor het ophalen
            maakDatabase();
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        //geen internet en geen database
        if (rs.getCount() == 0 && !isInternetAvailable()) {
            Toast.makeText(this,
                    "kan geen database aanmaken, geen internet connectie",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void maakDatabase() {
        //showpDialog();


        JsonArrayRequest req = new JsonArrayRequest(urlstring,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject methode = (JSONObject) response
                                        .get(i);

                                String vak = methode.getString(TAG_VAK);

                                String holdects = methode.getString(TAG_ECTS);
                                String holdperiod = methode.getString(TAG_PERIOD);
                                String holdstudiejaar = methode.getString(TAG_STUDIEJAAR);
                                String code = methode.getString(TAG_CODE);

                               int studiejaar = Integer.parseInt(holdstudiejaar);
                                int ects = Integer.parseInt(holdects);
                                //cijfer wordt standaard 0. niet ingevuld.
                                double grade = 0.0;
                                int period = Integer.parseInt(holdperiod);


                                DatabaseHelper dbHelper = DatabaseHelper.getHelper(getApplicationContext());

                                //store values en schrijf weg ind e database
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
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        //hidepDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hidepDialog();
            }
        });
        // Adding request to request queue
        JSONcontroller.getInstance().addToRequestQueue(req);

    }


/*
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    /*

     */

}
