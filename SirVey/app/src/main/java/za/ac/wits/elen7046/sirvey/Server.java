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
    private static final String SERVERTAG= "ServerClass";
    //private static String API = "http://192.168.1.10:9000/api";
    //private static final String API = "http://192.168.43.218:9000/api";
    private static String API;

    private static final RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
    private static final Api api = restAdapter.create(Api.class);
    private static List<Survey> surveys;
    private LocalBroadcastManager bManager;

    public Server(LocalBroadcastManager bManager) {
        surveys = new ArrayList<>();
        this.bManager = bManager;

    }

    private static void log(RetrofitError e) {
        Log.e("ServerAPI", "API call failed", e);
    }

    public void getSurveysFromRemoteServer(final Translator translater ) {
         api.getFeed(new Callback<List<Survey>>() {
            @Override
            public void success(List<Survey> remoteSurveys, Response response) {
                Log.wtf(SERVERTAG, "Started Call to server");

                ArrayList<String> surveyNames = new ArrayList<>();
                for(Survey temp : remoteSurveys) {
                    surveyNames.add(temp.getName());
                }
                Intent i = new Intent(MainActivity.UPDATE_SURVEYS_LIST_UI); //This should be getIntent();


                i.putStringArrayListExtra("surveyNames", surveyNames);

                bManager.sendBroadcast(i);
                Log.wtf(SERVERTAG, "Success call from server");
            }
            @Override
            public void failure(RetrofitError error) {
                log(error);
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
    }
    public String getIPAddress () {
        return API;
    }
}