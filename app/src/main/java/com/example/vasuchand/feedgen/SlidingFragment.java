package com.example.vasuchand.feedgen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class SlidingFragment extends Fragment {

    ImageView settings;
    Toolbar toolbar;
    Button b1,b2,b3,b4,b5,b6,b7;
    TextView t1;

    public static SlidingFragment newInstance() {
        return new SlidingFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sliding_layout, parent, false);
        toolbar =(Toolbar)rootView.findViewById(R.id.toolbar);
        t1 = (TextView) rootView.findViewById(R.id.t1);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), youtube.class);
                // intent.putExtra("category", "trending");
                startActivity(intent);

            }
        });

        return rootView;


      }






}