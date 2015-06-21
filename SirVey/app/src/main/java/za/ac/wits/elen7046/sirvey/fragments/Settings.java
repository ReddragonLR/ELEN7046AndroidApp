package za.ac.wits.elen7046.sirvey.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import za.ac.wits.elen7046.sirvey.R;


public class Settings extends Fragment {


    TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings,container,false);


        return v;
    }

    public void changeServerAddressText(String mText)
    {
        mTextView = (TextView)getView().findViewById(R.id.serverAddress);
        mTextView.setText(mText);
    }

}