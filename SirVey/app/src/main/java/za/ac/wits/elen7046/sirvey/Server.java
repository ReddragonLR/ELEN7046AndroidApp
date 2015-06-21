package za.ac.wits.elen7046.sirvey;

import retrofit.http.GET;
import za.ac.wits.elen7046.sirvey.models.Translator;
import za.ac.wits.elen7046.sirvey.models.retrofit.Survey;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;



public class Server {
    private static final String SERVER_TAG= "ServerClass";
    private static String API;

    private static RestAdapter restAdapter;
    private static Api api;
    private static List<Survey> surveys;
    private LocalBroadcastManager bManager;

    public Server(LocalBroadcastManager bManager,String IPAddress) {
        surveys = new ArrayList<>();
        this.bManager = bManager;
        API = IPAddress;
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        api = restAdapter.create(Api.class);
    }

    private static void log(RetrofitError e) {
        Log.e("ServerAPI", "API call failed", e);
    }

    public void getSurveysFromRemoteServer(final Translator translater ) {
         api.getFeed(new Callback<List<Survey>>() {
            @Override
            public void success(List<Survey> remoteSurveys, Response response) {
                Log.wtf(SERVER_TAG, "Started Call to server");

                ArrayList<String> surveyNames = new ArrayList<>();
                for(Survey temp : remoteSurveys) {
                    surveyNames.add(temp.getName());
                }
                Intent i = new Intent(MainActivity.UPDATE_SURVEYS_LIST_UI); //This should be getIntent();

                Intent toastMessage = new Intent(MainActivity.TOAST);
                i.putStringArrayListExtra("surveyNames", surveyNames);
                toastMessage.putExtra("message","Successfully retrieve data from server!");
                bManager.sendBroadcast(i);
                bManager.sendBroadcast(toastMessage);
                Log.wtf(SERVER_TAG, "Success call from server");
            }
            @Override
            public void failure(RetrofitError error) {
                log(error);

                Intent toastMessage = new Intent(MainActivity.TOAST);
                toastMessage.putExtra("message","Failed to retrieve data from server!");
                bManager.sendBroadcast(toastMessage);
            }
        });
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    private interface Api {
        @GET("/surveys")
            //here is the other url part.best way is to start using /
        void getFeed(Callback<List<Survey>> response);

    }

    public void setIPAddress (String ipAddress) {
        API = ipAddress;
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        api = restAdapter.create(Api.class);
    }
    public String getIPAddress () {
        return API;
    }
}