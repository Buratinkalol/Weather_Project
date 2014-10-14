package com.example.sun.weatherproject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sun on 26.09.2014.
 *
 */
public class MyWeather extends Activity {
    Receiver receiver;
    private EditText myEditText;
    ListView list;
    Context context = MyWeather.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new MyAdapter(context));
        myEditText = (EditText) findViewById(R.id.editText);

        final Button startAsyncTask =(Button) findViewById(R.id.lang_button);
        startAsyncTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //по событию онклик явный запуск сервиса
               Intent intent = new Intent(MyWeather.this, FragmentsActivity.class);
               //intent.putExtra(MyLanguage.LANGUAGE, "ru");
               startActivity(intent);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: Intent intentRu = new Intent(MyWeather.this, MyLanguage.class);
                        intentRu.putExtra(MyLanguage.LANGUAGE, "ru");
                        startService(intentRu); break;
                    case 1: Intent intentEn = new Intent(MyWeather.this, MyLanguage.class);
                        intentEn.putExtra(MyLanguage.LANGUAGE, "en");
                        startService(intentEn); break;
                    case 2: Intent intentFr = new Intent(MyWeather.this, MyLanguage.class);
                        intentFr.putExtra(MyLanguage.LANGUAGE, "fr");
                        startService(intentFr); break;
                    default: Intent intentDe = new Intent(MyWeather.this, MyLanguage.class);
                        intentDe.putExtra(MyLanguage.LANGUAGE, "de");
                        startService(intentDe); break;
                }
            }
        });
        IntentFilter filter = new IntentFilter(Receiver.ACTION);
        //filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new Receiver();
        registerReceiver(receiver,filter);
    }
    public void createNotification() {
        Intent intent = new Intent(this, MyWeather.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle("NOTIFICATION!!!")
                .setContentText("SUBJECT!!!")
                .setSmallIcon(R.drawable.cross)
                .setContentIntent(pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }

    public class Receiver extends BroadcastReceiver{
        public static final String LANGUAGE = "text";
        public static final String ACTION = "lang.received";
        @Override
        public void onReceive(Context context, Intent intent){
            //реакция на полученное намерение
            String language = intent.getStringExtra(LANGUAGE);
            myEditText.setText(language);
            //createNotification();
        }
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



