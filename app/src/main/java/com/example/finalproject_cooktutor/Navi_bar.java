package com.example.finalproject_cooktutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Navi_bar extends AppCompatActivity {

    private String username;
    private String nation;
    private String interest;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_bar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        nation = intent.getStringExtra("nation");
        interest = intent.getStringExtra("interest");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);
        return true;
    }

    public String getData(String s) {
        String result = "";
        switch (s){
            case "username": result = username;break;
            case "nation": result = nation;break;
            case "interest": result = interest;break;
            default:break;
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search_menu:
                search();
                return true;
            case R.id.help:
                showHelp();
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        final String msg = "Are you sure you want to LOG OUT";

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("ALERT");

        alertBuilder.setMessage(msg);

        alertBuilder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Navi_bar.this, MainActivity.class);
                startActivity(intent);
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

    private void showHelp() {
        final String msg = "If you have any question or suggestion, just e-mail to hal918@lehigh.edu or yuq618@lehigh.edu";

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("HELP");

        alertBuilder.setMessage(msg);

        alertBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });


        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private void search() {
        Intent intent=new Intent(this, searchActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

}
