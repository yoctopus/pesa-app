package com.octopus.pesa.models.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.octopus.pesa.models.math.Calc;

/**
 * Created by octopus on 6/27/16.
 */
public class BalanceChart extends View {
    int green = Color.parseColor("#388e3c");
    int orange = Color.parseColor("#f9a825");
    RectF rectf;
    int temp = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int height, width;
    private float income, expense, total;

    public BalanceChart(Context context) {
        super(context);
    }

    public BalanceChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getExpense() {
        return expense;
    }

    public void setExpense(float expense) {
        this.expense = expense;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int top = 0, left = 0, right = 0, bottom = 0;
        this.total = income + expense;
        int measurelenght;
        if (super.getHeight() <= super.getWidth()) {
            measurelenght = super.getHeight();
            top = 0;
            left = (super.getWidth() / 2) - (measurelenght / 2);
            right = left + measurelenght;
            bottom = top + measurelenght;
        } else if (super.getHeight() > super.getWidth()) {
            measurelenght = super.getWidth();
            top = (getHeight() / 2) - (measurelenght / 2);
            left = 0;
            right = left + measurelenght;
            bottom = top + measurelenght;
        }
        rectf = new RectF(left, top, right, bottom);
        float angleIncome = getAngle(getIncome(), total);
        float angleExpense = getAngle(getExpense(), total);
        paint.setColor(green);
        canvas.drawArc(rectf, 0, angleIncome, true, paint);

        paint.setColor(orange);
        canvas.drawArc(rectf, angleIncome, angleExpense, true, paint);

    }

    private float getAngle(float Value, float total) {
        return new Calc().getAngle(Value, total);
    }
}





















