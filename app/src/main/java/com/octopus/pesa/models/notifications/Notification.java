package com.octopus.pesa.models.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

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

    private String title;
    private String message;

    public Notification(Context context) {
        this.setContext(context);
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

    public void setToastBundle(String title, String message) {
        this.title = title;
        this.message = message;
    }
    public void setDialogBundle(String title, String message) {
        alert = new PesaAlert(context);
        this.title = title;
        this.message = message;
    }


    public void displayDialog() {
        alert.setTitle(title);
        alert.setMessage(message);
        alert.showAlert();
    }

    public void showProgress() {

    }

    public void showToast() {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
