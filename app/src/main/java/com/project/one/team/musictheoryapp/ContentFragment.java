package com.project.one.team.musictheoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";

    public static ContentFragment newInstance(String title, String content) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String title = getArguments().getString(ARG_TITLE);
        String content = getArguments().getString(ARG_CONTENT);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_content, container, false);

        TextView titleTextView = (TextView) v.findViewById(R.id.titleTextView);
        titleTextView.setText(title);

        TextView contentTextView = (TextView) v.findViewById(R.id.contentTextView);
        contentTextView.setText(content);

        return v;
    }

}
