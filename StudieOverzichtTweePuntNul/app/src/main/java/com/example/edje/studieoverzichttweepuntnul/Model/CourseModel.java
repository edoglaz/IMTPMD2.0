package com.example.edje.studieoverzichttweepuntnul.Model;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.CODE;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.ECTS;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.GRADE;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.NAME;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.PERIOD;

public class CourseModel {

    private String vakCode;
    private int aantalECTS;
    private int cijfer;
    private int periode;
    private String naam;

    public CourseModel(String vc, String n, int ae, int c, int p) {
        this.vakCode = vc;
        this.naam = n;
        this.aantalECTS = ae;
        this.cijfer = c;
        this.periode = p;


    }

    public String getName(){
        return naam;
    }

    public String getEcts(){
        return String.valueOf(aantalECTS);
    }

    public String getGrade(){
        return String.valueOf(cijfer);
    }

    public String getPeriod(){
        return String.valueOf(periode);
    }

    public String getCode(){
        return String.valueOf(vakCode);
    }

    public void setGrade(String name) {
        this.cijfer = cijfer;
    }

    // ADD GETTERS AND SETTERS - ONLY IF NEEDED !!
}