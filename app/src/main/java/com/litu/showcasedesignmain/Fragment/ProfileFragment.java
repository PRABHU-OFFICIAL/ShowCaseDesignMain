package com.litu.showcasedesignmain.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.litu.showcasedesignmain.EditProfile;
import com.litu.showcasedesignmain.R;


public class ProfileFragment extends Fragment {

Button edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_profile, container, false);

         edit = view.findViewById(R.id.edit_account_settings_btn);
        edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), EditProfile.class);
                startActivity(in);
            }
        });

    return  view;
    }
}