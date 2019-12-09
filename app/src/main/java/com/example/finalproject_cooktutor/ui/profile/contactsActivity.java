package com.example.finalproject_cooktutor.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finalproject_cooktutor.R;
import com.example.finalproject_cooktutor.adapter.FriendAdapter;
import com.example.finalproject_cooktutor.entity.Friend;
import com.example.finalproject_cooktutor.ui.chat.OneChatActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class contactsActivity extends AppCompatActivity {
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setTitle("Contacts");

        Intent intent = getIntent();
        account = intent.getStringExtra("account");

        showFriendsList();
    }

    public void showFriendsList(){
        List<String> friends = NIMClient.getService(FriendService.class).getFriendAccounts();
        List<Friend> friendList = new ArrayList<>();
        for(int i = 0;i<friends.size();i++) {
            Log.i("my friends' list: ", i + "  " + friends.get(i));
            Friend friend = new Friend(friends.get(i),R.drawable.avatar);
            friendList.add(friend);
        }

        FriendAdapter adapter = new FriendAdapter(contactsActivity.this, R.layout.friend_item, friendList);
        ListView listView = (ListView) findViewById(R.id.friends_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String friendId = friends.get(position);
                Intent intent = new Intent(contactsActivity.this,OneChatActivity.class);
                intent.putExtra("account", account);
                intent.putExtra("friendId",friendId);
                startActivity(intent);
            }
        });
    }
}