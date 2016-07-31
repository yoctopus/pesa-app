package com.octopus.pesa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.octopus.pesa.PesaApp;
import com.octopus.pesa.R;
import com.octopus.pesa.models.TempData;
import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.notifications.Notification;

import java.util.ArrayList;

/**
 * A login screen that offers login via password
 * enter login pin
 * let the account verify the pin start the a transaction once the pin is entered to check
 * the pin in the database account table.
 * the transaction should call on transaction complete method.
 * start the main activity and finish this activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //user account
    private PesaApp app;
    private Notification notification;
    //textview holding the pin
    private TextView pinText;
    //buttons for entering the pin
    private ArrayList<Button> buttons = new ArrayList<>();

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        setPinText((TextView) findViewById(R.id.textviewPin));
        app = (PesaApp) getApplication();

        notification = new Notification(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        configureButtons();

    }

    private void configureButtons() {
        getButtons().add((Button) findViewById(R.id.btn0));
        getButtons().add((Button) findViewById(R.id.btn1));
        getButtons().add((Button) findViewById(R.id.btn2));
        getButtons().add((Button) findViewById(R.id.btn3));
        getButtons().add((Button) findViewById(R.id.btn4));
        getButtons().add((Button) findViewById(R.id.btn5));
        getButtons().add((Button) findViewById(R.id.btn6));
        getButtons().add((Button) findViewById(R.id.btn7));
        getButtons().add((Button) findViewById(R.id.btn8));
        getButtons().add((Button) findViewById(R.id.btn9));
        for (int i = 0; i < getButtons().size(); i++) {
            getButtons().get(i).setOnClickListener(this);
        }

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(String pin) {

        // Reset errors.

        getPinText().setError(null);

        // Store values at the time of the login attempt.

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (pin.isEmpty() && !isPasswordValid(pin)) {
            getPinText().setError("Invalid Pin");
            focusView = getPinText();
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt
            execute(pin);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        final int id = btn.getId();
        switch (id) {
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9: {
                String number = btn.getText().toString();
                String entered = getPinText().getText().toString().trim();
                if (entered.length() < 4) {
                    getPinText().append(number);
                    if (entered.length() == 3) {
                        attemptLogin(entered);
                    }
                }
                break;
            }

        }

    }

    //initiate the login attempt
    public void execute(String pin) {
        int intpin = Integer.parseInt(pin);
        if (intpin == getAccount().getInfo().getPin()) {
            notification.setToastBundle("Success", "Login success");
            notification.notify(Notification.TOAST);
            login();
        }
        else {
            getPinText().setText(null);
            getPinText().setError(null);
            notification.setToastBundle("Failed", "Login failed");
            notification.notify(Notification.TOAST);
        }
    }

    public void login() {
        StartMainActivity();
        finish();
    }

    private void StartMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        TempData.account = getAccount();
        startActivity(intent);
    }


    public Account getAccount() {
        return app.getAccount();
    }

    public void setAccount(Account account) {
        app.setAccount(account);
    }

    public TextView getPinText() {
        return pinText;
    }

    public void setPinText(TextView pinText) {
        this.pinText = pinText;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }
}

