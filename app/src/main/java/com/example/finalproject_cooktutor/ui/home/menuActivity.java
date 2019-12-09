package com.example.finalproject_cooktutor.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject_cooktutor.R;
import com.jaren.lib.view.LikeView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import androidx.appcompat.app.AppCompatActivity;

public class menuActivity extends AppCompatActivity {

    private ImageView menuPhoto;
    private TextView menuTitle;
    private TextView menuIntro;
    private TextView menuAuthor;
    private LikeView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuPhoto= (ImageView) findViewById(R.id.menu_info_photo);
        menuTitle= (TextView) findViewById(R.id.menu_info_title);
        menuIntro= (TextView) findViewById(R.id.menu_info_intro);
        menuAuthor = (TextView) findViewById(R.id.menu_info_author);
        lv = (LikeView) findViewById(R.id.lv);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        theMenu item= (theMenu) intent.getSerializableExtra("Menu");
        menuPhoto.setImageResource(item.getPhotoId());
        menuTitle.setText(item.getTitle());
        menuIntro.setText(item.getIntro());
        menuAuthor.setText(item.getAuthor());
        lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.toggle();
            }
        });

//        new menuLoadActivity().execute("loadMenu", author, title);
    }


    class menuLoadActivity extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params){
            try{
                String method = (String)params[0];
                String author = (String)params[1];
                String title = (String)params[2];
                String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(author, "UTF-8");
                data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");

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
                Toast.makeText(menuActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("Request ", "successfully");
                Log.d("RESULT ", result);
                String[] userInfos = result.split(";");
                String menu_id = userInfos[1];
                String description = userInfos[2];
                String intro = userInfos[3];
                String hot = userInfos[4];
                menuIntro.setText(intro);
            }
        }
    }
}
