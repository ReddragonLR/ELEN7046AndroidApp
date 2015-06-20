package za.ac.wits.elen7046.sirvey;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import io.realm.Realm;
import za.ac.wits.elen7046.sirvey.models.Translator;
import za.ac.wits.elen7046.sirvey.models.retrofit.Survey;


public class MainActivity extends ActionBarActivity {

    // Declaring Your View and Variables
    private static final String SETTINGSTAG = "SettingButtonPressed";
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Surveys","Settings"};
    Translator translator;
    int numberOfTabs =2;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        translator = new Translator();
        realm = Realm.getInstance(this);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,numberOfTabs);

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


    }

    /** Called when the user touches the button */
    public void RequestSurveysFromServerButtonPressed(View view) {
        Log.wtf(SETTINGSTAG, "RequestSurveysFromServerButtonPressed");

        Server server = new Server();
        server.getSurveysFromRemoteServer(translator,realm);

    }

    public void DeleteLocalStorageSurveysButtonPressed(View view) {
        Log.wtf(SETTINGSTAG,"DeleteLocalStorageSurveysButtonPressed");
    }

}
