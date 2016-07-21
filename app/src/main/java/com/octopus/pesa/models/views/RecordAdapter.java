package com.octopus.pesa.models.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.octopus.pesa.R;

/**
 * Created by octopus on 6/30/16.
 */
public class RecordAdapter extends ArrayAdapter<RecordView> {
    public RecordAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordView recordView = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(R.layout.record_row, parent);
        }

        return row;
    }
}
