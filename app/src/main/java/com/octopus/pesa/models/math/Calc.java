package com.octopus.pesa.models.math;

import java.util.Date;

/**
 * Created by octopus on 6/27/16.
 */
public class Calc {
    private final int totalAngle = 360;

    public Calc() {

    }

    public static Date getDate(long millis) {
        return new Date(millis);
    }

    public static String getDateString(Date now, long mills) {
        return getDateString(now, getDate(mills));
    }

    public static String getDateString(Date now, Date then) {
        String string = null;
        if(now.getDay() == then.getDay()) {
            if ((now.getHours() - then.getHours()) == 1) {
                int md = now.getMinutes() - then.getMinutes();
                string = md+" minutes ago";
            }
            if(now.getHours() - then.getHours() > 1) {
                int hd = now.getHours() - then.getHours();
                string = hd+" hours ago";
            }
        }

        if ((now.getDay() - then.getDay()) == 1) {
            string = "Yesterday";
        }
        if ((now.getDay() - then.getDay()) == 1 && then.getHours() <= 12) {
            string = "Yesterday morning";
        }
        if ((now.getDay() - then.getDay()) == 1 && then.getHours() > 12 && then.getHours() <= 16) {
            string = "Yesterday afternoon";
        }
        if ((now.getDay() - then.getDay()) == 1 && then.getHours() > 16) {
            string = "Yesterday evening";
        }
        return string;
    }

    public float getAngle(float value, float total) {
        return (value / total) * totalAngle;
    }
}
