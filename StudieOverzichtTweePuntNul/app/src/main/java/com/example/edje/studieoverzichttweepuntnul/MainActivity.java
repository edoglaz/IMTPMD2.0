package com.example.edje.studieoverzichttweepuntnul;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseHelper;
import com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);


        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CourseColumn.NAME, "Inf Keuze Programming Mobile Devices");
        values.put(DatabaseInfo.CourseColumn.ECTS, "3");
        values.put(DatabaseInfo.CourseColumn.CODE, "IKPMD");
        values.put(DatabaseInfo.CourseColumn.GRADE, "5.5");

// INSERT dit values object in DE (ZELFGEMAAKTE) RIJ COURSE,
        dbHelper.insert(DatabaseInfo.CourseTables.COURSE, null, values);

        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, new String[]{"*"}, null, null, null, null, null);

        rs.moveToFirst();   // Skip : de lege elementen vooraan de rij.
// Maar : de rij kan nog steeds leeg zijn
// Hoe : lossen we dit op ??

// Haalt de name uit de resultset
        String name = (String) rs.getString(rs.getColumnIndex("ects"));

// Even checken of dit goed binnen komt
        Toast.makeText(getApplicationContext(), name,
                Toast.LENGTH_LONG).show();
    }
}
