package com.example.sun.weatherproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sun on 26.09.2014.
 */
public class MyWeather extends Activity {
    ListView list;
    Context context = MyWeather.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new MyAdapter(context));
    }


    private class MyAdapter extends BaseAdapter {
        private ArrayList<String> cities = new ArrayList<String>();
        private Context context;
        public MyAdapter( Context cont ) {
            this.context = cont;
            cities.add("Moscow");
            cities.add("London");
            cities.add("Kazan");
        }
        @Override
        public int getCount() {
            return cities.size();
        }

        @Override
        public String getItem(int i) {
            return cities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.text = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.text.setText(getItem(i));
            return convertView;
        }
        private class ViewHolder {
            public TextView text;
        }

    }
}



