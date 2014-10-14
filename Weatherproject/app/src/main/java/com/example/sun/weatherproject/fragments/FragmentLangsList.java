package com.example.sun.weatherproject.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sun.weatherproject.R;
import java.util.ArrayList;
import com.example.sun.weatherproject.MyLanguage;

/**
 * Created by sundays on 10/14/14.
 */
public class FragmentLangsList extends ListFragment implements AdapterView.OnItemClickListener {
    public EditText myEditText;
    MyAdapter adapter;
    ListView list;
    private OnItemSelectedListener mCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.v("", "ОТОБРАЖЕНИЕ ЛИСТА ЛЭЙАУТ!!!!!!!!!!!!!!!!!!");
        //View view = inflater.inflate(R.layout.fr_list_detail, null,false);
        //list=(ListView) view.findViewById(R.id.listView);
        //list.setAdapter(adapter);
        //setListAdapter(adapter);
        //list = (ListView) view.findViewById(R.id.listView);
        //list.setAdapter(new MyAdapter(context));
        //myEditText = (EditText) view.findViewById(R.id.editText);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        adapter = new MyAdapter(getActivity());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?>parent,View view, int i, long id){
        //Toast.makeText(getActivity(),menutitles[position],Toast.LENGTH_SHORT).show();
        //mCallBack.onLanguageSelected(i);
       switch (i){
            case 0: Intent intentRu = new Intent(view.getContext(), MyLanguage.class);
                intentRu.putExtra(MyLanguage.LANGUAGE, "ru");
                view.getContext().startService(intentRu); break;
            case 1: Intent intentEn = new Intent(view.getContext(), MyLanguage.class);
                intentEn.putExtra(MyLanguage.LANGUAGE, "en");
                view.getContext().startService(intentEn); break;
            case 2: Intent intentFr = new Intent(view.getContext(), MyLanguage.class);
                intentFr.putExtra(MyLanguage.LANGUAGE, "fr");
                view.getContext().startService(intentFr); break;
            default: Intent intentDe = new Intent(view.getContext(), MyLanguage.class);
                intentDe.putExtra(MyLanguage.LANGUAGE, "de");
                view.getContext().startService(intentDe); break;
        }
        Log.v("","КЛИКНУЛ!!!!");
    }

    public interface OnItemSelectedListener {
        public void onLanguageSelected(int lang);
    }

    private class MyAdapter extends BaseAdapter {
        private ArrayList<String> cities = new ArrayList<String>();
        private Context context;
        public MyAdapter( Context cont ) {
            this.context = cont;
            cities.add("Русский");
            cities.add("Английский");
            cities.add("Французский");
            cities.add("Немецкий");
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
