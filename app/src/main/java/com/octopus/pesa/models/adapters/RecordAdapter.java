package com.octopus.pesa.models.adapters;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.octopus.pesa.R;
import com.octopus.pesa.models.Item;
import com.octopus.pesa.models.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by octopus on 6/28/16.
 */
public class RecordAdapter extends ArrayAdapter {
    private ArrayList<Record> records;
    private ImageView imageView;
    private TextView item;
    private TextView amount;
    private TextView date;
    private TextView type;

    public RecordAdapter(Context context, int resource, ArrayList<Record> objects) {
        super(context, resource, objects);
        this.records = objects;
    }

    private void add(Record r) {
        getRecords().add(r);
        super.add(r);
    }
    @Override
    public int getCount() {
        return getRecords().size();
    }

    @Override
    public Object getItem(int i) {

        return getRecords().get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Record r = getRecords().get(i);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.record_row,viewGroup, false);
        setItem((TextView) v.findViewById(R.id.item));
        setAmount((TextView) v.findViewById(R.id.amount));
        setDate((TextView) v.findViewById(R.id.date));
        type = (TextView) v.findViewById(R.id.typeText);
        setImageView((ImageView) v.findViewById(R.id.list_image));

        getItem().setText(r.getNameItem());
        getAmount().setText(""+r.getAmount());
        getDate().setText(r.getDateString());
        if(r.getType() == Item.EXPENSE_TYPE) {
            type.setText("Expense");
        }
        else if (r.getType() == Item.INCOME_TYPE) {
           type.setText("Income");
        }


        return v;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getItem() {
        return item;
    }

    public void setItem(TextView item) {
        this.item = item;
    }

    public TextView getAmount() {
        return amount;
    }

    public void setAmount(TextView amount) {
        this.amount = amount;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }
}
