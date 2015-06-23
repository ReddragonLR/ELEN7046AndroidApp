package za.ac.wits.elen7046.sirvey;

import io.realm.Realm;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import za.ac.wits.elen7046.sirvey.models.Translator;
import za.ac.wits.elen7046.sirvey.models.retrofit.CompletedSurvey;
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
    private static final String TAG= Server.class.getName();
    private static String API;

    private static RestAdapter restAdapter;
    private static Api api;
    private LocalBroadcastManager bManager;


    public Server(LocalBroadcastManager bManager,String IPAddress) {
        this.bManager = bManager;
        API = IPAddress;
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        api = restAdapter.create(Api.class);
    }

    private static void log(RetrofitError e) {
        Log.e(TAG, "API call failed", e);
    }

    public void getSurveysFromRemoteServer(final Translator translator ,final Realm realm) {
         api.getFeed(new Callback<List<Survey>>() {
             @Override
             public void success(List<Survey> remoteSurveys, Response response) {

                 realm.beginTransaction();
                 realm.allObjects(za.ac.wits.elen7046.sirvey.models.realm.Survey.class).clear();
                 realm.commitTransaction();

                 ArrayList<String> surveyNames = new ArrayList<>();
                 for (Survey temp : remoteSurveys) {
                     surveyNames.add(temp.getName());
                 }
                 Intent i = new Intent(MainActivity.SUCCESSFULLY_SENT_COMPLETED_SURVEYS);
                 bManager.sendBroadcast(i);

                 translator.translateRetrofitSurveysToRealmSurveys(remoteSurveys, realm);
                 realm.close();
             }

             @Override
             public void failure(RetrofitError error) {
                 log(error);
                 Intent toastMessage = new Intent(MainActivity.TOAST);
                 toastMessage.putExtra("message", "Failed to retrieve data from server!");
                 bManager.sendBroadcast(toastMessage);
             }
         });
    }

    public void sendSurveys(List<CompletedSurvey> completedSurveys) {
        api.sendSurveys(completedSurveys, new Callback<List<CompletedSurvey>>() {
            @Override
            public void success(List<CompletedSurvey> completedSurveys, Response response) {
                Intent toastMessage = new Intent(MainActivity.TOAST);
                toastMessage.putExtra("message", "Successfully send data to server!");
                bManager.sendBroadcast(toastMessage);

            }

            @Override
            public void failure(RetrofitError error) {
                Intent toastMessage = new Intent(MainActivity.TOAST);
                toastMessage.putExtra("message", "Failed to send data to server!");
                bManager.sendBroadcast(toastMessage);

            }
        });
    }

    private interface Api {
        @GET("/surveys")
        void getFeed(Callback<List<Survey>> response);

        @POST("/completedSurveys/?option=bulkCompletedSurveyUpload")
        void sendSurveys(@Body List<CompletedSurvey> body, Callback<List<CompletedSurvey>> callback);

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