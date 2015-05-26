package za.ac.wit.elen7046.sirveyapp;

import retrofit.RestAdapter;
import za.ac.wit.elen7046.sirveyapp.api.Api;

/**
 * Created by avanindra.singh on 2015/05/24.
 */
public class RESTAdapter {
    String API = "http://localhost:9000";
    RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();

    Api api = restAdapter.create(Api.class);
}
