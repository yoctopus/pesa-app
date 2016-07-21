package com.octopus.pesa.models.views;

import android.widget.ImageView;

import com.octopus.pesa.models.Record;

/**
 * Created by octopus on 6/28/16.
 */
public class RecordView {
    private Record record;
    private ImageView InComeImage, expenseImage;

    public RecordView() {

    }


    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }


}
