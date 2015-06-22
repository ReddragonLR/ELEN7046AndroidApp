package za.ac.wits.elen7046.sirvey;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import za.ac.wits.elen7046.sirvey.fragments.Settings;
import za.ac.wits.elen7046.sirvey.models.Translator;
import za.ac.wits.elen7046.sirvey.models.realm.CompletedSurvey;
import za.ac.wits.elen7046.sirvey.models.realm.Survey;

public class MainActivity extends ActionBarActivity {

    // Declaring Your View and Variables
    private static final String TAG = MainActivity.class.getName();
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
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create a new empty instance of Realm
        realm = Realm.getInstance(this);
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
        Log.wtf(TAG,"Completed On create of MainAct");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    /* Called when the user touches the button */
    public void RequestSurveysFromServerButtonPressed(View view) {
        Log.wtf(TAG, "RequestSurveysFromServerButtonPressed");


        server.getSurveysFromRemoteServer(translator, realm);
    }


    public void ChangeServerButtonPressed(View view) {
        Log.wtf(TAG, "RequestSurveysFromServerButtonPressed");

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

    public void DeleteLocalStorageSurveysQuestions() {

        realm.beginTransaction();
        realm.allObjects(Survey.class).clear();
        realm.commitTransaction();

    }

    public void DeleteLocalCompletedSurveys() {

        realm.beginTransaction();
        realm.allObjects(CompletedSurvey.class).clear();
        realm.commitTransaction();
    }

    public void DeleteLocalStorageSurveysButtonPressed(View view) {
        Log.wtf(TAG, "DeleteLocalStorageSurveysButtonPressed");
        DeleteLocalStorageSurveysQuestions();

        Log.wtf(TAG, "Successfully deleted from local db");
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

    public void DeleteLocalAnsweredSurveysButtonPressed(View view) {
        Log.wtf(TAG, "DeleteLocalAnsweredSurveysButtonPressed");
        DeleteLocalCompletedSurveys();
        Log.wtf(TAG, "DeleteLocalAnsweredSurveysButtonPressed");
        Toast.makeText(getApplicationContext(), "Successfully deleted completed surveys", Toast.LENGTH_SHORT).show();

    }

    public void SendCompletedSurveysToServerButtonPressed(View view) {
        Log.wtf(TAG, "SendCompletedSurveysToServerButtonPressed");
        RealmResults<CompletedSurvey> data = realm.allObjects(CompletedSurvey.class);
        List<za.ac.wits.elen7046.sirvey.models.retrofit.CompletedSurvey>  surveysToSubmit = translator.translateRealmQCompletedSurveysToRetrofitCompletedSurvey(data);
       //' String temp = surveysToSubmit.
        Log.wtf(TAG,"FUCK");
            server.sendSurveys(surveysToSubmit);
        Log.wtf(TAG, "SendCompletedSurveysToServerButtonPressed");



    }
}
