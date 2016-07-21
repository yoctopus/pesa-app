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

    public float getAngle(float value, float total) {
        return (value / total) * totalAngle;
    }
}
