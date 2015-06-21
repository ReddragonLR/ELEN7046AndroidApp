package za.ac.wits.elen7046.sirvey.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import za.ac.wits.elen7046.sirvey.R;

public class Surveys extends ListFragment {
    String[] array;
    ArrayList<String> lst;
    public ArrayAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_surveys,container,false);
        array= getResources().getStringArray(R.array.list);
        lst = new ArrayList<String>(Arrays.asList(array));

        arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,lst);
        setListAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.wtf("WTF", "DESTROUYED");
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
}

