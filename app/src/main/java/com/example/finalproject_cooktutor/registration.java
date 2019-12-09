package com.example.finalproject_cooktutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class registration extends AppCompatActivity {

    private EditText usernameField, passwordField, confirmField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);
        confirmField = (EditText)findViewById(R.id.confirmPsw);
    }

    public void registration(View view){
        String user = usernameField.getText().toString();
        String psw = passwordField.getText().toString();
        String confirm = confirmField.getText().toString();
        if(!psw.equals(confirm)){
            String msg = "Please enter same Password";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }else {
            String method = "register";
            Log.d("username: ", user);
            Log.d("password: ", psw);
            new registerActivity().execute(method, user, psw);
        }
    }

    class registerActivity extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params){
            try{
                String method = (String)params[0];
                String username = (String)params[1];
                String password = (String)params[2];
                String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
            if(result.contains("been used")){
                Toast.makeText(registration.this, "User name has been used", Toast.LENGTH_SHORT).show();
            }
            else if(result.contains("Failed")){
                Toast.makeText(registration.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(registration.this, "Register successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(registration.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
