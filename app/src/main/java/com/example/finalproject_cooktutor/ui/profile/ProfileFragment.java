package com.example.finalproject_cooktutor.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject_cooktutor.Navi_bar;
import com.example.finalproject_cooktutor.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private Button likelist;
    private Button contacts;
    private Button uploads;
    private Button configuration;
    private String username;
    private String nation;
    private String interest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView usernameTv = root.findViewById(R.id.username);
        usernameTv.setText(username);

        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                usernameTv.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        username = ((Navi_bar) context).getData("username");
        nation = ((Navi_bar) context).getData("nation");
        interest = ((Navi_bar) context).getData("interest");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        likelist = (Button) getActivity().findViewById(R.id.likeList);
        contacts = (Button) getActivity().findViewById(R.id.contacts);
        uploads = (Button) getActivity().findViewById(R.id.myUploads);
        configuration = (Button) getActivity().findViewById(R.id.configuration);

        likelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), likelistActivity.class);
                intent.putExtra("username", username);
                getActivity().startActivity(intent);
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), contactsActivity.class);
                intent.putExtra("username", username);
                getActivity().startActivity(intent);
            }
        });

        uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), uploadsActivity.class);
                intent.putExtra("username", username);
                getActivity().startActivity(intent);
            }
        });

        configuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), configurationActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("nation", nation);
                intent.putExtra("interest", interest);
                getActivity().startActivity(intent);
            }
        });
    }


}