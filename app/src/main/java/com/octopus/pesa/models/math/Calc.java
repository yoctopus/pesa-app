package com.octopus.pesa.models.math;

import android.text.format.DateUtils;

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

    public static String getDateString(long mills) {
        return getDateString(getDate(mills));
    }

    public static String getDateString(Date then) {
        Date now = new Date();
        long df = now.getTime() - then.getTime();
        long dfs = df / DateUtils.SECOND_IN_MILLIS;
        String stringdf = DateUtils.formatElapsedTime(dfs);
        String[] utils = stringdf.split(":");
        String string = null;
        if (utils.length == 2) {
            if (Integer.parseInt(utils[0]) == 0) {
                string = utils[1] +" seconds ago";
            }
            if (Integer.parseInt(utils[0]) > 0) {
                string = utils[0] +" minutes ago";
            }
        }
        else if (utils.length == 3) {
            if (Integer.parseInt(utils[0]) > 0) {
                string = utils[0] +" hours ago";
            }
            if (Integer.parseInt(utils[0]) > 24) {
                int days = Integer.parseInt(utils[0])/24;
                string = days+" days ago";
            }
        }

        /*if (now.getHours() == then.getHours()) {
            if (then.getMinutes() == now.getMinutes()) {
                int sd = now.getSeconds() - then.getSeconds();
                stringdf = sd + " seconds ago";
            } else {
                int md = now.getMinutes() - then.getMinutes();
                stringdf = md + " minutes ago";
            }

        } else if (now.getHours() > then.getHours()) {
            int hd = now.getHours() - then.getHours();
            stringdf = hd + " hours ago";
        } else if ((now.getDate() - then.getDate()) == 1) {
            stringdf = "Yesterday";
            if (then.getHours() <= 12) {
                stringdf += " morning";
            } else if (then.getHours() > 12 && then.getHours() <= 16) {
                stringdf += " afternoon";
            } else if (then.getHours() > 16) {
                stringdf += " evening";
            }
        } else if (now.getDay() - then.getDay() > 1) {
            int dd = now.getDay() - then.getDay();
            stringdf = dd + " days ago";
        }*/

        return string;
    }

    public float getAngle(float value, float total) {
        return (value / total) * totalAngle;
    }
}
