package com.example.finalproject_cooktutor.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject_cooktutor.Navi_bar;
import com.example.finalproject_cooktutor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<theMenu> menuList;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private View root;
    private String username;
    private String nation;
    private String interest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView= root.findViewById(R.id.recyclerView);
        initData();
        if(menuList.isEmpty()){
            addData();
        }

        adapter=new RecyclerViewAdapter(menuList, getActivity(),username);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        floatingActionButton = root.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),addMenuActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("nation",nation);
                intent.putExtra("interest",interest);
//                menuList.clear();
                getActivity().startActivity(intent);
            }
        });

        return root;
    }

    private void addData() {
        menuList.add(new theMenu("Sushi","Japanese Traditional food", "\n" +
                "1) Keep the rice cool. You can add apple cider vinegar to rice and stir well. Authentic sushi rice is made with vinegar. However, the special sushi vinegar is too expensive. Use apple cider vinegar instead.\n" +
                "2) Beat the egg mixture and fry the eggs.\n" +
                "3) Wash your hands, then wet your palms with a bit of cold water so that the rice grains don't stick to your hands. Grab the right amount of rice and make it boxy.\n" +
                "4) Cut the egg into the shape shown in the picture, place it on the rice balls, and cut a piece of laver to tie them. Note that the interface of the laver is at the bottom of the rice ball! This completes the fried egg sushi. The authentic fried egg sushi egg slices are very thick, everyone can think of ways to make them thicker.\n" +
                "5) Jump from step 3 to this. After making the rice balls, take a piece of laver (a little higher than the rice balls) to surround the rice balls and stick them. Cut another bundle and make a boundary.\n" +
                "6) Place crab roe and omelette on each side. Fuck! A bright orange and yellow two-color sushi is OK!", "hao","1", "0", R.drawable.sushi));
        menuList.add(new theMenu("Gongbao Chicken","Famous Chinese food", "\n" +
                "1) Pat the chicken breast with the back of the knife, cut into small cubes, add one tablespoon of cooking wine, half a tablespoon of cooking oil, half a teaspoon of white pepper, half a teaspoon of salt, pickle a teaspoon of starch for 10 minutes, and mix well with water starch.\n" +
                "2) Wash the green onions and cut into sections, wash the dried peppers, cut off both ends to remove the pepper seeds, and cut the cucumber into diced pieces.\n" +
                "3) In a small bowl, add soy sauce, balsamic vinegar, salt, ginger juice, caster sugar, and cooking wine, and mix well to make a seasoning sauce.\n" +
                "4) Leave the base oil in the pot, heat the peppercorns and dried peppers, fry them with a low heat, and then add the shallots.\n" +
                "5) Add chicken, add 1 tablespoon cooking wine, stir-fry the chicken and change color, then pour in water starch.\n" +
                "6) Finally, add the sauce, and then add the cooked peanuts, stir fry evenly, and use water starch to simmer.", "hao","3", "0", R.drawable.gongbao));
        menuList.add(new theMenu("Pizza","Classical Italian food","\n" +
                "1) Mix high gluten flour, low gluten flour, and salt into the pot. Put the yeast in a bowl, pour in warm water (close to body temperature), mix well and let stand for 5 minutes, then pour into the flour, stir with chopsticks, and slowly mix.\n" +
                "2) Then knead the loose dough into a slightly smooth dough by hand. Add softened butter. Knead the dough and butter until they are completely combined, and the dough becomes smooth and elastic.\n" +
                "3) Cover the pot with a plastic wrap, place the dough in a warm place, and ferment for about 1 hour. When the dough becomes 1-1.5 times larger, the dough is fermented. \n" +
                "4) Put butter into the pan, melt over low heat, then add flour and stir-fry.\n" +
                "5) Pour in milk and stir continuously. After about half a minute, add light cream and continue cooking until the sauce is thick.\n" +
                "6) Add cheese powder, pizza grass, sprinkle with black pepper and salt at the end, mix well and turn off the heat. " +
                "7) Flatten the fermented dough, roll it into a round pasta with a rolling pin, and spread the rolled dough on a pizza plate. Use your hands to form a slightly thicker dough, and use a fork to pierce the dough. Several holes.\n" +
                "8) Evenly spread a layer of creamy white sauce on the crust, sprinkle a thin layer of mozzarella cheese, and then cut the sliced \u200B\u200Bpineapple slices (drained water), sausage slices, corn kernels (drained water) in that order Spread on the crust and sprinkle the remaining mozzarella cheese on top.\n" +
                "9) Preheat the oven 10 minutes in advance. Put the pizza in the middle layer of the oven. Bake at 200 ° C for about 20-25 minutes. Bake until the surrounding area is colored and the surface is golden.", "li","4", "0", R.drawable.pizza));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        username = ((Navi_bar) context).getData("username");
        nation = ((Navi_bar) context).getData("nation");
        interest = ((Navi_bar) context).getData("interest");
    }

    private void initData() {
        menuList =new ArrayList<>();

        new menuDataLoadActivity().execute("allMenus","","");

//        if(menuList.isEmpty()){
//
//
//        }
    }


    class menuDataLoadActivity extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params){
            try{
                String method = params[0];
                String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");

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
                Toast.makeText(getActivity(), "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("Submit ", "successfully");
                String[] userInfos = result.split(";");
                String[] infos = Arrays.copyOfRange(userInfos, 1, userInfos.length);

                for (int i=0; i<infos.length;i++) {
                    String[] new_item = infos[i].split("----");
                    switch (new_item[2]){
                        case "Sushi":
                            menuList.add(new theMenu(new_item[2],new_item[3],new_item[4],new_item[1],new_item[0],new_item[6],R.drawable.sushi));
                            break;
                        case "Gongbao Chicken":
                            menuList.add(new theMenu(new_item[2],new_item[3],new_item[4],new_item[1],new_item[0],new_item[6],R.drawable.gongbao));
                            break;
                        case "Pizza":
                            menuList.add(new theMenu(new_item[2],new_item[3],new_item[4],new_item[1],new_item[0],new_item[6],R.drawable.pizza));
                            break;
                        default:
                            menuList.add(new theMenu(new_item[2],new_item[3],new_item[4],new_item[1],new_item[0],new_item[6],R.drawable.ic_default_image));
                            break;
                    }
                }
            }
        }
    }
}