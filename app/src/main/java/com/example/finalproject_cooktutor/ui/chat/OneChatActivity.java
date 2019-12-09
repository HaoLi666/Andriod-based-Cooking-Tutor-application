package com.example.finalproject_cooktutor.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject_cooktutor.R;
import com.example.finalproject_cooktutor.adapter.MsgAdapter;
import com.example.finalproject_cooktutor.entity.Msg;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OneChatActivity extends AppCompatActivity {
    private String account;
    private String friendId;

    private List<Msg> msgList=new ArrayList<Msg>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_chat);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        friendId = intent.getStringExtra("friendId");

        setTitle(friendId);

        IMMessage anchor = MessageBuilder.createEmptyMessage(friendId , SessionTypeEnum.P2P, System.currentTimeMillis());

        NIMClient.getService(MsgService.class).pullMessageHistory(anchor, 100, false).setCallback(new RequestCallbackWrapper<List<IMMessage>>(){
            @Override
            public void onResult(int code, List<IMMessage> result, Throwable exception) {
                initMsgs(result);

            }
        });
    }

    private void initMsgs(List<IMMessage> messages){
        for(int i =  messages.size()-1;i>=0;i--){
            IMMessage message = messages.get(i);
            String content = message.getContent();
            if(message.getDirect().equals(MsgDirectionEnum.Out)){
                Msg msg=new Msg(content,Msg.TYPE_SEND);
                msgList.add(msg);
            }else{
                Msg msg=new Msg(content,Msg.TYPE_RECEIVED);
                msgList.add(msg);
            }
        }
        inputText=(EditText)findViewById(R.id.send_message);
        send=(Button)findViewById(R.id.send);
        msgRecyclerView=(RecyclerView)findViewById(R.id.msg_recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter=new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg=new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");

                    SessionTypeEnum type =  SessionTypeEnum.P2P;
                    final IMMessage textMessage = MessageBuilder.createTextMessage(friendId, type, content);
                    NIMClient.getService(MsgService.class).sendMessage(textMessage, false).setCallback(new RequestCallback<Void>() {
                        @Override
                        public void onSuccess(Void param) {
                            Toast.makeText(OneChatActivity.this, "send successfully", Toast.LENGTH_SHORT).show();
                            Log.i("send msg successfully: ","from "+ account + " to " + friendId);
                            Log.i("send content: ",content);
                        }

                        @Override
                        public void onFailed(int code) {

                        }

                        @Override
                        public void onException(Throwable exception) {

                        }
                    });
                }
            }
        });
    }
}
