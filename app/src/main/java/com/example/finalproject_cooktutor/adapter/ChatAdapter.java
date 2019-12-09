package com.example.finalproject_cooktutor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject_cooktutor.R;
import com.example.finalproject_cooktutor.entity.Chat;

import java.util.List;

public class ChatAdapter extends ArrayAdapter {
    private final int resourceId;

    public ChatAdapter(Context context, int textViewResourceId, List<Chat> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Chat chat = (Chat) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView chatImage = (ImageView) view.findViewById(R.id.chat_image);
        TextView chatContent = (TextView) view.findViewById(R.id.chat_content);
        chatImage.setImageResource(chat.getImageId());
        chatContent.setText(chat.getName() + "\n\n" + chat.getLastMsg());
        return view;
    }
}
