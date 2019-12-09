package com.example.finalproject_cooktutor.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.finalproject_cooktutor.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class likelistActivity extends AppCompatActivity {

    public String[] infos = {};
    public String[] infos2 = {};
    private SearchView mySearchView;
    private ListView myListView;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likelist);

        Intent intent = getIntent();
        String usernameMSG = intent.getStringExtra("username");

        new sendLikelistActivity().execute("likelist",usernameMSG,"");
    }

    class sendLikelistActivity extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params){
            try{
                String method = (String)params[0];
                String username_send = (String)params[1];
                String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username_send, "UTF-8");

                String link="http://192.168.0.128:8888/cook_tutor/index.php";
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null){
                    sb.append(line);
                }
                Log.d("My Result:", sb.toString());
                return sb.toString();
            }
            catch (Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result){
            if(result.contains("Failed")){
                Toast.makeText(likelistActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("Request ", "successfully");
                Log.d("RESULT ", result);
                String[] userInfos = result.split(";");
                infos = Arrays.copyOfRange(userInfos, 1, userInfos.length);
                infos2 = Arrays.copyOfRange(userInfos, 1, userInfos.length);

                for (int i=0; i<infos.length;i++) {
                    String[] new_item = infos[i].split("----");
                    infos[i] = new_item[1]+"\nAuthor: "+new_item[0]+"\n"+new_item[2];
                    infos2[i] = new_item[1]+"\nAuthor: "+new_item[0]+"\n"+new_item[2]+"\n"+new_item[3];
                }

                mySearchView = (SearchView) findViewById(R.id.searchView);
                myListView = (ListView) findViewById(R.id.listView);
                myListView.setAdapter(new ArrayAdapter<String>(likelistActivity.this, android.R.layout.simple_list_item_activated_1, infos));
                myListView.setTextFilterEnabled(true);

                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick (AdapterView < ? > adapter, View view, int position, long arg) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(likelistActivity.this);
                        alertBuilder.setTitle("Detail Step");
                        alertBuilder.setMessage(infos2[position]);
                        alertBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog = alertBuilder.create();
                        alertDialog.show();
                    }
                });

                mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (!TextUtils.isEmpty(newText)){
                            myListView.setFilterText(newText);
                        }else{
                            myListView.clearTextFilter();
                        }
                        return false;
                    }
                });
            }
        }
    }
}
