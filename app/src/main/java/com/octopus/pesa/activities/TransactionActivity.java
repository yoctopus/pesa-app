package com.octopus.pesa.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.octopus.pesa.PesaApp;
import com.octopus.pesa.R;
import com.octopus.pesa.models.TempData;
import com.octopus.pesa.fragments.BalanceFragment;
import com.octopus.pesa.fragments.ExpenseFragment;
import com.octopus.pesa.fragments.IncomeFragment;
import com.octopus.pesa.models.Account;
import com.octopus.pesa.models.Transaction;
import com.octopus.pesa.models.errors.BlankEditTextException;
import com.octopus.pesa.models.errors.BlankSpinnerException;
import com.octopus.pesa.models.notifications.Notification;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity implements
        IncomeFragment.OnFragmentInteractionListener,
        ExpenseFragment.OnFragmentInteractionListener,
        BalanceFragment.OnFragmentInteractionListener,
        View.OnClickListener, Transaction.TransactionCompleteListener {

    private PesaApp app;
    private int userChoice;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private Notification notification;
    private Spinner incomeSpinner;
    private Spinner expenseSpinner;
    private TextView incomeTextView;
    private TextView expenseTextView;
    private ArrayList<Button> expenseButtons;
    private ArrayList<Button> incomeButtons;
    private ImageButton expenseBackButton;
    private ImageButton incomeBackButton;
    private ImageButton expenseOkButton;
    private ImageButton incomeOkButton;
    private TextView balView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        setAppBarLayout((AppBarLayout) findViewById(R.id.appbar_transaction));
        setToolbar((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(getToolbar());
        Intent intent = this.getIntent();
        this.setUserChoice(intent.getIntExtra(TempData.FRAGMENTID, 0));
        /*
        Create the adapter that will return a fragment for each of the three
        primary sections of the activity.
        */
        setmSectionsPagerAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        // Set up the ViewPager with the sections adapter.
        setmViewPager((ViewPager) findViewById(R.id.container));
        getmViewPager().setAdapter(getmSectionsPagerAdapter());
        getmViewPager().setCurrentItem(getUserChoice());
        setTabLayout((TabLayout) findViewById(R.id.tabs));
        getTabLayout().setupWithViewPager(getmViewPager());
        setApp((PesaApp) getApplication());
        setNotification(new Notification(this));
    }

    @Override
    public void onFragmentInteraction(int position) {
        switch (position) {
            case TempData.incomeChoice: {
                setUpIncomeFragment();
                break;
            }
            case TempData.expenseChoice: {
                setUpExpenseFragment();
                break;
            }
            case TempData.balanceChoice: {
                setUpBalanceFragment();
                break;
            }
        }
    }

    private void setUpIncomeFragment() {
        setIncomeSpinner((Spinner) findViewById(R.id.item_income_name_spinner));
        setIncomeTextView((TextView) findViewById(R.id.incomo_amount_textView));
        incomeButtons = new ArrayList<>();
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn0));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn1));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn2));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn3));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn4));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn5));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn6));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn7));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn8));
        getIncomeButtons().add((Button) findViewById(R.id.incomebtn9));
        setIncomeBackButton((ImageButton) findViewById(R.id.incomebackButton));
        setIncomeOkButton((ImageButton) findViewById(R.id.incomeokButton));
        for (int i = 0; i < getIncomeButtons().size(); i++) {
            getIncomeButtons().get(i).setOnClickListener(this);
        }
        getIncomeBackButton().setOnClickListener(this);
        getIncomeOkButton().setOnClickListener(this);
        populateIncomeItemSpinner();

    }

    private void setUpExpenseFragment() {
        setExpenseSpinner((Spinner) findViewById(R.id.expense_tem_name_spinner));
        setExpenseTextView((TextView) findViewById(R.id.expense_amount_textView));
        expenseButtons = new ArrayList<>();

        getExpenseButtons().add((Button) findViewById(R.id.expensebtn0));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn1));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn2));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn3));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn4));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn5));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn6));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn7));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn8));
        getExpenseButtons().add((Button) findViewById(R.id.expensebtn9));
        setExpenseBackButton((ImageButton) findViewById(R.id.expensebackButton));
        setExpenseOkButton((ImageButton) findViewById(R.id.expenseokButton));
        for (int i = 0; i < getExpenseButtons().size(); i++) {
            getExpenseButtons().get(i).setOnClickListener(this);
        }
        getExpenseBackButton().setOnClickListener(this);
        getExpenseOkButton().setOnClickListener(this);
        populateExpenseItemSpinner();
    }

    private void setUpBalanceFragment() {
        setBalView((TextView) findViewById(R.id.balanceTextView));
        int bal = getAccount().getInfo().getTotalBal();
        String balance = "" + bal;
        getBalView().setText(balance);
    }

    private void populateIncomeItemSpinner() {
        ArrayList<String> items = getAccount().getIncomeItems();
        getIncomeSpinner().setPrompt("Choose Item Below...");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < items.size(); i++) {
            spinnerAdapter.add(items.get(i));
            spinnerAdapter.notifyDataSetChanged();
        }
        getIncomeSpinner().setAdapter(spinnerAdapter);
    }

    private void populateExpenseItemSpinner() {
        ArrayList<String> items = getAccount().getExpenseItems();
        getExpenseSpinner().setPrompt("Choose Item Below...");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < items.size(); i++) {
            spinnerAdapter.add(items.get(i));
            spinnerAdapter.notifyDataSetChanged();
        }
        getExpenseSpinner().setAdapter(spinnerAdapter);
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            //income screen buttons
            case R.id.incomebtn0:
            case R.id.incomebtn1:
            case R.id.incomebtn2:
            case R.id.incomebtn3:
            case R.id.incomebtn4:
            case R.id.incomebtn5:
            case R.id.incomebtn6:
            case R.id.incomebtn7:
            case R.id.incomebtn8:
            case R.id.incomebtn9: {
                Button b = (Button) view;
                getIncomeTextView().append(b.getText());
                break;
            }
            //expense screen buttons
            case R.id.expensebtn0:
            case R.id.expensebtn1:
            case R.id.expensebtn2:
            case R.id.expensebtn3:
            case R.id.expensebtn4:
            case R.id.expensebtn5:
            case R.id.expensebtn6:
            case R.id.expensebtn7:
            case R.id.expensebtn8:
            case R.id.expensebtn9: {
                Button b = (Button) view;
                getExpenseTextView().append(b.getText());
                break;
            }
            case R.id.incomebackButton: {
                String entry = getIncomeTextView().getText().toString();
                if (entry.isEmpty()) {
                    break;
                } else {
                    String newentry = entry.substring(0, entry.length() - 1);
                    getIncomeTextView().setText(newentry);
                }
                break;
            }
            case R.id.incomeokButton: {
                try {
                    if (checkIncomeInputs()) {
                        TextView tview = (TextView) getIncomeSpinner().getSelectedView();
                        String item = tview.getText().toString();
                        String amount = getIncomeTextView().getText().toString();
                        TransactIncome(item, amount);
                    }
                } catch (BlankSpinnerException | BlankEditTextException e) {
                    notifyError(e);
                }
                break;
            }

            case R.id.expensebackButton: {
                String entry = getExpenseTextView().getText().toString();
                if (entry.isEmpty()) {
                    break;
                } else {
                    String newentry = entry.substring(0, entry.length() - 1);
                    getExpenseTextView().setText(newentry);
                }
                break;
            }
            case R.id.expenseokButton: {
                try {
                    if (checkExpenseInputs()) {
                        TextView tview = (TextView) getExpenseSpinner().getSelectedView();
                        String item = tview.getText().toString();
                        String amount = getExpenseTextView().getText().toString();
                        TransactExpense(item, amount);
                    }
                } catch (BlankSpinnerException | BlankEditTextException e) {
                    notifyError(e);
                }
                break;
            }
        }
    }

    private boolean TransactIncome(String item, String amount) {
        int cash = Integer.parseInt(amount);
        getAccount().transactIncome(item, cash);
        getAccount().setOnTransactionCompleteListener(this);
        return true;
    }

    private boolean TransactExpense(String item, String amount) {
        int cash = Integer.parseInt(amount);
        getAccount().transactExpense(item, cash);
        getAccount().setOnTransactionCompleteListener(this);
        return true;
    }

    private boolean checkIncomeInputs() throws BlankSpinnerException, BlankEditTextException {
        if (getIncomeSpinner().getSelectedView() == null) {
            throw new BlankSpinnerException("No choice selected");
        }
        if (getIncomeTextView().getText().toString().isEmpty()) {
            throw new BlankEditTextException("No amount specified");
        }
        return true;
    }

    private boolean checkExpenseInputs() throws BlankEditTextException, BlankSpinnerException {
        if (getExpenseSpinner().getSelectedView() == null) {
            throw new BlankSpinnerException("No choice selected");
        }
        if (getExpenseTextView().getText().toString().isEmpty()) {
            throw new BlankEditTextException("No amount Specified");
        }
        return true;
    }

    private void notifyError(Exception e) {
        getNotification().setDialogBundle(e.getMessage(), null);
        getNotification().getAlert().setPositiveButton("Correct", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                correctTransaction();
            }
        });
        getNotification().getAlert().setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ignoreTransaction();
            }
        });
        getNotification().notify(Notification.DIALOG);
    }

    private void correctTransaction() {
        /**empty method body**/
    }

    private void ignoreTransaction() {
        finish();
    }

    @Override
    public void onTransactionComplete(int id, boolean success) {
        switch (id) {
            case Transaction.INCOME:
            case Transaction.EXPENSE: {
                getApp().getAccount().refresh();
                break;
            }
            case Transaction.RECORDS: {
                getApp().getAccount().setRecords(TempData.records);
                break;
            }
            case Transaction.INFO: {
                getApp().getAccount().setInfo(TempData.info);
                break;
            }

        }
    }

    public Account getAccount() {
        return getApp().getAccount();
    }

    public void setAccount(Account account)
    {
        getApp().setAccount(account);

    }

    public int getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(int userChoice) {
        this.userChoice = userChoice;
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public SectionsPagerAdapter getmSectionsPagerAdapter() {
        return mSectionsPagerAdapter;
    }

    public void setmSectionsPagerAdapter(SectionsPagerAdapter mSectionsPagerAdapter) {
        this.mSectionsPagerAdapter = mSectionsPagerAdapter;
    }

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    public void setAppBarLayout(AppBarLayout appBarLayout) {
        this.appBarLayout = appBarLayout;
    }

    public Spinner getIncomeSpinner() {
        return incomeSpinner;
    }

    public void setIncomeSpinner(Spinner incomeSpinner) {
        this.incomeSpinner = incomeSpinner;
    }

    public Spinner getExpenseSpinner() {
        return expenseSpinner;
    }

    public void setExpenseSpinner(Spinner expenseSpinner) {
        this.expenseSpinner = expenseSpinner;
    }

    public TextView getIncomeTextView() {
        return incomeTextView;
    }

    public void setIncomeTextView(TextView incomeTextView) {
        this.incomeTextView = incomeTextView;
    }

    public TextView getExpenseTextView() {
        return expenseTextView;
    }

    public void setExpenseTextView(TextView expenseTextView) {
        this.expenseTextView = expenseTextView;
    }

    public ArrayList<Button> getExpenseButtons() {
        return expenseButtons;
    }

    public void setExpenseButtons(final ArrayList<Button> expenseButtons) {
        this.expenseButtons = expenseButtons;
    }

    public ArrayList<Button> getIncomeButtons() {
        return incomeButtons;
    }

    public void setIncomeButtons(final ArrayList<Button> incomeButtons) {
        this.incomeButtons = incomeButtons;
    }

    public ImageButton getExpenseBackButton() {
        return expenseBackButton;
    }

    public void setExpenseBackButton(ImageButton expenseBackButton) {
        this.expenseBackButton = expenseBackButton;
    }

    public ImageButton getIncomeBackButton() {
        return incomeBackButton;
    }

    public void setIncomeBackButton(ImageButton incomeBackButton) {
        this.incomeBackButton = incomeBackButton;
    }

    public ImageButton getExpenseOkButton() {
        return expenseOkButton;
    }

    public void setExpenseOkButton(ImageButton expenseOkButton) {
        this.expenseOkButton = expenseOkButton;
    }

    public ImageButton getIncomeOkButton() {
        return incomeOkButton;
    }

    public void setIncomeOkButton(ImageButton incomeOkButton) {
        this.incomeOkButton = incomeOkButton;
    }

    public TextView getBalView() {
        return balView;
    }

    public void setBalView(TextView balView) {
        this.balView = balView;
    }

    public PesaApp getApp() {
        return app;
    }

    public void setApp(PesaApp app) {
        this.app = app;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case TempData.incomeChoice: {
                    fragment = IncomeFragment.newInstance(null, null);
                    break;
                }
                case TempData.expenseChoice: {
                    fragment = ExpenseFragment.newInstance();
                    break;
                }
                case TempData.balanceChoice: {
                    fragment = BalanceFragment.newInstance(null, null);
                    break;
                }
            }
            return fragment;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "INCOME";
                case 1:
                    return "EXPENSE";
                case 2:
                    return "BALANCE";
            }
            return null;
        }
    }
}
