package com.example.lenovo.database_app;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSON_parser extends ListActivity {
    private static final String LOGIN ="login";
    private static final String USER = "username";
    private static final String CITY = "city";
   // static JSONArray myjsonarray = null;
    JSONArray myjsonarray;

    ArrayList<HashMap<String, String>> json_array = new ArrayList<HashMap<String, String>>();


    public JSONArray getJSONFeed(String url) {
        StringBuilder sbuilder = new StringBuilder();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet hget = new HttpGet(url);
        try {
            org.apache.http.HttpResponse response = httpclient.execute(hget);
            StatusLine sline = response.getStatusLine();
            int Statuscode = sline.getStatusCode();
            if (Statuscode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    sbuilder.append(line);

                }

            } else {
                Log.e("Connection Status", "Not connected to webpage");
            }
        } catch (ClientProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            myjsonarray = new JSONArray(sbuilder.toString());

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return myjsonarray;
    }


    private class doJSONread extends AsyncTask<String, Void, Boolean> {


        private ListActivity activity;

        public doJSONread(ListActivity activity)
        {

            this.activity=activity;
            context=activity;
        }
        private Context context;
        @Override
        protected Boolean doInBackground(String... params) {
            JSONArray j = getJSONFeed(params[0]);
            for (int i = 0; i < j.length(); i++) {

                try {
                    JSONObject c = j.getJSONObject(i);
                    String l = c.getString("login");
                    String user_name = c.getString("username");
                    String city_name = c.getString("city");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(LOGIN, l);
                    map.put(USER, user_name);
                    map.put(CITY, city_name);
                    json_array.add(map);


                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
            return null;
        }





    @Override
    protected void onPostExecute(final Boolean suceess) {
     int resource=R.layout.mylist;
     String[] from = new String[]{LOGIN,USER,CITY};
      int[] to={R.id.login,R.id.username,R.id.city};

            ListAdapter adapter= new SimpleAdapter(context,json_array,resource,from,to);

        setListAdapter(adapter);
       
    }

}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       // new doJSONread().execute("http://extjs.org.cn/extjs/examples/grid/survey.html");
        new doJSONread(this).execute("http://www.pradeep-projects.in/Online_Exam/Online_exam_New/JSON_encodesample.php");
    }

}
