package za.ac.wits.elen7046.sirvey.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import za.ac.wits.elen7046.sirvey.R;


public class SettingsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings,container,false);
        return v;
    }
}