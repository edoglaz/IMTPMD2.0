package com.example.edje.studieoverzichttweepuntnul.Model;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.CODE;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.ECTS;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.GRADE;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.NAME;
import static com.example.edje.studieoverzichttweepuntnul.Database.DatabaseInfo.CourseColumn.PERIOD;

public class CourseModel {

    public String vakCode;
    public int aantalECTS;
    public double cijfer;
    public int periode;
    public String naam;
    public int studiejaar;
    public String notitie;

    public CourseModel(String vc, String n, int ae, double c, int p, int sj,String note) {
        this.vakCode = vc;
        this.naam = n;
        this.aantalECTS = ae;
        this.cijfer = c;
        this.periode = p;
        this.studiejaar = sj;
        this.notitie = note;

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

    public String getNotitie() {return String.valueOf(notitie);}

    public String getStudiejaar(){
        return String.valueOf(studiejaar);
    }

    public void setGrade(String name) {
        this.cijfer = cijfer;
    }

    // ADD GETTERS AND SETTERS - ONLY IF NEEDED !!
}