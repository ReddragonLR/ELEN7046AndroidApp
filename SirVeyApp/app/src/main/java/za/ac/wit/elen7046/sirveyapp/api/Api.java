package za.ac.wit.elen7046.sirveyapp.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import za.ac.wit.elen7046.sirveyapp.models.Survey;


public interface Api {
    @GET("/surveys")      //here is the other url part.best way is to start using /
    void getFeed( Callback<List<Survey>> response);   //string user is for passing values from edittext for eg: user=basil2style,google
    //response is the response from the server which is now in the POJO
}
