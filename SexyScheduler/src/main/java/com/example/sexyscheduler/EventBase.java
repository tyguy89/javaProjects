package com.example.sexyscheduler;

import java.io.Serializable;
import java.util.UUID;

public abstract class EventBase implements Serializable {
    public String title;

    public String serial;
    public String tag;
    boolean notified = false;

    public EventBase(String title, String tag){
        this.title = title;


        this.tag = tag;
        this.serial = UUID.randomUUID().toString();

    }

    @Override
    public String toString() {
        return "EventBase{" +
                "title='" + title + '\'' +
                ", serial='" + serial + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
