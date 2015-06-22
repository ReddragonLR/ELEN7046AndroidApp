package za.ac.wits.elen7046.sirvey.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import za.ac.wits.elen7046.sirvey.R;


public class Settings extends Fragment {

    TextView mTextView;
    public static final String ServerIP = "ServerIPKey";
    @Override
         public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        return v;
    }
    @Override
    public void onStart () {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getPreferences(0);
        String IPAddress = sharedPreferences.getString(ServerIP, "Default ServerIP");

        changeServerAddressText(IPAddress);
    }


    public void changeServerAddressText(String mText)
    {
        mTextView = (TextView)getView().findViewById(R.id.serverAddress);
        mTextView.setText(mText);
    }
}