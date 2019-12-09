package com.example.finalproject_cooktutor.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject_cooktutor.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MenuViewHolder>{

    private List<theMenu> menus;
    private Context context;
    private String username;

    public RecyclerViewAdapter(List<theMenu> menus,Context context, String username) {
        this.menus = menus;
        this.context = context;
        this.username = username;
    }


    static class MenuViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView menu_photo;
        TextView menu_title;
        TextView menu_desc;
        TextView menu_author;
        Button share;
        Button readMore;

        public MenuViewHolder(final View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            menu_photo= (ImageView) itemView.findViewById(R.id.menu_photo);
            menu_title= (TextView) itemView.findViewById(R.id.menu_title);
            menu_author = (TextView) itemView.findViewById(R.id.menu_author);
            menu_desc= (TextView) itemView.findViewById(R.id.menu_desc);
            share= (Button) itemView.findViewById(R.id.btn_share);
            readMore= (Button) itemView.findViewById(R.id.btn_more);

            menu_title.setBackgroundColor(Color.argb(20, 0, 0, 0));
        }


    }

    @Override
    public RecyclerViewAdapter.MenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.menu_item,viewGroup,false);
        MenuViewHolder mvh=new MenuViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MenuViewHolder personViewHolder, int i) {
        final int j=i;

        personViewHolder.menu_photo.setImageResource(menus.get(i).getPhotoId());
        personViewHolder.menu_title.setText(menus.get(i).getTitle());
        personViewHolder.menu_author.setText(menus.get(i).getAuthor());
        personViewHolder.menu_desc.setText(menus.get(i).getDesc());
        personViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,menuActivity.class);
                intent.putExtra("Menu",menus.get(j));
                intent.putExtra("username",username);
                context.startActivity(intent);
            }
        });

        personViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, menus.get(j).getDesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, menus.get(j).getTitle()));
            }
        });
//
        personViewHolder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,menuActivity.class);
                intent.putExtra("Menu",menus.get(j));
                intent.putExtra("username",username);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return menus.size();
    }
}
