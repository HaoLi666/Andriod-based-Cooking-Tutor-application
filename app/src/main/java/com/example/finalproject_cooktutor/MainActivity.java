package com.example.finalproject_cooktutor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    String ne_result = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);
    }

    public void login(View view){
        String user = usernameField.getText().toString();
        String psw = passwordField.getText().toString();
        String method = "login";
        Log.d("username: ", user);
        Log.d("password: ", psw);
        new loginActivity().execute(method, user, psw);
    }

    public void go_registration(View view){
        Intent intent = new Intent(this, registration.class);
        startActivity(intent);
    }

    public void loginIM(String account, String token){
                LoginInfo info = new LoginInfo(account, token); // config...
                RequestCallback<LoginInfo> callback =
                        new RequestCallback<LoginInfo>() {

                            @Override
                            public void onException(Throwable arg0) {
                                System.out.println("--------------------------------");
                                System.out.println(arg0);
                            }

                            @Override
                            public void onFailed(int code) {
                                if (code == 302) {
                                    Toast.makeText(MainActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
                                } else if (code == 408) {
                                    Toast.makeText(MainActivity.this, "timeout", Toast.LENGTH_SHORT).show();
                                } else if (code == 415) {
                                    Toast.makeText(MainActivity.this, "no network", Toast.LENGTH_SHORT).show();
                                } else if (code == 416) {
                                    Toast.makeText(MainActivity.this, "wrong, please try later", Toast.LENGTH_SHORT).show();
                                } else if (code == 417) {
                                    Toast.makeText(MainActivity.this, "the account is already logged in at another end", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "unknown mistake, please try late", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onSuccess(LoginInfo loginInfo) {
                                Log.e("TAG", "onSuccess: " + loginInfo + "======================================================");
                                new loginActivity().execute("login", account, token);
                                finish();
                            }
                        };
                NIMClient.getService(AuthService.class).login(info)
                        .setCallback(callback);
    }

    class loginActivity extends AsyncTask<String, String, String> {

//        public static final String EXTRA_MESSAGE = "MESSAGE";

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

            if(result.equals("Failed")){
                Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
            else if(result.contains("Failed")){
                Toast.makeText(MainActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
//            } else if(ne_result.equals("false")){
//                Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("Login ", "successfully");
                String[] res = result.split(";");
                System.out.println(result);
                Intent intent = new Intent(MainActivity.this, Navi_bar.class);
                intent.putExtra("username", res[1]);
                intent.putExtra("nation", res[2]);
                intent.putExtra("interest", res[3]);
                startActivity(intent);
            }
        }
    }
}
