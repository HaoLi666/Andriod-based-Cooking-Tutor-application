package com.example.finalproject_cooktutor.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject_cooktutor.Navi_bar;
import com.example.finalproject_cooktutor.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class addMenuActivity extends AppCompatActivity {

    private static final int REQUEST_LIST_CODE = 0;
    private static final int REQUEST_CAMERA_CODE = 1;

    private String username;
    private String title;
    private String desc;
    private String intro;
    private String photoID;

    private String nation;
    private String interest;

    private TextView tvResult;
    private EditText title_et;
    private EditText desc_et;
    private EditText intro_et;

//    private SimpleDraweeView draweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_add_menu);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        nation = intent.getStringExtra("nation");
        interest = intent.getStringExtra("interest");

        tvResult = (TextView) findViewById(R.id.tvResult);
        title_et =  (EditText) findViewById(R.id.title);
        desc_et =  (EditText) findViewById(R.id.desc);
        intro_et =  (EditText) findViewById(R.id.intro);
//        draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });

    }

    public void Single(View view) {
        tvResult.setText("");
        ISListConfig config = new ISListConfig.Builder()
                .multiSelect(false)
                .btnText("Confirm")
                .btnTextColor(Color.WHITE)
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#008577"))
                .allImagesText("All Images")
                .build();

        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }

    public void Camera(View view) {
        ISCameraConfig config = new ISCameraConfig.Builder()
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                .build();

        ISNav.getInstance().toCameraActivity(this, config, REQUEST_CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            for (String path : pathList) {
                tvResult.append(path + "\n");
            }
        } else if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result");
            tvResult.append(path + "\n");
//            draweeView.setImageURI(Uri.parse(path));
        }
    }

    public void submit_menu(View view){
        title = title_et.getText().toString();
        desc = desc_et.getText().toString();
        intro = intro_et.getText().toString();
        photoID = tvResult.getText().toString();

        new sendNewMenuActivity().execute("addNewMenu","","");
    }


    class sendNewMenuActivity extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params){
            try{
                String method = params[0];
                String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");
                data += "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8");
                data += "&" + URLEncoder.encode("intro", "UTF-8") + "=" + URLEncoder.encode(intro, "UTF-8");
                data += "&" + URLEncoder.encode("photoID", "UTF-8") + "=" + URLEncoder.encode(photoID, "UTF-8");

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
                Toast.makeText(addMenuActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }else if(result.contains("Sorry")){
                Toast.makeText(addMenuActivity.this, "Sorry, you have already added the Menu before, please check your uploads", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("Submit ", "successfully");
                Toast.makeText(addMenuActivity.this, "Successfully adding the menu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(addMenuActivity.this, Navi_bar.class);
                intent.putExtra("username",username);
                intent.putExtra("nation",nation);
                intent.putExtra("interest",interest);
                startActivity(intent);
            }
        }
    }
}
