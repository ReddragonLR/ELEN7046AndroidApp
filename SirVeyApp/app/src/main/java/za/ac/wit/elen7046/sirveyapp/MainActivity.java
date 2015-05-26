package za.ac.wit.elen7046.sirveyapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import za.ac.wit.elen7046.sirveyapp.api.Api;
import za.ac.wit.elen7046.sirveyapp.models.Survey;


public class MainActivity extends ActionBarActivity {
    String API = "http://10.0.3.2:9000/api";
    Button click;
    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.button);

        myText = (TextView) findViewById(R.id.text_id);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
                Api api = restAdapter.create(Api.class);
                String _id = "5561a1ca3f187d9004fc24d6";
                api.getFeed(new Callback<List<Survey>>() {
                    @Override
                    public void success(List<Survey> surveys, Response response) {
                        myText.append(surveys.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        myText.append("Failed");
                    }
                });
            }
        });
    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
