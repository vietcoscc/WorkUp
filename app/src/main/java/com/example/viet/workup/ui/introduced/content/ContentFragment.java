package com.example.viet.workup.ui.introduced.content;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ContentFragment extends Fragment {
    private int mLayout;

    @SuppressLint("ValidFragment")
    public ContentFragment(int mLayout) {
        this.mLayout = mLayout;
    }

    public ContentFragment() {

    }

    public static ContentFragment newInstance(int mLayout) {
        ContentFragment fragment = new ContentFragment(mLayout);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //TODO:
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(mLayout, container, false);
        return view;
    }


}
