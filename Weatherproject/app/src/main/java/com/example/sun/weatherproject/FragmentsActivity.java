package com.example.sun.weatherproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.sun.weatherproject.fragments.FragmentLangsList;
import com.example.sun.weatherproject.fragments.FragmentTranslateContainer;

/**
 * Created by sundays on 10/14/14.
 */
public class FragmentsActivity extends FragmentActivity implements FragmentLangsList.OnItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.v("","ПЕРЕД ТЕМ КАК ОТОБРАЗИТЬ ЛЭЙАУТ!!!!!!!!!!!!!!!!!!");
        setContentView(R.layout.fragment_translate);
    }

    @Override
    public void onLanguageSelected(int lang){
        FragmentTranslateContainer newFragment = FragmentTranslateContainer.getInstance(lang);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_list,newFragment);
        transaction.commit();

    }
}
