package com.example.finalproject_cooktutor.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject_cooktutor.Navi_bar;
import com.example.finalproject_cooktutor.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class configurationActivity extends AppCompatActivity {

    private TextView username;
    private EditText nation;
    private EditText interest;
    private AlertDialog alertDialog;

    String usernameMSG = "";
    String nationMSG = "";
    String interestsMSG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        Intent intent = getIntent();
        usernameMSG = intent.getStringExtra("username");
        nationMSG = intent.getStringExtra("nation");
        interestsMSG = intent.getStringExtra("interest");

        username = findViewById(R.id.username);
        nation = (EditText) findViewById(R.id.nation);
        interest = (EditText) findViewById(R.id.interest);

        username.setText(usernameMSG);
        if(!nationMSG.equals("Unknown")){
            nation.setText(nationMSG);
        }
        interest.setText(interestsMSG);
    }

    public void showInterestAlertDialog(View view){
        final String[] items = {"Chinese", "Japanese", "American", "French", "Italian"};
        final ArrayList<String> result = new ArrayList<>();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Interests");

        alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(configurationActivity.this, "selecting " + items[i], Toast.LENGTH_SHORT).show();
                    result.add(items[i]);
                }else {
                    Toast.makeText(configurationActivity.this, "canceling " + items[i], Toast.LENGTH_SHORT).show();
                    result.remove(items[i]);
                }
            }
        });
        alertBuilder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = "";
                for(int j = 0;j<result.size();j++){
                    s = s + result.get(j);
                    if(j!=result.size()-1){
                        s = s + "_";
                    }
                }
                interest.setText(s);
                alertDialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });


        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void submit(View view){
        nationMSG = nation.getText().toString();
        interestsMSG = interest.getText().toString();

        new confSubmitActivity().execute(usernameMSG, nationMSG, interestsMSG);
    }


    class confSubmitActivity extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params){
            try{
                String method = "configuration";
                String username_send = (String)params[0];
                String nation_send = (String)params[1];
                String interest_send = (String)params[2];
                String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username_send, "UTF-8");
                data += "&" + URLEncoder.encode("nation", "UTF-8") + "=" + URLEncoder.encode(nation_send, "UTF-8");
                data += "&" + URLEncoder.encode("interest", "UTF-8") + "=" + URLEncoder.encode(interest_send, "UTF-8");

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
                Toast.makeText(configurationActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("Submit ", "successfully");
                Intent intent = new Intent(configurationActivity.this, Navi_bar.class);
                intent.putExtra("username", usernameMSG);
                intent.putExtra("nation", nationMSG);
                intent.putExtra("interest", interestsMSG);
                startActivity(intent);
            }
        }
    }
}
