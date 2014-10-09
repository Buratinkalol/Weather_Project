package com.example.sun.weatherproject;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.widget.EditText;

import com.example.sun.weatherproject.MyWeather;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sundays on 10/8/14.
 */

public class MyLanguage extends IntentService {
    public static final String LANGUAGE = "ru";
    public MyLanguage() { super("MyLanguage");}

    public void urlConnection(String lang) throws IOException{
        URL url = new URL("https://translate.yandex.net/api/v1.5/tr/getLangs?trnsl.1.1.20141008T184207Z.282734f58fd648f2.c285f1cef2fb775e156107ca011b94bfdec084c8&text=make&lang="+lang);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        if (code == 200) {
            InputStream in = connection.getInputStream();
            handleStream(in);
        }

    }
    public void handleStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder text=new StringBuilder();

        try {
            while((line = reader.readLine())!=null) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String dataString = text.toString();
        Log.v("",dataString);
        //return text.toString();

        //while (reader.readLine() != null)
        //Log.e("",reader.readLine());
        
    }
    /*
    public void handleInputStream (InputStream in) throws IOException,XmlPullParserException {
        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(in, null);
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("result")) {
                    eventType = xpp.next();
                    String name = "";
                    while (!(eventType == XmlPullParser.END_TAG && xpp.getName().equals("result"))) {
                        if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("name"))
                            name = xpp.nextText();
                        eventType = xpp.next();
                    }

                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.d("PULLPARSER", "XML PULL PARSER EXEPTION" , e);
        } catch (IOException e) {
            Log.d("PULLPARSER", "IOExeption" , e);

        }
    }*/
    private void handleInputStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String result="",line="";
                    while ((line = reader.readLine())!=null) {
                        result += line;
                    }
                    Log.e("", result);
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(MyWeather.Receiver.ACTION);
                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    broadcastIntent.putExtra(MyWeather.Receiver.LANGUAGE, result);
                    sendBroadcast(broadcastIntent);
    }
    /*
    public void httpGet() throws IOException {
        HttpGet request = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/getLangs?trnsl.1.1.20141008T184207Z.282734f58fd648f2.c285f1cef2fb775e156107ca011b94bfdec084c8&[ui=en]");
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(),443));
        HttpParams params = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(params,10);
        ConnManagerParams.setMaxConnectionsPerRoute(params,
                new ConnPerRoute() {
                    @Override
                    public int getMaxForRoute(HttpRoute httpRoute) {
                        return 5;
                    }
                });
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
        httpClient.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                return 5000;
            }
        });
        HttpResponse response = httpClient.execute(request);
        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            handleInputStream(response.getEntity().getContent());
        }
    }*/

    @Override
    protected void onHandleIntent(Intent intent){
        //реализуется в фоновом потоке,
        // все намерения обрабатываются последовательно,
        // после чего сервис завершает свою работу.
        String lang = intent.getStringExtra(LANGUAGE);
        try {
            urlConnection(lang);
        } catch (IOException e) {
            Log.e("",e.getLocalizedMessage(),e);
        }
    }
}
