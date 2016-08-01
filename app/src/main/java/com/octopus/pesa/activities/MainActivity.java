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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.octopus.pesa.PesaApp;
import com.octopus.pesa.R;
import com.octopus.pesa.models.TempData;
import com.octopus.pesa.models.Transaction;
import com.octopus.pesa.models.adapters.RecordAdapter;
import com.octopus.pesa.models.notifications.Notification;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Transaction.TransactionCompleteListener {

    private TextView nameView;
    private DrawerLayout drawer;
    private ListView recordView;
    private TextView balanceText;

    private Notification notification;

    private PesaApp app;

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

        recordView = (ListView) findViewById(R.id.listView_recent);
        balanceText = (TextView) findViewById(R.id.show_daily_usage_percentage);

        app = (PesaApp) getApplication();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        prepareData();
    }


    private void prepareData() {
        app.getAccount().setActivityContext(this);
        app.getAccount().requestRecords();
        app.getAccount().setOnTransactionCompleteListener(this);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
            nameView = (TextView) findViewById(R.id.userName);
            String name = app.getAccount().getInfo().getName();
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
        app = (PesaApp) getApplication();
        prepareData();
        super.onResume();
    }


    @Override
    public void onTransactionComplete(int id, boolean success) {
        switch (id) {
            case Transaction.RECORDS: {
                if (success) {
                    app.getAccount().setRecords(TempData.account.getRecords());
                    app.getAccount().setItems(TempData.account.getItems());
                    printBalance();
                    printRecords();
                } else {
                    informUserofNoRecords();
                }
            }
        }
    }

    private void printBalance() {
        int totalspent = app.getAccount().getExpenseTotal();
        int accset = app.getAccount().getInfo().getDailyLimit();
        if (accset != 0) {
            int percent = totalspent / accset * 100;
            balanceText.setText(percent+" %");
        }
        else {
            balanceText.setText(totalspent+" %");
        }
    }

    private void printRecords() {

        if (!app.getAccount().getRecords().isEmpty()) {
            RecordAdapter adapter = new RecordAdapter(this, R.layout.record_row, app.getAccount().getRecords());
            recordView.setAdapter(adapter);
            recordView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });
        }

    }

    private void informUserofNoRecords() {
        notification.setDialogBundle("No Records", "Would you like to add a new record");
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
        this.startActivity(intent);
    }

    private void startTransactionActivity(int id) {
        Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
        intent.putExtra(TempData.FRAGMENTID, id);
        this.startActivity(intent);
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        this.startActivity(intent);
    }

    private void startDeveloperActivity() {
        Intent intent = new Intent(MainActivity.this, DeveloperActivity.class);
        this.startActivity(intent);
    }
}
