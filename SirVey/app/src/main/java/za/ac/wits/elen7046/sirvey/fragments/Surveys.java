package za.ac.wits.elen7046.sirvey.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;


import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import za.ac.wits.elen7046.sirvey.AnswerSurveyActivity;
import za.ac.wits.elen7046.sirvey.R;
import za.ac.wits.elen7046.sirvey.models.realm.CompletedSurvey;
import za.ac.wits.elen7046.sirvey.models.realm.Survey;

public class Surveys extends Fragment {
    private static final String TAG = Surveys.class.getName();
    Realm realm;
    ArrayList<String> lst;
    public ArrayAdapter arrayAdapter;
    int completedSurveysCount;

    private RealmChangeListener listener = new RealmChangeListener() {
        public void onChange() {
            if (arrayAdapter!= null) {
                Context activityContext = getActivity().getApplicationContext();
                 realm = Realm.getInstance(activityContext);

                RealmResults<Survey> surveys = realm.where(Survey.class).findAll();
                ArrayList<String> temp = new ArrayList<>();

                for (Survey survey : surveys) {
                    temp.add(survey.getName());
                }
                lst = temp;
                updateFragment1ListView(lst);


                completedSurveysCount = realm.allObjects(CompletedSurvey.class).size();
                TextView numOfSurveys = (TextView) getView().findViewById(R.id.textNumberSurveys);
                numOfSurveys.setText(String.valueOf(completedSurveysCount));
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Create Realm instance for the UI thread
        Context activityContext = getActivity().getApplicationContext();
        realm = Realm.getInstance(activityContext);
        //realm = Realm.getInstance(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_surveys, container, false);

        RealmResults<Survey> surveys = realm.where(Survey.class).findAll();
        ArrayList<String> temp = new ArrayList<>();

        for (Survey survey : surveys) {
            temp.add(survey.getName());
        }

        lst = temp;
        ListView listView = (ListView) view.findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,lst);
        listView.setAdapter(arrayAdapter);

        completedSurveysCount = realm.allObjects(CompletedSurvey.class).size();

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i;
                i = new Intent(getActivity(), AnswerSurveyActivity.class);

                ListView lv = (ListView) parent;
                TextView tv = (TextView) lv.getChildAt(position);
                String surveyName = tv.getText().toString();
                i.putExtra("SURVEY_NAME",surveyName);
                startActivity(i);

            }

        });


        return view;
    }

    @Override
    public void onStart () {
        super.onStart();

        completedSurveysCount = realm.allObjects(CompletedSurvey.class).size();
        TextView numOfSurveys = (TextView) getView().findViewById(R.id.textNumberSurveys);

        numOfSurveys.setText(String.valueOf(completedSurveysCount));
        Log.wtf(TAG,"WTF");

    }

    @Override
    public void onPause() {
        super.onPause();

        // Disable UI refresh while the fragment is no longer active.
        realm.removeChangeListener(listener);

    }
    @Override
    public void onResume() {
        super.onResume();
        // Enable UI refresh while the fragment is active.
        realm.addChangeListener(listener);
    }


    public void updateFragment1ListView(ArrayList<String> lst){

        arrayAdapter.clear();

        if (lst != null){

            for (Object object : lst) {

                arrayAdapter.insert(object, arrayAdapter.getCount());
            }
        }

        arrayAdapter.notifyDataSetChanged();
    }


@Override
public void onDestroy() {
    super.onDestroy();
    // Remember to close the Realm instance when done with it.
    realm.close();
    Log.wtf("WTF", "DESTROUYED");
}

}

