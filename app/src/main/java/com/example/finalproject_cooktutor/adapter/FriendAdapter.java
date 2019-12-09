package com.example.finalproject_cooktutor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject_cooktutor.R;
import com.example.finalproject_cooktutor.entity.Friend;

import java.util.List;

public class FriendAdapter extends ArrayAdapter {
    private final int resourceId;

    public FriendAdapter(Context context, int textViewResourceId, List<Friend> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friend friend = (Friend) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView fruitImage = (ImageView) view.findViewById(R.id.friend_image);
        TextView fruitName = (TextView) view.findViewById(R.id.friend_name);
        fruitImage.setImageResource(friend.getImageId());
        fruitName.setText(friend.getName());
        return view;
    }
}
