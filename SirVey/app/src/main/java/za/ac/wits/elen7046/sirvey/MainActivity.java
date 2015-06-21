package za.ac.wits.elen7046.sirvey;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import za.ac.wits.elen7046.sirvey.fragments.Settings;
import za.ac.wits.elen7046.sirvey.fragments.Surveys;
import za.ac.wits.elen7046.sirvey.models.Translator;

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
    Realm realm;
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


        // Clear the realm from last time
        Realm.deleteRealmFile(this);
        translator = new Translator();
        realm = Realm.getInstance(this);


        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getFragmentManager(),Titles,numberOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
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


        server.getSurveysFromRemoteServer(translator);
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
                Settings settingsFragment = (Settings)getFragmentManager().findFragmentByTag(getFragmentTag(pager.getId(),1));
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





    public void DeleteLocalStorageSurveysButtonPressed(View view) {
        Log.wtf(MAIN_ACTIVITY_TAG,"DeleteLocalStorageSurveysButtonPressed");
        Log.wtf(MAIN_ACTIVITY_TAG, "Successfully deleted from local db");
    }
    public void updateSurveysFragmentWithNewList(ArrayList<String> stringArrayList) {
        Surveys surveysFragment = (Surveys) getFragmentManager().findFragmentByTag(getFragmentTag(pager.getId(),0));
        surveysFragment.updateFragment1ListView(stringArrayList);


    }

    private String getFragmentTag(int viewPagerId, int fragmentPosition)
    {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(UPDATE_SURVEYS_LIST_UI)) {
                ArrayList<String> serverListOfSurveys = intent.getStringArrayListExtra("surveyNames");
                updateSurveysFragmentWithNewList(serverListOfSurveys);
            }

            if(intent.getAction().equals((TOAST))) {
                 Toast.makeText(getApplicationContext(), intent.getExtras().getString("message"), Toast.LENGTH_SHORT).show();
            }

        }
    };

}
