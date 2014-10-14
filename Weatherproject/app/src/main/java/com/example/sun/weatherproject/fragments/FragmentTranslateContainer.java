package com.example.sun.weatherproject.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sun.weatherproject.R;

/**
 * Created by sundays on 10/14/14.
 */
public class FragmentTranslateContainer extends Fragment {
    public static String LANG_NUM = "lang_num";

    public static FragmentTranslateContainer getInstance(int lang){
        FragmentTranslateContainer translateFragment = new FragmentTranslateContainer();
        Bundle bundle = new Bundle();
        bundle.putInt(LANG_NUM, lang);
        translateFragment.setArguments(bundle);
        return translateFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.v("", "ОТОБРАЖЕНИЕ КОНТЕЙНЕРА ЛЭЙАУТ!!!!!!!!!!!!!!!!!!");
        return inflater.inflate(R.layout.fr_translate_detail, null);

    }
    /*

    Здесь нужно сделать подобие Receiver (как в MyWeather.java) чтобы в textView прилетало из intent
     отпарсенный Json

     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView checkTextView = (TextView) view.findViewById(R.id.textView);
        checkTextView.setText("Fragment #2");
    }
}
