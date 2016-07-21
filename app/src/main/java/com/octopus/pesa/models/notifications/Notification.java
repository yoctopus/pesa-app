package com.octopus.pesa.models.notifications;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by octopus on 7/16/16.
 */
public class Notification {
    public static final int NOTIFICATIONBAR = 0;
    public static final int DIALOG = 1;
    public static final int PROGRESSBAR = 2;
    public static final int TOAST = 3;
    public static final int SOUND = 4;
    public static final int VIBRATE = 5;
    private Context context;

    private PesaAlert alert;
    private AlertDialog dialog;

    public Notification(Context context) {
        this.setContext(context);
        setAlert(new PesaAlert(context));
    }

    public void notify(final int type) {
        switch (type) {
            case NOTIFICATIONBAR: {
                showNotification();
                break;
            }
            case DIALOG: {
                displayDialog();
                break;
            }
            case PROGRESSBAR: {
                showProgress();
                break;
            }
            case TOAST: {
                showToast();
                break;
            }
            case SOUND: {
                soundTone();
                break;
            }
            case VIBRATE: {
                vibrate();
                break;
            }
        }
    }


    public void displayDialog() {
        alert.showAlert();
    }

    public void showProgress() {

    }

    public void showToast() {

    }

    public void soundTone() {

    }

    public void vibrate() {

    }

    public void showNotification() {

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public PesaAlert getAlert() {
        return alert;
    }

    public void setAlert(PesaAlert alert) {
        this.alert = alert;
    }
}
