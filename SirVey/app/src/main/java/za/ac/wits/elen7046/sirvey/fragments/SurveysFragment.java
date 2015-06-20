package za.ac.wits.elen7046.sirvey.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import za.ac.wits.elen7046.sirvey.R;

public class SurveysFragment extends ListFragment {
    String[] list_items;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_surveys,container,false);

        list_items= getResources().getStringArray(R.array.list);
        setListAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list_items));

        return view;
    }


}

