package com.octopus.pesa.activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.octopus.pesa.R;
import com.octopus.pesa.TempData;
import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.Transaction;
import com.octopus.pesa.models.notifications.Notification;
import com.octopus.pesa.models.views.RecordView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Transaction.TransactionCompleteListener {
    private Account account;
    private TextView nameView;
    private DrawerLayout drawer;
    private RecordView recordView;

    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTransactionActivity();
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        notification = new Notification(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        prepareData();
    }

    private void prepareData() {
        account = TempData.account;
        account.setActivityContext(this);
        account.requestRecords();
        account.setOnTransactionCompleteListener(this);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
            nameView = (TextView) findViewById(R.id.userName);
            String name = account.getInfo().getName();
            nameView.setText(name);
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_income) {
            startTransactionActivity(TempData.incomeChoice);
        } else if (id == R.id.nav_expense) {
            startTransactionActivity(TempData.expenseChoice);
        } else if (id == R.id.nav_balance) {
            startTransactionActivity(TempData.balanceChoice);
        } else if (id == R.id.nav_settings) {
            startSettingsActivity();

        } else if (id == R.id.nav_developer) {
            startDeveloperActivity();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        printRecords();
        super.onResume();
    }


    @Override
    public void onTransactionComplete(int id, boolean success) {
        switch (id) {
            case TempData.RecordsTransactionID: {
                if (success) {
                    account.setRecords(TempData.records);
                    account.setItems(TempData.items);
                    printBalance();
                    printRecords();
                } else {
                    informUserofNoRecords();
                }
            }
        }
    }

    private void printBalance() {

    }

    private void printRecords() {

    }

    private void informUserofNoRecords() {
        notification.getAlert().setTitle("No records");
        notification.getAlert().setMessage("Would you like to add a new record");
        notification.getAlert().setPositiveButton("Add record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startTransactionActivity();
            }
        });
        notification.getAlert().setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CancelDialog();
            }
        });
        notification.getAlert().setCancelable(true);
        notification.notify(Notification.DIALOG);
    }

    private void CancelDialog() {
        notification.getAlert().setView(View.GONE);
    }


    private void startTransactionActivity() {
        Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
        TempData.account = account;
        this.startActivity(intent);
    }

    private void startTransactionActivity(int id) {
        Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
        intent.putExtra(TempData.FRAGMENTID, id);
        TempData.account = account;
        this.startActivity(intent);
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        TempData.account = account;
        this.startActivity(intent);
    }

    private void startDeveloperActivity() {
        Intent intent = new Intent(MainActivity.this, DeveloperActivity.class);
        this.startActivity(intent);
    }
}
