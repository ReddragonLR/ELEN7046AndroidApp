package za.ac.wits.elen7046.sirvey;

import retrofit.http.GET;
import za.ac.wits.elen7046.sirvey.models.retrofit.Survey;
import android.util.Log;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;



public class Server {
    private static final String API = "http://192.168.1.10:9000/api";
    private static final RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
    private static final Api api = restAdapter.create(Api.class);
    private static List<Survey> surveys;


    public Server() {

    }

    private static void log(RetrofitError e) {
        Log.e("ServerAPI", "API call failed", e);
    }

    public void getSurveysFromRemoteServer() {
         api.getFeed(new Callback<List<Survey>>() {
            @Override
            public void success(List<Survey> remoteSurveys, Response response) {

            surveys = remoteSurveys;
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
}