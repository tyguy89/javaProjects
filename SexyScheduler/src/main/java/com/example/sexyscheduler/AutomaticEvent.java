package com.example.sexyscheduler;

import java.io.Serializable;
import java.util.ArrayList;

public class AutomaticEvent extends AppointmentEvent implements Serializable {

    public ArrayList<AutomaticEvent> siblings;
    public AppointmentEvent main = this;
    public boolean linked;


    public AutomaticEvent(String title, String tag, boolean linked, String start, String end) {
        super(title, start, end, tag,new MyDay("Monday",1,2));
        this.linked = linked;
    }

    public void switchLinked() {this.linked = !this.linked;}

    public void addSiblings(ArrayList<AutomaticEvent> s) {siblings = s;}
}
