package com.example.finalproject_cooktutor.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finalproject_cooktutor.Navi_bar;
import com.example.finalproject_cooktutor.R;
import com.example.finalproject_cooktutor.adapter.ChatAdapter;
import com.example.finalproject_cooktutor.entity.Chat;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ChatFragment extends Fragment {

    private ChatViewModel chatViewModel;
    private String account;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                ViewModelProviders.of(this).get(ChatViewModel.class);
        root = inflater.inflate(R.layout.fragment_chat, container, false);

        chatViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        recentContacts();
        return root;
    }

    private void recentContacts() {
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                        List<Chat> chatList = new ArrayList<>();
                        for(int i = 0;i<recents.size();i++) {
                            Log.i("recent contact list: ", i + "  " + recents.get(i).getContactId());
                            if(recents.get(i).getContactId().equals(account)){
                                continue;
                            }
                            Chat chat = new Chat(recents.get(i).getContactId(),recents.get(i).getContent(),R.drawable.avatar);
                            chatList.add(chat);
                        }

                        ChatAdapter adapter = new ChatAdapter(getActivity(), R.layout.chat_item, chatList);
                        ListView listView = root.findViewById(R.id.chat_list);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String friendId = recents.get(position).getContactId();
                                Intent intent = new Intent(getActivity(),OneChatActivity.class);
                                intent.putExtra("account", account);
                                intent.putExtra("friendId",friendId);
                                getActivity().startActivity(intent);
                            }
                        });

                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        account = ((Navi_bar) context).getData("username");
    }
}