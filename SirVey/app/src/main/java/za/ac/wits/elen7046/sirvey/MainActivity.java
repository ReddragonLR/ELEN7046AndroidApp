package za.ac.wits.elen7046.sirvey;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import za.ac.wits.elen7046.sirvey.fragments.Settings;
import za.ac.wits.elen7046.sirvey.fragments.Surveys;
import za.ac.wits.elen7046.sirvey.models.Translator;
import za.ac.wits.elen7046.sirvey.models.realm.Survey;

public class MainActivity extends ActionBarActivity {

    // Declaring Your View and Variables
    private static final String MAIN_ACTIVITY_TAG = "MAIN_ACTIVITY";
    public static final String UPDATE_SURVEYS_LIST_UI = "za.ac.wits.elen7046.sirvey.MainActivity.UPDATE_SURVEYS_LIST_UI";
    public static final String TOAST = "za.ac.wits.elen7046.sirvey.MainActivity.TOAST";
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Surveys","Settings"};
    Translator translator;
    int numberOfTabs =2;
    LocalBroadcastManager bManager;
    Server server;
    public static final String ServerIP = "ServerIPKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent
        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPDATE_SURVEYS_LIST_UI);
        intentFilter.addAction(TOAST);
        bManager.registerReceiver(bReceiver, intentFilter);


        translator = new Translator();



        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,numberOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);

        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width


        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);



        SharedPreferences sharedPreferences = this.getPreferences(0);
        String IPAddress = sharedPreferences.getString(ServerIP, "Default ServerIP Address");

        // Create server
        server = new Server(bManager,IPAddress);
        Log.wtf(MAIN_ACTIVITY_TAG,"Completed On create of MainAct");
    }

    /* Called when the user touches the button */
    public void RequestSurveysFromServerButtonPressed(View view) {
        Log.wtf(MAIN_ACTIVITY_TAG, "RequestSurveysFromServerButtonPressed");
        Realm realm = Realm.getInstance(this);

        server.getSurveysFromRemoteServer(translator, realm);

    }


    public void ChangeServerButtonPressed(View view) {
        Log.wtf(MAIN_ACTIVITY_TAG, "RequestSurveysFromServerButtonPressed");

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Server Address");
        alert.setMessage("Enter server Address:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);
        input.setText(server.getIPAddress());
        input.setSelection(input.getText().length());

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                server.setIPAddress(value);
                Settings settingsFragment = (Settings) adapter.getRegisteredFragment(1);


                settingsFragment.changeServerAddressText(value);
                saveServerIPToPreferences(value) ;

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled. Do Nothing
            }
        });
        alert.show();
    }

    private void saveServerIPToPreferences(String value) {

        SharedPreferences sharedPreferences =  this.getPreferences(0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ServerIP, value);

        editor.commit();

    }

    public void DeleteLocalStorageSurveys() {
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        realm.allObjects(Survey.class).clear();
        realm.commitTransaction();

    }


    public void DeleteLocalStorageSurveysButtonPressed(View view) {
        Log.wtf(MAIN_ACTIVITY_TAG, "DeleteLocalStorageSurveysButtonPressed");
        DeleteLocalStorageSurveys();

        Log.wtf(MAIN_ACTIVITY_TAG, "Successfully deleted from local db");
        Toast.makeText(getApplicationContext(), "Successfully deleted surveys", Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals((TOAST))) {
                 Toast.makeText(getApplicationContext(), intent.getExtras().getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
    };


}
