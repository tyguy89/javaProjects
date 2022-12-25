package com.example.sexyscheduler;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Notif extends HBox {
    CalendarModel model;

    public Notif(CalendarModel model){
        this.model = model;
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                MyDay day = model.getDayByNames(LocalDateTime.now().getYear(),model.mapMonthToString.get(LocalDateTime.now().getMonth()),LocalDateTime.now().getDayOfMonth());
                for(EventBase event: day.events) {
                    String start = null;
                    if (event instanceof AppointmentEvent) {
                        start = ((AppointmentEvent) event).start;
                    } else if (event instanceof DeadlineEvent) {
                        start = ((DeadlineEvent) event).time;
                    }
                    if (start != null) {
                        String[] st = start.split(":");
                        LocalTime time1 = LocalTime.now();
                        LocalTime time2 = LocalTime.of(Integer.parseInt(st[0]), Integer.parseInt(st[1]));
                        if ((time1.until(time2, ChronoUnit.MINUTES) <= 30) && (time1.until(time2, ChronoUnit.MINUTES) > 0)) {
                            if (!event.notified) {
                                Notifications.create()
                                        .title(event.title)
                                        .text("starts at: " + start)
                                        .hideAfter(new Duration(5000))
                                        .show();
                                event.notified = true;
                            }
                        }
                    }
                }
            }
        };
        timer.start();

    }

    public void setModel(CalendarModel model) {
        this.model = model;
    }
}
